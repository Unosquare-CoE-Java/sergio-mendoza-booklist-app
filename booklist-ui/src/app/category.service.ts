import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { HttpClient } from  '@angular/common/http';
import { Category } from "./category";
import { environment } from "src/environments/environment";

@Injectable({
    providedIn: 'root'
})
export class CategoryService {
    private apiServerUrl = environment.apiBaseUrl;

    constructor(private http: HttpClient) {}

    public getAllCategories(): Observable<Category[]> {
        return this.http.get<Category[]>(`${this.apiServerUrl}/categories`)
    }

    public saveCategory(category: Category): Observable<void> {
        return this.http.post<void>(`${this.apiServerUrl}/categories`, category)
    }

    public deleteCategory(categoryId: number): Observable<void> {
        return this.http.put<void>(`${this.apiServerUrl}/categories/${categoryId}`, '')
    }
}