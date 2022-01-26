import { Category } from "./category";

export interface Book {
    id: number,
    name: string,
    description: string,
    publisher: string,
    author: string,
    isbn: string
    publishedDate: Date, // is this format valid?
    categories: Category[]
}