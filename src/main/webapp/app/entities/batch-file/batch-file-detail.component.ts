import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBatchFile } from 'app/shared/model/batch-file.model';

@Component({
  selector: 'jhi-batch-file-detail',
  templateUrl: './batch-file-detail.component.html'
})
export class BatchFileDetailComponent implements OnInit {
  batchFile: IBatchFile | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ batchFile }) => {
      this.batchFile = batchFile;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
