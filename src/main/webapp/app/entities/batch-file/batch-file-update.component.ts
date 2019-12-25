import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IBatchFile, BatchFile } from 'app/shared/model/batch-file.model';
import { BatchFileService } from './batch-file.service';
import { IBatchParams } from 'app/shared/model/batch-params.model';
import { BatchParamsService } from 'app/entities/batch-params/batch-params.service';

@Component({
  selector: 'jhi-batch-file-update',
  templateUrl: './batch-file-update.component.html'
})
export class BatchFileUpdateComponent implements OnInit {
  isSaving = false;

  batchparams: IBatchParams[] = [];

  editForm = this.fb.group({
    id: [],
    batchName: [],
    path: [],
    batchParams: []
  });

  constructor(
    protected batchFileService: BatchFileService,
    protected batchParamsService: BatchParamsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ batchFile }) => {
      this.updateForm(batchFile);

      this.batchParamsService
        .query()
        .pipe(
          map((res: HttpResponse<IBatchParams[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IBatchParams[]) => (this.batchparams = resBody));
    });
  }

  updateForm(batchFile: IBatchFile): void {
    this.editForm.patchValue({
      id: batchFile.id,
      batchName: batchFile.batchName,
      path: batchFile.path,
      batchParams: batchFile.batchParams
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const batchFile = this.createFromForm();
    if (batchFile.id !== undefined) {
      this.subscribeToSaveResponse(this.batchFileService.update(batchFile));
    } else {
      this.subscribeToSaveResponse(this.batchFileService.create(batchFile));
    }
  }

  private createFromForm(): IBatchFile {
    return {
      ...new BatchFile(),
      id: this.editForm.get(['id'])!.value,
      batchName: this.editForm.get(['batchName'])!.value,
      path: this.editForm.get(['path'])!.value,
      batchParams: this.editForm.get(['batchParams'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBatchFile>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IBatchParams): any {
    return item.id;
  }
}
