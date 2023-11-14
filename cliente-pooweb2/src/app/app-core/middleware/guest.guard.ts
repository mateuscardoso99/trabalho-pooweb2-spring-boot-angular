import { ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot } from "@angular/router";
import { StorageService } from "../service/storage.service";
import { inject } from "@angular/core";
import { PermissaoDto } from "../dto/PermissaoDto";

export const GuestAuthGuard: CanActivateFn = (route: ActivatedRouteSnapshot,state: RouterStateSnapshot) => {
    const storageService = inject(StorageService);
    const router = inject(Router);

    const user = storageService.getUser();
    if (user) {
        if(user.usuario.permissoes.includes(PermissaoDto.CLIENTE))
            router.navigate(['/usuario/inicio'])
        else if(user.usuario.permissoes.includes(PermissaoDto.ADMIN_ESTABELECIMENTO))
            router.navigate(['/estabelecimento/home'])
        return false;
    }
    return true;
}