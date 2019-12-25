import { IBatchFile } from 'app/shared/model/batch-file.model';

export interface IBatchParams {
  id?: number;
  param?: string;
  required?: number;
  paramName?: string;
  batchFile?: IBatchFile;
}

export class BatchParams implements IBatchParams {
  constructor(
    public id?: number,
    public param?: string,
    public required?: number,
    public paramName?: string,
    public batchFile?: IBatchFile
  ) {}
}
