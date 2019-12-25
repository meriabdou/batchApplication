import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBatchParams } from 'app/shared/model/batch-params.model';
import { BatchParamsService } from './batch-params.service';

@Component({
  templateUrl: './batch-params-delete-dialog.component.html'
})
export class BatchParamsDeleteDialogComponent {
  batchParams?: IBatchParams;

  constructor(
    protected batchParamsService: BatchParamsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.batchParamsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('batchParamsListModification');
      this.activeModal.close();
    });
  }
}
