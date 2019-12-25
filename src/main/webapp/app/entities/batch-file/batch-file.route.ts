import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBatchFile, BatchFile } from 'app/shared/model/batch-file.model';
import { BatchFileService } from './batch-file.service';
import { BatchFileComponent } from './batch-file.component';
import { BatchFileDetailComponent } from './batch-file-detail.component';
import { BatchFileUpdateComponent } from './batch-file-update.component';

@Injectable({ providedIn: 'root' })
export class BatchFileResolve implements Resolve<IBatchFile> {
  constructor(private service: BatchFileService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBatchFile> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((batchFile: HttpResponse<BatchFile>) => {
          if (batchFile.body) {
            return of(batchFile.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BatchFile());
  }
}

export const batchFileRoute: Routes = [
  {
    path: '',
    component: BatchFileComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'batchApplicationApp.batchFile.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BatchFileDetailComponent,
    resolve: {
      batchFile: BatchFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'batchApplicationApp.batchFile.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BatchFileUpdateComponent,
    resolve: {
      batchFile: BatchFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'batchApplicationApp.batchFile.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BatchFileUpdateComponent,
    resolve: {
      batchFile: BatchFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'batchApplicationApp.batchFile.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
