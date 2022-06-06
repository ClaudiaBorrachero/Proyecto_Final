import { Byte } from "@angular/compiler/src/util";

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
  title?:       string;
  price?:       number;
  description?:  null | string;
  category?:    string;
  finished?:   boolean;
  jobDate?: Date;
  // fechaFin?:     null | Date;
  // tipoPrecio?:   string;
  file?: Byte[];
  user_id?: Usuario;
  location?: string;
}
