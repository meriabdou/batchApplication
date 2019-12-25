import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBatchParams } from 'app/shared/model/batch-params.model';

type EntityResponseType = HttpResponse<IBatchParams>;
type EntityArrayResponseType = HttpResponse<IBatchParams[]>;

@Injectable({ providedIn: 'root' })
export class BatchParamsService {
  public resourceUrl = SERVER_API_URL + 'api/batch-params';

  constructor(protected http: HttpClient) {}

  create(batchParams: IBatchParams): Observable<EntityResponseType> {
    return this.http.post<IBatchParams>(this.resourceUrl, batchParams, { observe: 'response' });
  }

  update(batchParams: IBatchParams): Observable<EntityResponseType> {
    return this.http.put<IBatchParams>(this.resourceUrl, batchParams, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBatchParams>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBatchParams[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
