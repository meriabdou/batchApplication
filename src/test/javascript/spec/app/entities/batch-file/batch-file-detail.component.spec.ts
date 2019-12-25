import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BatchApplicationTestModule } from '../../../test.module';
import { BatchFileDetailComponent } from 'app/entities/batch-file/batch-file-detail.component';
import { BatchFile } from 'app/shared/model/batch-file.model';

describe('Component Tests', () => {
  describe('BatchFile Management Detail Component', () => {
    let comp: BatchFileDetailComponent;
    let fixture: ComponentFixture<BatchFileDetailComponent>;
    const route = ({ data: of({ batchFile: new BatchFile(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BatchApplicationTestModule],
        declarations: [BatchFileDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BatchFileDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BatchFileDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load batchFile on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.batchFile).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
