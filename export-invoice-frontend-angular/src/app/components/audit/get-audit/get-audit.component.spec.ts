import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GetAuditComponent } from './get-audit.component';

describe('GetAuditComponent', () => {
  let component: GetAuditComponent;
  let fixture: ComponentFixture<GetAuditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GetAuditComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GetAuditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
