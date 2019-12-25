import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BatchApplicationSharedModule } from 'app/shared/shared.module';
import { BatchFileComponent } from './batch-file.component';
import { BatchFileDetailComponent } from './batch-file-detail.component';
import { BatchFileUpdateComponent } from './batch-file-update.component';
import { BatchFileDeleteDialogComponent } from './batch-file-delete-dialog.component';
import { batchFileRoute } from './batch-file.route';

@NgModule({
  imports: [BatchApplicationSharedModule, RouterModule.forChild(batchFileRoute)],
  declarations: [BatchFileComponent, BatchFileDetailComponent, BatchFileUpdateComponent, BatchFileDeleteDialogComponent],
  entryComponents: [BatchFileDeleteDialogComponent]
})
export class BatchApplicationBatchFileModule {}
