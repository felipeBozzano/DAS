import { Component } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService} from '../../services/authService/AuthService';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {ILogin } from '../../api/models/login.model';
import {NetflixResourceService} from '../../api/resources/netflix-resource.service';


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
                usuario: new FormControl('',[Validators.required, Validators.maxLength(16)]),
                contrasena: new FormControl('',[Validators.required, Validators.maxLength(16)])
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
      const { usuario, contrasena } = this.formLogin.value
      const login: ILogin = {
        usuario: usuario,
        contrasena: contrasena,
      }

      console.log(login);
      this.netflixResourceService.login(login)
        .subscribe(
          (response) => {
            // Si la respuesta es exitosa, redirige al home
            if (response.mensaje === 'Usuario existente') {
              this.authService.login(response);
              this.showError = false;
              this.route.queryParams.subscribe(params => {
                this.codigoTransaccion = params['codigo_transaccion'];
              });
              this.netflixResourceService.crear_autorizacion(this.codigoTransaccion, response.id_cliente).subscribe(
                (response) => {
                  console.log(response);
                  window.location.href = "http://localhost:4200/usuario"+ "?codigo_de_transaccion=" + response.codigo_de_transaccion + "&ic_cliente=" + response.id_cliente;
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
