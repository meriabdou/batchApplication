import { IBatchParams } from 'app/shared/model/batch-params.model';

export interface IBatchFile {
  id?: number;
  batchName?: string;
  path?: string;
  batchParams?: IBatchParams;
}

export class BatchFile implements IBatchFile {
  constructor(public id?: number, public batchName?: string, public path?: string, public batchParams?: IBatchParams) {}
}
