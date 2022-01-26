import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router'
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from  '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BookComponent } from './book.component';
import { LoginComponent } from './login.component';
import { SignupComponent } from './signup.component';

const routes: Routes = [
  { path: 'signin', component: LoginComponent },
  { path: 'sign-up', component: SignupComponent },

  // Home component should be listed after all the static routes, the more you know...
  { path: '', component: BookComponent, pathMatch: 'full' },
  
  // otherwise redirect to books, wildcard component must be listed last
  { path: '**', redirectTo: '' }
    
];

@NgModule({
  declarations: [
    AppComponent,
    BookComponent,
    LoginComponent, 
    SignupComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    RouterModule.forRoot(routes)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
