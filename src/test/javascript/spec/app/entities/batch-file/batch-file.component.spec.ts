import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BatchApplicationTestModule } from '../../../test.module';
import { BatchFileComponent } from 'app/entities/batch-file/batch-file.component';
import { BatchFileService } from 'app/entities/batch-file/batch-file.service';
import { BatchFile } from 'app/shared/model/batch-file.model';

describe('Component Tests', () => {
  describe('BatchFile Management Component', () => {
    let comp: BatchFileComponent;
    let fixture: ComponentFixture<BatchFileComponent>;
    let service: BatchFileService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BatchApplicationTestModule],
        declarations: [BatchFileComponent],
        providers: []
      })
        .overrideTemplate(BatchFileComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BatchFileComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BatchFileService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new BatchFile(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.batchFiles && comp.batchFiles[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
