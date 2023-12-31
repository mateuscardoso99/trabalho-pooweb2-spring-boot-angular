import { ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot } from "@angular/router";
import { StorageService } from "../service/storage.service";
import { inject } from "@angular/core";
import { TokenDto } from "../dto/TokenDto";
import { Permissao } from "../dto/UsuarioDto";

export const AuthGuard: CanActivateFn = (route: ActivatedRouteSnapshot,routeState: RouterStateSnapshot) => {
    const storageService = inject(StorageService);
    const router = inject(Router);

    const user: TokenDto = storageService.getUser() as TokenDto;

    if(user){
        if(routeState.url.includes("/login") || routeState.url.includes("/signup")){
            if(user.usuario.permissoes.includes(Permissao.CLIENTE)){
                router.navigate(['/usuario/inicio'])
            }
            else if(user.usuario.permissoes.includes(Permissao.ADMIN_ESTABELECIMENTO)){
                router.navigate(['/estabelecimento/home'])
            }
            return false;
        }
        else{
            if(!user.usuario.permissoes.includes(route.data.permission)){
                router.navigate(['/login'])
                return false;
            }
            return true;
        }
    }
    else{
        if(!routeState.url.includes("/login") && !routeState.url.includes("/signup") && routeState.url != "/"){
            router.navigate(['/login'])
            return false;
        }
        return true;
    }
}