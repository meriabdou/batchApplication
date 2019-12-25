import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IBatchParams, BatchParams } from 'app/shared/model/batch-params.model';
import { BatchParamsService } from './batch-params.service';
import { IBatchFile } from 'app/shared/model/batch-file.model';
import { BatchFileService } from 'app/entities/batch-file/batch-file.service';

@Component({
  selector: 'jhi-batch-params-update',
  templateUrl: './batch-params-update.component.html'
})
export class BatchParamsUpdateComponent implements OnInit {
  isSaving = false;

  batchfiles: IBatchFile[] = [];

  editForm = this.fb.group({
    id: [],
    param: [],
    required: [],
    paramName: [],
    batchFile: []
  });

  constructor(
    protected batchParamsService: BatchParamsService,
    protected batchFileService: BatchFileService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ batchParams }) => {
      this.updateForm(batchParams);

      this.batchFileService
        .query()
        .pipe(
          map((res: HttpResponse<IBatchFile[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IBatchFile[]) => (this.batchfiles = resBody));
    });
  }

  updateForm(batchParams: IBatchParams): void {
    this.editForm.patchValue({
      id: batchParams.id,
      param: batchParams.param,
      required: batchParams.required,
      paramName: batchParams.paramName,
      batchFile: batchParams.batchFile
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const batchParams = this.createFromForm();
    if (batchParams.id !== undefined) {
      this.subscribeToSaveResponse(this.batchParamsService.update(batchParams));
    } else {
      this.subscribeToSaveResponse(this.batchParamsService.create(batchParams));
    }
  }

  private createFromForm(): IBatchParams {
    return {
      ...new BatchParams(),
      id: this.editForm.get(['id'])!.value,
      param: this.editForm.get(['param'])!.value,
      required: this.editForm.get(['required'])!.value,
      paramName: this.editForm.get(['paramName'])!.value,
      batchFile: this.editForm.get(['batchFile'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBatchParams>>): void {
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

  trackById(index: number, item: IBatchFile): any {
    return item.id;
  }
}
