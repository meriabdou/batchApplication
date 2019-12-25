import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'batch-file',
        loadChildren: () => import('./batch-file/batch-file.module').then(m => m.BatchApplicationBatchFileModule)
      },
      {
        path: 'batch-params',
        loadChildren: () => import('./batch-params/batch-params.module').then(m => m.BatchApplicationBatchParamsModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class BatchApplicationEntityModule {}
