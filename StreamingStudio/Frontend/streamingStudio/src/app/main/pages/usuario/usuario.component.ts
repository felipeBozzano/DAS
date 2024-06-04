import { Component, OnInit } from '@angular/core';
import {StreamingStudioResources} from '../../api/resources/streaming-studio.services';
import {ActivatedRoute} from '@angular/router';
import {IFinalizarFederacion} from '../../api/models/IFinalizarFederacion.model';
import {AuthService} from '../../services/authService/AuthService';
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-usuario',
  templateUrl: './usuario.component.html',
  styleUrls: ['./usuario.component.css']
})
export class UsuarioComponent implements OnInit {
  public currentUser: any;
  id_cliente: any;

  constructor(private route: ActivatedRoute, private authService: AuthService, private streamingStudioResources: StreamingStudioResources) { }
  usuarioForm!: FormGroup

  ngOnInit(): void {
    this.id_cliente = this.route.snapshot.paramMap.get('id_cliente');
    this.currentUser = this.authService.getCurrentUser();
    console.log("this.currentUser: ", this.currentUser);
    this.usuarioForm = new FormGroup({
      nombre: new FormControl(this.currentUser.nombre, [ Validators.maxLength(255)]),
      apellido: new FormControl(this.currentUser.apellido, [ Validators.maxLength(255)]),
      email: new FormControl({ value: this.currentUser.email, disabled: true })
    });
  }

  onSubmit() {
    if (this.usuarioForm.valid) {
      this.currentUser.nombre = this.usuarioForm.value.nombre;
      this.currentUser.apellido = this.usuarioForm.value.apellido;
      const newUser = {
        nombre: this.usuarioForm.value.nombre,
        apellido: this.usuarioForm.value.apellido,
        id_cliente : this.currentUser.id_cliente
      }
      console.log('Usuario actualizado:', this.currentUser);
      this.streamingStudioResources.actualizar_usuario(newUser).subscribe(response =>{
        console.log(response)
      })
    }
  }
}
