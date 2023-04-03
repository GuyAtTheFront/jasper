import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  constructor() { }

  public hasKey(key: string) : Boolean{
    return sessionStorage.getItem(key) !== null;
  }

  public getItem(key: string) : string | null {
    return sessionStorage.getItem(key);
  }

  public setItem(key: string, value: string) : void {
    sessionStorage.setItem(key, value);
    return;
  }

  public removeItem(key: string) : void {
    sessionStorage.removeItem(key);
    return;
  }

  public clear() : void {
    sessionStorage.clear();
    return;
  }

  public peek() : void {
    for (var i = 0; i < sessionStorage.length; i++) {
      const key = sessionStorage.key(i);
      const value = sessionStorage.getItem(key as string);
      console.log(`${key}: ${value}`)
    }
    return;
  }
}