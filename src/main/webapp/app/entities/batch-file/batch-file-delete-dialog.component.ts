import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBatchFile } from 'app/shared/model/batch-file.model';
import { BatchFileService } from './batch-file.service';

@Component({
  templateUrl: './batch-file-delete-dialog.component.html'
})
export class BatchFileDeleteDialogComponent {
  batchFile?: IBatchFile;

  constructor(protected batchFileService: BatchFileService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.batchFileService.delete(id).subscribe(() => {
      this.eventManager.broadcast('batchFileListModification');
      this.activeModal.close();
    });
  }
}
