import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class IntelligentserviceService {

  constructor(private http:HttpClient) 
  {

  }


  getServiceProviders(value):any {
    console.log("in intelligent service")
    var url=`http://13.232.118.81:8089/api/v1/intelligentService/${value}`;
    console.log("after the url")
    return this.http.get(url);
  }
}