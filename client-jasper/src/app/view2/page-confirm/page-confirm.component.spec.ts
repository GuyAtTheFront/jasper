import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PageConfirmComponent } from './page-confirm.component';

describe('PageConfirmComponent', () => {
  let component: PageConfirmComponent;
  let fixture: ComponentFixture<PageConfirmComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PageConfirmComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PageConfirmComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
