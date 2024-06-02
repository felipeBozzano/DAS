import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class StarPlusResourceService {

  private apiUrl = 'http://localhost:8084/star_plus'; // Reemplaza con la URL de tu servicio SOAP

  constructor(private http: HttpClient) { }

  login(usuario: string, contrasena: string): Observable<any> {
    const soapRequest = `
      <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns="http://platforms.streamingstudio.das.ubp.edu.ar/">
        <soapenv:Header/>
        <soapenv:Body>
          <plat:login>
            <ns:email>${usuario}</ns:email>
            <ns:contrasena>${contrasena}</ns:contrasena>
          </plat:login>
        </soapenv:Body>
      </soapenv:Envelope>`;

    const headers = new HttpHeaders({
      'Content-Type': 'text/xml',
      'Access-Control-Allow-Origin': '*'
    });

    return this.http.post(this.apiUrl, soapRequest, { headers, responseType: 'text' });
  }
}
