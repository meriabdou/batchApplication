import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBatchFile } from 'app/shared/model/batch-file.model';
import { BatchFileService } from './batch-file.service';
import { BatchFileDeleteDialogComponent } from './batch-file-delete-dialog.component';

@Component({
  selector: 'jhi-batch-file',
  templateUrl: './batch-file.component.html'
})
export class BatchFileComponent implements OnInit, OnDestroy {
  batchFiles?: IBatchFile[];
  eventSubscriber?: Subscription;

  constructor(protected batchFileService: BatchFileService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.batchFileService.query().subscribe((res: HttpResponse<IBatchFile[]>) => {
      this.batchFiles = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBatchFiles();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBatchFile): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBatchFiles(): void {
    this.eventSubscriber = this.eventManager.subscribe('batchFileListModification', () => this.loadAll());
  }

  delete(batchFile: IBatchFile): void {
    const modalRef = this.modalService.open(BatchFileDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.batchFile = batchFile;
  }
}
