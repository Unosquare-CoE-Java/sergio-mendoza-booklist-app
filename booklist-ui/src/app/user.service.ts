import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { HttpClient } from  '@angular/common/http';
import { User } from "./user";
import { Authority } from "./authority";
import { Login } from "./login";
import { environment } from "src/environments/environment";

@Injectable({
    providedIn: 'root'
})
export class UserService {
    private apiServerUrl = environment.apiBaseUrl;

    constructor(private http: HttpClient) {}

    public getAllUsers(): Observable<User[]> {
        return this.http.get<User[]>(`${this.apiServerUrl}/users`)
    }

    public saveUser(user: User): Observable<void> {
        return this.http.post<void>(`${this.apiServerUrl}/sign-up`, user)
    }

    public updateUser(user: User, userId: number): Observable<void> {
        return this.http.put<void>(`${this.apiServerUrl}/users/${userId}`, user)
    }

    public addAuthority(authority: Authority, userId: number): Observable<void> {
        return this.http.put<void>(`${this.apiServerUrl}/users/${userId}/permissions`, authority)
    }

    public deleteUser(userId: number): Observable<void> {
        return this.http.put<void>(`${this.apiServerUrl}/users/${userId}`, '')
    }

    public addBook(userId: number, bookId: number): Observable<void> {
        return this.http.put<void>(`${this.apiServerUrl}/users/${userId}/book/${bookId}`, '')
    }

    public authenticateUser(userLogin: Login): Observable<void> {
        return this.http.post<void>(`${this.apiServerUrl}/signin`, userLogin)
    }
}