import { Component } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {AbstractControl, FormBuilder, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators} from '@angular/forms';
import {IUser} from '../../api/models/IUser.model';
import {Star_plusResourceService} from '../../api/resources/star_plus-resource.service';
import {nextMonthDisabled} from '@ng-bootstrap/ng-bootstrap/datepicker/datepicker-tools';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  public formRegister!: FormGroup;

  // tslint:disable-next-line:max-line-length
  constructor(private http: HttpClient, private router: Router, private _fb: FormBuilder,  private netflixResourceService: Star_plusResourceService ) {

    function strongPasswordValidator(minLength: number): ValidatorFn {
      return (control: AbstractControl): { [key: string]: any } | null => {
        const value: string = control.value || '';
        const hasUpperCase = /[A-Z]/.test(value);
        const hasLowerCase = /[a-z]/.test(value);
        const hasNumeric = /[0-9]/.test(value);
        const hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(value);
        const isValid = hasUpperCase && hasLowerCase && hasNumeric && hasSpecialChar && value.length >= minLength;

        return isValid ? null : {strongPassword: true};
      };
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
      contrasena: new FormControl('', [Validators.required, Validators.maxLength(255)]),
      re_contrasena: new FormControl('', [Validators.required, Validators.maxLength(255)]),
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
    })
  }

  // tslint:disable-next-line:typedef
  async onSubmit() {
    if (this.formRegister.valid) {
      const {email, contrasena, nombre, apellido} = this.formRegister.value;
      const user: any = {
        email: email,
        contrasena: contrasena,
        nombre: nombre,
        apellido: apellido,
        valido: true
      };

      this.netflixResourceService.registro(user)
        .subscribe( {
          next: ()=>
          {
            // Si la respuesta es exitosa, redirige al home
            this.router.navigate(['/login']);
          }
        ,
        error: (error) => {
          // Si hay un error en la respuesta, muestra un mensaje de error
          console.error('Error en la solicitud:', error);
          throw error
        }
      });
    }
  }
}
