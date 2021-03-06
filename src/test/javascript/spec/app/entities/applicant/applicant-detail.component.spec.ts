import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgadmissionTestModule } from '../../../test.module';
import { ApplicantDetailComponent } from 'app/entities/applicant/applicant-detail.component';
import { Applicant } from 'app/shared/model/applicant.model';

describe('Component Tests', () => {
  describe('Applicant Management Detail Component', () => {
    let comp: ApplicantDetailComponent;
    let fixture: ComponentFixture<ApplicantDetailComponent>;
    const route = ({ data: of({ applicant: new Applicant(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgadmissionTestModule],
        declarations: [ApplicantDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ApplicantDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ApplicantDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load applicant on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.applicant).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
