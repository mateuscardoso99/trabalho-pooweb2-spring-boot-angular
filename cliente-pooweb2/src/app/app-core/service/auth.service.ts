import { HttpClient } from "@angular/common/http";
import { environment } from "src/environments/environment";
import { CadastroCliente } from "../request/CadastroCliente";
import { LoginRequest } from "../request/LoginRequest";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable({ providedIn: 'root' })
export class AuthService{
    constructor(private http: HttpClient){}

    register(dados: CadastroCliente){
        return this.http.post(environment.urlApi + "/register",dados);
    }

    login(dados: LoginRequest): Observable<any>{
        return this.http.post(environment.urlApi + "/login",dados);
    }

    refreshAuthToken(token: string){
        return this.http.post(environment.urlApi + "/refresh-token",token);
    }
}