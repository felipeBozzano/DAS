import { Component } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService} from '../../services/authService/AuthService';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {ILogin } from '../../api/models/login.model';
import {NetflixResourceService} from '../../api/resources/netflix-resource.service';
import {INuevaAutorizacionModel} from '../../api/models/INuevaAutorizacion.model';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  showError = false;
  public formLogin!: FormGroup;
  public routeHome: string = '';
  private autorizacion: any;
  private codigoTransaccion: any;

  constructor(private router: Router,
              private authService: AuthService,
              private _fb: FormBuilder,
              private netflixResourceService: NetflixResourceService,
              private route: ActivatedRoute)
              { this.formLogin = this._fb.group({
                email: new FormControl('',[Validators.required, Validators.maxLength(255), Validators.email]),
                contrasena: new FormControl('',[Validators.required, Validators.maxLength(255)])
              }) }

  ngOnInit(): void {
    this.route.data.subscribe(data => {
      this.autorizacion = data['autorizacion'];
      console.log(this.autorizacion);
    })
  }

  // tslint:disable-next-line:typedef
  onSubmit() {
    if (this.formLogin.valid) {
      const { email, contrasena } = this.formLogin.value
      const login: ILogin = {
        email: email,
        contrasena: contrasena,
      }

      console.log(login);
      this.netflixResourceService.login(login)
        .subscribe(
          (response) => {
            console.log("response: ", response);
            // Si la respuesta es exitosa, redirige al home
            if (response.mensaje === 'Usuario existente') {
              this.authService.login(response);
              this.showError = false;
              this.route.queryParams.subscribe(params => {
                this.codigoTransaccion = params['codigo_de_transaccion'];
              });
              const body: INuevaAutorizacionModel = {
                codigo_de_transaccion:  this.codigoTransaccion,
                id_cliente: response.id_cliente,
              }
              console.log("body: ", body);
              this.netflixResourceService.crear_autorizacion(body).subscribe(
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
