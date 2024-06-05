import { Pipe, PipeTransform } from '@angular/core';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';

@Pipe({
  name: 'safe'
})
export class SafePipe implements PipeTransform {

  constructor(private sanitizer: DomSanitizer) {}

  transform(value: string, type: string): SafeResourceUrl {
    switch (type) {
      case 'resourceUrl':
        return this.sanitizer.bypassSecurityTrustResourceUrl(value);
      default:
        return this.sanitizer.bypassSecurityTrustResourceUrl(value);
    }
  }

}
