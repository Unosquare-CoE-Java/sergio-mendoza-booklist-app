import { Book } from "./book"
import { Authority } from "./authority"

export interface User {
    id: number,
    firtsName: string,
    lastName: string,
    country: string,
    registrationDate: Date, //is this valid?
    username: string,
    password: string,
    authorities: Authority[],
    books: Book[]
}