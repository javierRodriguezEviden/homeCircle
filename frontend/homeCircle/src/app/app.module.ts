import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration, withEventReplay } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SobreNosotrosComponent } from './sobre-nosotros/sobre-nosotros.component';
import { HomeComponent } from './home/home.component';
import { RegistroComponent } from './registro/registro.component';
import { LoginComponent } from './login/login.component';
import { FormsModule } from '@angular/forms';

//Import para routes
import { RouterModule, Routes } from '@angular/router';
import { HomeRegistradoComponent } from './home-registrado/home-registrado.component';
import { MiPerfilComponent } from './mi-perfil/mi-perfil.component';
import { ReservaComponent } from './reserva/reserva.component';
import { ErrorComponent } from './error/error.component';
import { MisCasasComponent } from './mis-casas/mis-casas.component';

import { provideHttpClient } from '@angular/common/http';


//Creamos la variable const routes para establecer las rutas
const appRoutes:Routes=[
 
  //Lo que haremos aqui es crear un objeto para cada ruta
 
  //Como la creamos, con el path como en python y la ubicacion, ya que son componentes -> component
  {path:'', component:HomeComponent},
  {path:'sobreNosotros', component:SobreNosotrosComponent},
  {path:'registro', component:RegistroComponent},
  {path:'login', component:LoginComponent},
  {path:'homeRegistrado', component:HomeRegistradoComponent},
  {path:'miPerfil', component:MiPerfilComponent},
  {path:'reserva', component:ReservaComponent},
  {path:'error', component:ErrorComponent},
  {path:'misCasas', component:MisCasasComponent}

  //Esto le indica a angular que cualquier cosa diferente a lo visto antes, irá aqui
 
];

@NgModule({
  declarations: [
    AppComponent,
    SobreNosotrosComponent,
    HomeComponent,
    RegistroComponent,
    LoginComponent,
    HomeRegistradoComponent,
    MiPerfilComponent,
    ReservaComponent,
    ErrorComponent,
    MisCasasComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,

    //Para las rutas, para que utilice todas las rutas presentes en la constante
    RouterModule.forRoot(appRoutes),


  ],
  providers: [
    provideHttpClient(),
    provideClientHydration(withEventReplay())
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
