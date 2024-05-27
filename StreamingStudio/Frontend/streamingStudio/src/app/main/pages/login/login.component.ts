import { Component } from '@angular/core';
import { Router } from '@angular/router';
import {AuthService} from '../../services/authService/AuthService';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {StreamingStudioResources} from '../../api/resources/streaming-studio.services';
import {PublicationService} from '../../services/publicationService/publicationService';


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
              private streamingStudioResources: StreamingStudioResources,
              private publicidadesService: PublicationService)
              { this.formLogin = this._fb.group({
                email: new FormControl('',[Validators.required, Validators.maxLength(255), Validators.email]),
                contrasena: new FormControl('',[Validators.required, Validators.maxLength(255)])
              }) }

  // tslint:disable-next-line:typedef
  onSubmit() {
    if (this.formLogin.valid) {
      console.log(this.formLogin.value);
      this.streamingStudioResources.login(this.formLogin.value)
        .subscribe(
          (response) => {
            console.log(response);
            const clienteId = response.id_cliente;
            this.routeHome = `/home/${clienteId}`;
            // Si la respuesta es exitosa, redirige al home
            if (response.mensaje === 'Usuario existente') {
              this.authService.login(response);
              this.showError = false;
              // obtengo las publicidades
              this.streamingStudioResources.publicidades().subscribe( (response) => {
                  this.publicidadesService.setCurrentPublications(Object.values(response.Publicidades).flat())
              })
              this.router.navigate(['/home'], { queryParams: {id_cliente: clienteId }});
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
