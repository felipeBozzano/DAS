import { Component } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService} from '../../services/authService/AuthService';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Star_plusResourceService} from '../../api/resources/star_plus-resource.service';
import {StarPlusResourceService} from '../../api/resources/starplus/service/starplus.service';



@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  showError = false;
  public formLogin!: FormGroup;
  private autorizacion: any;
  public codigoTransaccion: string = '';

  constructor(private router: Router,
              private authService: AuthService,
              private _fb: FormBuilder,
              private route: ActivatedRoute,
              private  starPlusResourceService: Star_plusResourceService)
              { this.formLogin = this._fb.group({
                email: new FormControl('',[Validators.required, Validators.maxLength(255)]),
                contrasena: new FormControl('',[Validators.required, Validators.maxLength(255)])
              }) }

  // tslint:disable-next-line:typedef
  onSubmit() {
    if (this.formLogin.valid) {
      const { email, contrasena } = this.formLogin.value;
      const user = {
        email: email,
        contrasena: contrasena
      }
      this.starPlusResourceService.login(user).subscribe(
        (response) => {
          // Si la respuesta es exitosa, redirige al home
          if (response.valido === "true") {
            console.log("Login exitoso");
            this.showError = false;
            this.route.queryParams.subscribe(params => {
              this.codigoTransaccion = params['codigo_de_transaccion'];
            });
            const body: any = {
              codigo_de_transaccion:  this.codigoTransaccion,
              id_cliente: response.id_cliente,
            }
            console.log("body: ", body);
            this.starPlusResourceService.crear_autorizacion(body).subscribe(
              (response) => {
                console.log(response);
                window.location.href = response.url_de_redireccion + "?codigo_de_transaccion=" + response.codigo_de_transaccion;
              }
            )
          } else {
            this.showError = true;
          }
        },
        (error) => {
          // Manejo de errores
          console.error('Error en la solicitud:', error);
          this.showError = true;
        }
      );
    }
  }

  navigateToRegister() {
    this.router.navigate(['/register']); // Ruta para el componente de registro
  }
}
