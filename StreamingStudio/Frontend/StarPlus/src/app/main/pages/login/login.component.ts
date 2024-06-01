import { Component } from '@angular/core';
import { Router } from '@angular/router';
import {AuthService} from '../../services/authService/AuthService';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {ILogin } from '../../api/models/login.model';
import {Star_plusResourceService} from '../../api/resources/star_plus-resource.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  showError = false;
  public formLogin!: FormGroup;
  public routeHome: string = '';

  constructor(private router: Router,
              private authService: AuthService,
              private _fb: FormBuilder,
              private netflixResourceService: Star_plusResourceService)
              { this.formLogin = this._fb.group({
                usuario: new FormControl('',[Validators.required, Validators.maxLength(255)]),
                contrasena: new FormControl('',[Validators.required, Validators.maxLength(255)])
              }) }

  // tslint:disable-next-line:typedef
  onSubmit() {
    if (this.formLogin.valid) {
      const { usuario, contrasena } = this.formLogin.value
      const login: ILogin = {
        usuario: usuario,
        contrasena: contrasena,
      }
      const soapRequest = `
        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns="http://platforms.streamingstudio.das.ubp.edu.ar/">
          <soapenv:Header/>
          <soapenv:Body>
            <ns:login>
              <email>${usuario}</email>
              <contrasena>${contrasena}</contrasena>
            </ns:login>
          </soapenv:Body>
        </soapenv:Envelope>`;

      this.netflixResourceService.login(soapRequest)
        .subscribe(
          (response) => {
            // Si la respuesta es exitosa, redirige al home
            if (response.mensaje === 'Usuario existente') {
              console.log("kakakakaka");
              this.authService.login(response);
              this.showError = false;
              // obtengo las publicidades
              console.log(this.routeHome);
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
    }
  }

  navigateToRegister() {
    this.router.navigate(['/register']); // Ruta para el componente de registro
  }
}
