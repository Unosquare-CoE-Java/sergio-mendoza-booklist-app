import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BookComponent } from './book.component';
import { LoginComponent } from './login.component';
import { SignupComponent } from './signup.component';

const routes: Routes = [
  { path: 'signin', component: LoginComponent },
  { path: 'sign-up', component: SignupComponent },

  // Home component should be listed after all the static routes, the more you know...
  { path: '', component: BookComponent },
  
  // otherwise redirect to books, wildcard component must be listed last
  { path: '**', redirectTo: 'signin' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
