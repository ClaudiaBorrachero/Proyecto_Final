export interface Categoria {
  name: string;
  icono: string;
  jobs: any[]
}

export interface LoginRespuesta {
  jwt_token?: string;
  timestamp?: Date;
  status?:    number;
  error?:     string;
  trace?:     string;
  message?:   string;
  path?:      string;
}

export interface Usuario {
  email: string;
  password: string;
  firstName?: string;
  lastName?: string;
  phoneNumber?: string;
  // fechaNacimiento?: string;
  location?: string;
}

export interface Anuncio {
  id?:           number;
  titulo?:       string;
  precio?:       number;
  descripcion?:  null | string;
  categoria?:    string;
  finalizado?:   boolean;
  fechaAnuncio?: Date;
  fechaFin?:     null | Date;
  tipoPrecio?:   string;
  autorAnuncio?: Usuario;
  ubicacion?: string;
  comentario?: any;
}
