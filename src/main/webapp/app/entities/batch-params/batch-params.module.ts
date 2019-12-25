import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BatchApplicationSharedModule } from 'app/shared/shared.module';
import { BatchParamsComponent } from './batch-params.component';
import { BatchParamsDetailComponent } from './batch-params-detail.component';
import { BatchParamsUpdateComponent } from './batch-params-update.component';
import { BatchParamsDeleteDialogComponent } from './batch-params-delete-dialog.component';
import { batchParamsRoute } from './batch-params.route';

@NgModule({
  imports: [BatchApplicationSharedModule, RouterModule.forChild(batchParamsRoute)],
  declarations: [BatchParamsComponent, BatchParamsDetailComponent, BatchParamsUpdateComponent, BatchParamsDeleteDialogComponent],
  entryComponents: [BatchParamsDeleteDialogComponent]
})
export class BatchApplicationBatchParamsModule {}
