import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../AuthService';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  currentUser: any;
  client_id: string = ''

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
    this.currentUser = this.authService.getCurrentUser();
    this.client_id = this.currentUser.id_cliente;
    console.log("user: ", this.currentUser);
    console.log("client_id: ", this.client_id);
  }

}
