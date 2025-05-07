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





//Creamos la variable const routes para establecer las rutas
const appRoutes:Routes=[
 
  //Lo que haremos aqui es crear un objeto para cada ruta
 
  //Como la creamos, con el path como en python y la ubicacion, ya que son componentes -> component
  {path:'', component:HomeComponent},
  {path:'sobreNosotros', component:SobreNosotrosComponent},
  {path:'registro', component:RegistroComponent},
  {path:'login', component:LoginComponent},
  //Esto le indica a angular que cualquier cosa diferente a lo visto antes, irá aqui
 
 
];

@NgModule({
  declarations: [
    AppComponent,
    SobreNosotrosComponent,
    HomeComponent,
    RegistroComponent,
    LoginComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,

    //Para las rutas, para que utilice todas las rutas presentes en la constante
    RouterModule.forRoot(appRoutes),

  ],
  providers: [
    provideClientHydration(withEventReplay())
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
