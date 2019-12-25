import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BatchApplicationTestModule } from '../../../test.module';
import { BatchParamsUpdateComponent } from 'app/entities/batch-params/batch-params-update.component';
import { BatchParamsService } from 'app/entities/batch-params/batch-params.service';
import { BatchParams } from 'app/shared/model/batch-params.model';

describe('Component Tests', () => {
  describe('BatchParams Management Update Component', () => {
    let comp: BatchParamsUpdateComponent;
    let fixture: ComponentFixture<BatchParamsUpdateComponent>;
    let service: BatchParamsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BatchApplicationTestModule],
        declarations: [BatchParamsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(BatchParamsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BatchParamsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BatchParamsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BatchParams(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new BatchParams();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
