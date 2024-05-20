// auth.service.ts
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PublicationService {
  public publications: any = [];

  constructor() {}

  // Método para realizar el inicio de sesión
  setCurrentPublications(publications: any) {
    localStorage.setItem('publicidades', JSON.stringify(publications));
  }

  getCurrenPublications(): any {
    const storedPublications = localStorage.getItem('publicidades');

    // Check if the item exists in localStorage
    if (storedPublications) {
      try {
        this.publications = JSON.parse(storedPublications);
      } catch (e) {
        console.error('Error parsing JSON from localStorage:', e);
        this.publications = null;  // Or set to a default value
      }
    } else {
      this.publications = null;  // Or set to a default value
    }
    console.log(this.publications);
    return this.publications;
  }
}
