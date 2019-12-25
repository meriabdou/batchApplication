export interface IBatchFile {
  id?: number;
  batchName?: string;
  path?: string;
}

export class BatchFile implements IBatchFile {
  constructor(public id?: number, public batchName?: string, public path?: string) {}
}
