import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BatchApplicationTestModule } from '../../../test.module';
import { BatchParamsDetailComponent } from 'app/entities/batch-params/batch-params-detail.component';
import { BatchParams } from 'app/shared/model/batch-params.model';

describe('Component Tests', () => {
  describe('BatchParams Management Detail Component', () => {
    let comp: BatchParamsDetailComponent;
    let fixture: ComponentFixture<BatchParamsDetailComponent>;
    const route = ({ data: of({ batchParams: new BatchParams(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BatchApplicationTestModule],
        declarations: [BatchParamsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BatchParamsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BatchParamsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load batchParams on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.batchParams).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
