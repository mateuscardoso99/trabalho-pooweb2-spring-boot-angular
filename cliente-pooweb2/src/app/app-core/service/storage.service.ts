import { Injectable } from "@angular/core";
import { TokenDto } from "../dto/TokenDto";

const USER_KEY = 'auth-user';

@Injectable({ providedIn: 'root' })
export class StorageService{
    signOut(): void {
        window.sessionStorage.clear();
    }

    public saveUser(user: TokenDto): void {
        this.removeUser();
        window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
    }

    public removeUser(): void{
        window.sessionStorage.removeItem(USER_KEY);
    }

    public getUser(): TokenDto | null {
        const user = window.sessionStorage.getItem(USER_KEY);
        if (user) {
          return JSON.parse(user);
        }
        return null;
    }
}