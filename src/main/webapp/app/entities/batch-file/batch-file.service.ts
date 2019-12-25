import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBatchFile } from 'app/shared/model/batch-file.model';

type EntityResponseType = HttpResponse<IBatchFile>;
type EntityArrayResponseType = HttpResponse<IBatchFile[]>;

@Injectable({ providedIn: 'root' })
export class BatchFileService {
  public resourceUrl = SERVER_API_URL + 'api/batch-files';

  constructor(protected http: HttpClient) {}

  create(batchFile: IBatchFile): Observable<EntityResponseType> {
    return this.http.post<IBatchFile>(this.resourceUrl, batchFile, { observe: 'response' });
  }

  update(batchFile: IBatchFile): Observable<EntityResponseType> {
    return this.http.put<IBatchFile>(this.resourceUrl, batchFile, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBatchFile>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBatchFile[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
