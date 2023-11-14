import { ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot } from "@angular/router";
import { StorageService } from "../service/storage.service";
import { inject } from "@angular/core";

export const AuthGuard: CanActivateFn = (route: ActivatedRouteSnapshot,state: RouterStateSnapshot) => {
    const storageService = inject(StorageService);
    const router = inject(Router);

    const user = storageService.getUser();
    if (user && user.usuario.permissoes.includes(route.data.permission)) {
        return true;
    }
    router.navigate(['/login'])
    return false;
}