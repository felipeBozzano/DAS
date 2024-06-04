import {IClasificacion} from "./IClasificacion.model";
import {IGenero} from "./IGenero.model";

export interface IContenido {
  id_cliente: number,
  titulo: string,
  reciente: string,
  destacado: string,
  mas_visto: string,
  clasificacion: string,
  generos: IGenero
}
