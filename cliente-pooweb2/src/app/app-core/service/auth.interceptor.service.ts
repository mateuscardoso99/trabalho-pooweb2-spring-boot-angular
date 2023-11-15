import { HTTP_INTERCEPTORS, HttpErrorResponse, HttpEvent } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpHandler, HttpRequest } from '@angular/common/http';
import { catchError, filter, finalize, switchMap, take } from 'rxjs/operators';
import { StorageService } from './storage.service';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { AuthService } from './auth.service';
import { Router } from '@angular/router';

const TOKEN_HEADER_KEY = 'Authorization';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
    private isRefreshing = false;
    private refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject<any>(null);

    constructor(private storageService: StorageService, private authService: AuthService, private router: Router) { }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<Object>> {
        let authReq = req;
        const usuario = this.storageService.getUser();
        if (usuario != null) {
            authReq = this.addAuthToken(req,usuario.token);
        }
    
        //se status de erro for 401, então tenta usar o refresh token pra conseguir um novo token
        return next.handle(authReq).pipe(catchError(error => {
          if (error instanceof HttpErrorResponse && !authReq.url.includes('login') && error.status === 401) {
            return this.handle401Error(authReq, next);
          }
          else if(error.status === 403){
            this.storageService.signOut();
            this.router.navigate(['/login']);
          }

          return throwError(() => error);
        }));
      }
    
      private handle401Error(request: HttpRequest<any>, next: HttpHandler) {
        //quando o progresso da atualização estiver sendo processado (isRefreshing = true), esperaremos até que refreshTokenSubject tenha um valor não nulo (o novo token de acesso está pronto e podemos tentar novamente a solicitação).
        if (!this.isRefreshing) {
            this.isRefreshing = true;
            this.refreshTokenSubject.next(null);
        
            const token:any = null;//pegar refresh token recebido quando o usuario logou
        
            if (token){
                return this.authService.refreshAuthToken(token).pipe(
                    /**
                     * O switchMap serve para que, por exemplo, se você fizer 5 requisições, 
                     * ele irá processar apenas a última delas, que é a que realmente importa para nós, 
                     * construiremos nosso objeto uma vez e substituiremos o array somente uma vez. 
                     * Resumindo: usando switchMap todos os requests anteriores serão ignorados se um novo evento chegar.
                     */
                    switchMap((token: any) => {
                        this.isRefreshing = false;
            
                        //this.tokenService.saveToken(token.accessToken); salvar access token recebido no storage
                        this.refreshTokenSubject.next(token.accessToken);
                        
                        return next.handle(this.addAuthToken(request, token.accessToken));
                    }),
                    catchError((err) => {
                        //se depois de dar 401 e tentar o refresh token ainda der erro, então faz logout
                        this.isRefreshing = false;
                        
                        this.storageService.signOut();
                        this.router.navigate(['/login']);
                        return throwError(() => new Error(err.message));
                    })
                );
            }
        }
    
        //senão estiver buscando o refreshtoken então a requisição continua pra frente
        return this.refreshTokenSubject.pipe(
            filter(token => token !== null),
            take(1),
            switchMap((token) => next.handle(this.addAuthToken(request, token)))
        );
      }

    addAuthToken(request: HttpRequest<any>, token: string) {
        return request.clone({ headers: request.headers.set(TOKEN_HEADER_KEY, "Bearer " + token) });
    }
}

export const authInterceptorProviders = [
  { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
];