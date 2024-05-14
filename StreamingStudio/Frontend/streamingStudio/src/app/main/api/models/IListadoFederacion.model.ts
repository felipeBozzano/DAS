export interface IListadoFederaciones {
  plataformas_a_Federar: Array<{
    id_plataforma: string;
    url_imagen: string;
    url_api: string;
  }>,
  Plataformas_Federadas: Array<{
    id_plataforma: string;
    url_imagen: string;
    url_api: string;
  }>
}
