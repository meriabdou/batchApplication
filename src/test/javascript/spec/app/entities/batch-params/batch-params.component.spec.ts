import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BatchApplicationTestModule } from '../../../test.module';
import { BatchParamsComponent } from 'app/entities/batch-params/batch-params.component';
import { BatchParamsService } from 'app/entities/batch-params/batch-params.service';
import { BatchParams } from 'app/shared/model/batch-params.model';

describe('Component Tests', () => {
  describe('BatchParams Management Component', () => {
    let comp: BatchParamsComponent;
    let fixture: ComponentFixture<BatchParamsComponent>;
    let service: BatchParamsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BatchApplicationTestModule],
        declarations: [BatchParamsComponent],
        providers: []
      })
        .overrideTemplate(BatchParamsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BatchParamsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BatchParamsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new BatchParams(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.batchParams && comp.batchParams[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
