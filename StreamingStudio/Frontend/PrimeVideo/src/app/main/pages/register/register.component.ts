import {Component} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {
  AbstractControl,
  FormBuilder,
  FormControl,
  FormGroup,
  ValidationErrors,
  ValidatorFn,
  Validators
} from '@angular/forms';
import {IUser} from '../../api/models/IUser.model';
import {AuthService} from "../../services/authService/AuthService";
import {INuevaAutorizacionModel} from "../../api/models/INuevaAutorizacion.model";
import {PrimeVideoResourceService} from '../../api/resources/primeVideo-resource.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  showError = false;
  public formRegister!: FormGroup;
  private codigoTransaccion: any;

  // tslint:disable-next-line:max-line-length
  constructor(private router: Router,
              private _fb: FormBuilder,
              private authService: AuthService,
              private primeVideoResourceService: PrimeVideoResourceService,
              private route: ActivatedRoute) {

    function strongPasswordValidator(minLength: number): ValidatorFn {
      return (control: AbstractControl): { [key: string]: any } | null => {
        const value: string = control.value || '';
        const hasUpperCase = /[A-Z]/.test(value);
        const hasLowerCase = /[a-z]/.test(value);
        const hasNumeric = /[0-9]/.test(value);
        const hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(value);
        const isValid = hasUpperCase && hasLowerCase && hasNumeric && hasSpecialChar && value.length >= minLength;

        return isValid ? null : {strongPassword: true};
      }
    }

    function matchValidator(sourceKey: string, targetKey: string): ValidatorFn {
      return (control: AbstractControl): ValidationErrors | null => {
        const sourceControl = control.get(sourceKey);
        const targetControl = control.get(targetKey);

        if (!sourceControl || !targetControl) {
          return null; // form controls are not yet available
        }

        if (targetControl.errors && !targetControl.errors['mustMatch']) {
          return null; // another validator has already found an error
        }

        if (sourceControl.value !== targetControl.value) {
          targetControl.setErrors({mustMatch: true});
        } else {
          targetControl.setErrors(null);
        }

        return null;
      };
    }

    this.formRegister = this._fb.group({
      contrasena: new FormControl('', [Validators.required, strongPasswordValidator(8)]),
      re_contrasena: new FormControl('', [Validators.required, strongPasswordValidator(8)]),
      email: new FormControl('', [Validators.required, Validators.maxLength(255), Validators.email]),
      re_email: new FormControl('', [Validators.required, Validators.maxLength(255), Validators.email]),
      nombre: new FormControl('', [Validators.required, Validators.maxLength(100)]),
      apellido: new FormControl('', [Validators.required, Validators.maxLength(40)]),
      valid: new FormControl(true)
    }, {
      validators: [
        matchValidator('contrasena', 're_contrasena'),
        matchValidator('email', 're_email')
      ]
    });
  }

  onSubmit() {
    if (this.formRegister.valid) {
      const {usuario, contrasena, email, nombre, apellido} = this.formRegister.value;
      const user: IUser = {
        usuario: usuario,
        contrasena: contrasena,
        email: email,
        nombre: nombre,
        apellido: apellido,
        valido: true
      };
      console.log("IUser: ", user);

      this.primeVideoResourceService.registro(user).subscribe(
        (response) => {
          console.log("ILoginResponse: ", response);

          if (response.mensaje === 'Usuario registrado') {
            this.authService.login(response);
            this.showError = false;
            this.route.queryParams.subscribe(params => {
              this.codigoTransaccion = params['codigo_de_transaccion'];
            });

            const body: INuevaAutorizacionModel = {
              codigo_de_transaccion: this.codigoTransaccion,
              id_cliente: response.id_cliente,
            }
            console.log("Body para autorizacion: ", body);

            this.primeVideoResourceService.crear_autorizacion(body).subscribe(
              (response) => {
                console.log("IAutorizacionModel", response);
                window.location.href = response.url_de_redireccion + "?codigo_de_transaccion=" + response.codigo_de_transaccion;
              }
            )
          } else {
            this.showError = true;
          }
        }, (error) => {
          console.error('Error en la solicitud:', error);
        }
      );
    }
  }
}
