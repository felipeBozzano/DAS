import { Component } from '@angular/core';
import { Router } from '@angular/router';
import {AuthService} from '../../AuthService';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {ILogin } from '../../api/models/login.model';
import {StreamingStudioResources} from '../../api/resources/streaming-studio.services';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username: string = "";
  password: string = "";
  showError = false;
  public formLogin!: FormGroup;

  constructor(private router: Router,
              private authService: AuthService,
              private _fb: FormBuilder,
              private streamingStudioResources: StreamingStudioResources)
              { this.formLogin = this._fb.group({
                usuario: new FormControl('',[Validators.required, Validators.maxLength(16)]),
                contrasena: new FormControl('',[Validators.required, Validators.maxLength(16)])
              }) }

  // tslint:disable-next-line:typedef
  async onSubmit() {
    // this.spinnerService.show();
    // setTimeout(() => {
    if (this.formLogin.valid) {
      const { usuario, contrasena } = this.formLogin.value
      const login: ILogin = {
        usuario: usuario,
        contrasena: contrasena
      }

      console.log(login);
      this.streamingStudioResources.login(login)
        .subscribe(
          (response) => {
            // Si la respuesta es exitosa, redirige al home
            console.log('Respuesta del servidor:', response);
            if (response.mensaje === 'Usuario existente') {
              this.authService.login();
              this.showError = false;
              // this.spinnerService.hide();
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
      // }, 2000);
    }
  }

  navigateToRegister() {
    this.router.navigate(['/register']); // Ruta para el componente de registro
  }
}
