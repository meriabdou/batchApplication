import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBatchParams } from 'app/shared/model/batch-params.model';
import { BatchParamsService } from './batch-params.service';
import { BatchParamsDeleteDialogComponent } from './batch-params-delete-dialog.component';

@Component({
  selector: 'jhi-batch-params',
  templateUrl: './batch-params.component.html'
})
export class BatchParamsComponent implements OnInit, OnDestroy {
  batchParams?: IBatchParams[];
  eventSubscriber?: Subscription;

  constructor(
    protected batchParamsService: BatchParamsService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.batchParamsService.query().subscribe((res: HttpResponse<IBatchParams[]>) => {
      this.batchParams = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBatchParams();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBatchParams): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBatchParams(): void {
    this.eventSubscriber = this.eventManager.subscribe('batchParamsListModification', () => this.loadAll());
  }

  delete(batchParams: IBatchParams): void {
    const modalRef = this.modalService.open(BatchParamsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.batchParams = batchParams;
  }
}
