export interface IInformacionContenidoResponseModel {
  infoContenido:{
    url_imagen: string,
    titulo: string,
    descripcion: string
  },
  genero: {
    descripcion: string
  },
  directores: Array<{
      nombre: string,
      apellido: string
  }>,
  actores: Array<{
    nombre: string,
    apellido: string
  }>,
  plataformas: any
}
