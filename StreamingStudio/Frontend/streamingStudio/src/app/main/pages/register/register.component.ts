import { Component } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  usuario: string = "";
  contrasena: string = "";
  email: string = "";
  nombre: string = "";
  apellido: string = "";
  valido: boolean = true;

  constructor(private http: HttpClient, private router: Router ) { }

  crearCuenta() {
    const body = { usuario: this.usuario, contrasena: this.contrasena, email: this.email, nombre: this.nombre, apellido: this.apellido, valio: this.valido };
    return this.http.post<any>('http://localhost:8080/ss/create_user', body);
  }

  onSubmit() {
    this.crearCuenta()
      .subscribe(
      (response) => {
        // Si la respuesta es exitosa, redirige al home
        console.log('Respuesta del servidor:', response);
        this.router.navigate(['/login']);
      },
      (error) => {
        // Si hay un error en la respuesta, muestra un mensaje de error
        console.error('Error en la solicitud:', error);
        // Aquí puedes manejar el error y mostrar un mensaje de error al usuario
      }
    );
  }
}
