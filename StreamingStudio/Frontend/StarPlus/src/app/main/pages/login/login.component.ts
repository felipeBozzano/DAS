import { Component } from '@angular/core';
import { Router } from '@angular/router';
import {AuthService} from '../../services/authService/AuthService';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {ILogin } from '../../api/models/login.model';
import {Star_plusResourceService} from '../../api/resources/star_plus-resource.service';
import {StarPlusResourceService} from '../../api/resources/starplus/service/starplus.service';
import {HttpParams} from '@angular/common/http';
import {catchError, map} from 'rxjs';


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
              private netflixResourceService: Star_plusResourceService,
              private  starPlusResourceService: StarPlusResourceService)
              { this.formLogin = this._fb.group({
                usuario: new FormControl('',[Validators.required, Validators.maxLength(255)]),
                contrasena: new FormControl('',[Validators.required, Validators.maxLength(255)])
              }) }

  // tslint:disable-next-line:typedef
  onSubmit() {
    if (this.formLogin.valid) {
      const { email, contrasena } = this.formLogin.value;
      const soapRequest = `
        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns="http://platforms.streamingstudio.das.ubp.edu.ar/">
          <soapenv:Header/>
          <soapenv:Body>
            <ns:login>
              <email>${email}</email>
              <contrasena>${contrasena}</contrasena>
            </ns:login>
          </soapenv:Body>
        </soapenv:Envelope>`;

      fetch("http://localhost:8084/star_plus", {
        method: "POST",
        body: soapRequest,
        headers: { "Content-type": "text/xml; charset=utf-8", "SOAPAction": "http://localhost:8084/star_plus/login" }
      }).then(response => response.text())
        .then(xml => {
          let parser = new DOMParser();
          let xmlDoc = parser.parseFromString(xml, 'text/xml');
          console.info(xmlDoc.querySelector("NewDataSet"));
          console.info("=====================");
          console.info(xmlDoc.querySelectorAll("Error")[1].innerHTML);
        }).catch (err => console.log(err));
      /*
      this.netflixResourceService.login(soapRequest).subscribe(
        (response) => {
          console.log(response);
          // Procesar la respuesta SOAP
          const parser = new DOMParser();
          const xmlDoc = parser.parseFromString(response, 'text/xml');
          const mensaje = xmlDoc.getElementsByTagName('mensaje')[0].textContent;

          // Si la respuesta es exitosa, redirige al home
          if (mensaje === 'Usuario existente') {
            console.log("Login exitoso");
            this.showError = false;
            this.router.navigate(['/home']);
          } else {
            this.showError = true;
          }
        },
        (error) => {
          // Manejo de errores
          console.error('Error en la solicitud:', error);
          this.showError = true;
        }
      );*/
    }
  }

  navigateToRegister() {
    this.router.navigate(['/register']); // Ruta para el componente de registro
  }
}
