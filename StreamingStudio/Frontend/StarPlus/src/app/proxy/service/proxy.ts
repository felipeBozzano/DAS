import {Observable} from 'rxjs';

function  getRecords(this: any): Observable<any[]>{
  // @ts-ignore
  return this.http.get<any[]>('/echo/get/json')
}

