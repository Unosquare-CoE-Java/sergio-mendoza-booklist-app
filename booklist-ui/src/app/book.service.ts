import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { HttpClient } from  '@angular/common/http';
import { Book } from "./book";
import { environment } from "src/environments/environment";

@Injectable({
    providedIn: 'root'
})
export class BookService {
    private apiServerUrl = environment.apiBaseUrl;

    constructor(private http: HttpClient) {}

    public getAllBooks(): Observable<Book[]> {
        return this.http.get<Book[]>(`${this.apiServerUrl}/books`)
    }

    public saveBook(book: Book): Observable<void> {
        return this.http.post<void>(`${this.apiServerUrl}/books`, book)
    }

    public updateBook(book: Book, bookId: number): Observable<void> {
        return this.http.put<void>(`${this.apiServerUrl}/books/${bookId}`, book)
    }

    public deleteBook(bookId: number): Observable<void> {
        return this.http.put<void>(`${this.apiServerUrl}/books/${bookId}`, '')
    }

    public addCategory(bookId: number, categoryId: number): Observable<void> {
        return this.http.put<void>(`${this.apiServerUrl}/books/${bookId}/category/${categoryId}`, '')
    }
}