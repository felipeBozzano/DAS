import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-splash',
  templateUrl: './splash.component.html',
  styleUrls: ['./splash.component.css']
})
export class SplashComponent implements OnInit {

  constructor( private _router: Router) { }

  ngOnInit(): void {
    setTimeout(() => {
      this.saltar()
    }, 3000);
  }

  saltar(){
    this._router.navigate(['/login'])
  }
}
