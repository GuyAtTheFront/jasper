import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UploadService {

  URL = "/api/image"

  constructor( private httpClient: HttpClient ) { }

  postFormData(data: FormData){
    return this.httpClient.post(this.URL, data);
  }
}
