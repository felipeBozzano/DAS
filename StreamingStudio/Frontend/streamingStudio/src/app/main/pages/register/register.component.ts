import { Component } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {AbstractControl, FormBuilder, FormControl, FormGroup, ValidatorFn, Validators} from '@angular/forms';
import {IUser} from '../../api/models/IUser.model';
import {StreamingStudioResources} from '../../api/resources/streaming-studio.services';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  public formRegister!: FormGroup;

  constructor(private http: HttpClient, private router: Router, private _fb: FormBuilder,  private streamingStudioResources: StreamingStudioResources ) {

     function strongPasswordValidator(minLength: number): ValidatorFn {
      return (control: AbstractControl): { [key: string]: any } | null => {
        const value: string = control.value || '';
        const hasUpperCase = /[A-Z]/.test(value);
        const hasLowerCase = /[a-z]/.test(value);
        const hasNumeric = /[0-9]/.test(value);
        const hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(value);
        const isValid = hasUpperCase && hasLowerCase && hasNumeric && hasSpecialChar && value.length >= minLength;

        return isValid ? null : { 'strongPassword': true };
      };
    }

    this.formRegister = this._fb.group({
      usuario: new FormControl('',[Validators.required, Validators.maxLength(16)]),
      contrasena: new FormControl('',[Validators.required, strongPasswordValidator(8)]),
      email: new FormControl('',[Validators.required, Validators.maxLength(16),Validators.email]),
      nombre: new FormControl('',[Validators.required, Validators.maxLength(16)]),
      apellido: new FormControl('',[Validators.required, Validators.maxLength(16)]),
    })
  }

  async onSubmit() {
    if (this.formRegister.valid) {
      const {usuario, contrasena, email, nombre, apellido} = this.formRegister.value
      const user: IUser = {
        usuario: usuario,
        contrasena: contrasena,
        email: email,
        nombre: nombre,
        apellido: apellido,
        valido: true
      }
      this.streamingStudioResources.registro(user)
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
}
