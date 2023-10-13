import { ComponentFixture, TestBed } from '@angular/core/testing';
// import { LoginComponentTokenService } from './login-authentication.component';

// import { LoginAuthenticationComponent } from './login-authentication.component';
import { LoginComponentTokenService } from './login-authentication.component';

describe('LoginAuthenticationComponent', () => {
  let component: LoginComponentTokenService;
  let fixture: ComponentFixture<LoginComponentTokenService>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LoginComponentTokenService ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoginComponentTokenService);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
