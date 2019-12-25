import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BatchApplicationTestModule } from '../../../test.module';
import { BatchFileUpdateComponent } from 'app/entities/batch-file/batch-file-update.component';
import { BatchFileService } from 'app/entities/batch-file/batch-file.service';
import { BatchFile } from 'app/shared/model/batch-file.model';

describe('Component Tests', () => {
  describe('BatchFile Management Update Component', () => {
    let comp: BatchFileUpdateComponent;
    let fixture: ComponentFixture<BatchFileUpdateComponent>;
    let service: BatchFileService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BatchApplicationTestModule],
        declarations: [BatchFileUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(BatchFileUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BatchFileUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BatchFileService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BatchFile(123);
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
        const entity = new BatchFile();
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
