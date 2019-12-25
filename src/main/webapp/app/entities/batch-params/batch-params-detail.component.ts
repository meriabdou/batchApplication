import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBatchParams } from 'app/shared/model/batch-params.model';

@Component({
  selector: 'jhi-batch-params-detail',
  templateUrl: './batch-params-detail.component.html'
})
export class BatchParamsDetailComponent implements OnInit {
  batchParams: IBatchParams | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ batchParams }) => {
      this.batchParams = batchParams;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
