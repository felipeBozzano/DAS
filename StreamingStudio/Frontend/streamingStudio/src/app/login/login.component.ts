import { Component } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { Router } from '@angular/router';
import {SpinnerService} from '../SpinnerService';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username: string;
  password: string;
  showError = false;

  constructor(private http: HttpClient, private router: Router, public spinnerService: SpinnerService ) { }

  // tslint:disable-next-line:typedef
  login(usuario: string, contraseña: string) {
    const body = { usuario: this.username, contrasena: this.password };
    return this.http.post<any>('http://localhost:8080/ss/login_user', body);
  }

  // tslint:disable-next-line:typedef
  onSubmit() {
    //this.spinnerService.show();
    //setTimeout(() => {
      this.login(this.username, this.password)
        .subscribe(
          (response) => {
            // Si la respuesta es exitosa, redirige al home
            console.log('Respuesta del servidor:', response);
            if (response.mensaje == "Usuario existente") {
              this.showError = false;
              //this.spinnerService.hide();
              this.router.navigate(['/home']);
            } else {
              this.showError = true;
            }
          },
          (error) => {
            // Si hay un error en la respuesta, muestra un mensaje de error
            console.error('Error en la solicitud:', error);
            // Aquí puedes manejar el error y mostrar un mensaje de error al usuario
          }
        );
    //}, 2000);
  }

  navigateToRegister() {
    this.router.navigate(['/register']); // Ruta para el componente de registro
  }
}
