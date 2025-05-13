import { NgModule } from '@angular/core';
import { BrowserModule, withEventReplay } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SobreNosotrosComponent } from './sobre-nosotros/sobre-nosotros.component';
import { HomeComponent } from './home/home.component';
import { RegistroComponent } from './registro/registro.component'; // Importa el componente
import { LoginComponent } from './login/login.component';
import { FormsModule } from '@angular/forms';

import { RouterModule, Routes } from '@angular/router';
import { HomeRegistradoComponent } from './home-registrado/home-registrado.component';
import { MiPerfilComponent } from './mi-perfil/mi-perfil.component';
import { ReservaComponent } from './reserva/reserva.component';
import { ErrorComponent } from './error/error.component';
import { MisCasasComponent } from './mis-casas/mis-casas.component';
import { provideHttpClient } from '@angular/common/http';
const appRoutes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'sobreNosotros', component: SobreNosotrosComponent },
  { path: 'registro', component: RegistroComponent },
  { path: 'login', component: LoginComponent },
  { path: 'homeRegistrado', component: HomeRegistradoComponent },
  { path: 'miPerfil', component: MiPerfilComponent },
  { path: 'reserva', component: ReservaComponent },
  { path: 'error', component: ErrorComponent },
  { path: 'misCasas', component: MisCasasComponent }
];

@NgModule({
  declarations: [
    AppComponent,
    SobreNosotrosComponent,
    HomeComponent,
    RegistroComponent, // Declara el componente aquí
    LoginComponent,
    HomeRegistradoComponent,
    MiPerfilComponent,
    ReservaComponent,
    ErrorComponent,
    MisCasasComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [
    provideHttpClient()
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}