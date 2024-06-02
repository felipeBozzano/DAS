import { Component } from '@angular/core';
import { Router } from '@angular/router';
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
  public routeHome: string = '';

  constructor(private router: Router,
              private authService: AuthService,
              private _fb: FormBuilder,
              private netflixResourceService: Star_plusResourceService,
              private  starPlusResourceService: StarPlusResourceService)
              { this.formLogin = this._fb.group({
                email: new FormControl('',[Validators.required, Validators.maxLength(255)]),
                contrasena: new FormControl('',[Validators.required, Validators.maxLength(255)])
              }) }

  // tslint:disable-next-line:typedef
  onSubmit() {
    if (this.formLogin.valid) {
      const { email, contrasena } = this.formLogin.value;
      const soapRequest = `
        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:plat="http://platforms.streamingstudio.das.ubp.edu.ar/">
           <soapenv:Header/>
           <soapenv:Body>
           <init-param>
              <param-name>cors.allowOrigin</param-name>
              <param-value>*</param-value>
            </init-param>
            <init-param>
              <param-name>cors.supportedMethods</param-name>
              <param-value>GET, POST, HEAD, OPTIONS</param-value>
            </init-param>
              <plat:login>
                 <!--Optional:-->
                 <email>${email}</email>
                 <!--Optional:-->
                 <contrasena>${contrasena}</contrasena>
              </plat:login>
           </soapenv:Body>
        </soapenv:Envelope>`;

      console.log(soapRequest);


      fetch("http://localhost:8084/echo/star_plus", {
        method: "POST",
        //mode: "no-cors",
        body: soapRequest,
        headers: { "Content-type": "text/xml; charset=utf-8", "SOAPAction": "http://platforms.streamingstudio.das.ubp.edu.ar/login" }
      }).then(response => response.text())
        .then(xml => {
          let parser = new DOMParser();
          let xmlDoc = parser.parseFromString(xml, 'text/xml');
          console.log(xmlDoc);
          console.info(xmlDoc.querySelector("NewDataSet"));
          console.info("=====================");
          console.info(xmlDoc.querySelectorAll("Error")[1].innerHTML);
        }).catch (err => console.log(err));


      /*
      fetch("http://localhost:8084/star_plus/login", {
        method: "POST",
        mode: "no-cors",
        body: soapRequest,
        headers: { "Content-type": "text/xml; charset=utf-8", "SOAPAction": "http://platforms.streamingstudio.das.ubp.edu.ar/login" }
      }).then(response => {
        // Verificar el estado de la respuesta
        if (response.ok) {
          return response.text(); // Devolver el texto de la respuesta si está ok
        } else {
          throw new Error("Error en la solicitud SOAP");
        }
      }).then(text => {
        // Acceder al texto de la respuesta
        console.log("Texto de la respuesta:", text);

        // A partir de aquí, puedes manipular el texto de la respuesta según sea necesario
        // Si la respuesta es XML, puedes parsearla usando DOMParser o alguna otra librería XML
      }).catch(err => console.log(err));
      */

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
