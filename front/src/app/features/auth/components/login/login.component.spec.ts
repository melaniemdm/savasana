import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';

import { LoginComponent } from './login.component';
import { of, throwError } from 'rxjs';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      providers: [SessionService],
      imports: [
        RouterTestingModule,
        BrowserAnimationsModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule]
    })
      .compileComponents();
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});


describe('LoginComponent Integration Test', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authService: jest.Mocked<AuthService>;
  let sessionService: jest.Mocked<SessionService>;
  let router: jest.Mocked<Router>;

  beforeEach(async () => {
    // Mock AuthService
    authService = {
      login: jest.fn(),
    } as unknown as jest.Mocked<AuthService>;

    // Mock SessionService
    sessionService = {
      logIn: jest.fn(),
    } as unknown as jest.Mocked<SessionService>;

    // Mock Router
    router = {
      navigate: jest.fn(),
    } as unknown as jest.Mocked<Router>;

    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      imports: [
        RouterTestingModule,
        BrowserAnimationsModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule,
      ],
      providers: [
        { provide: AuthService, useValue: authService },
        { provide: SessionService, useValue: sessionService },
        { provide: Router, useValue: router },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should call AuthService.login on form submission', () => {
    // Mock input data
    const mockLoginRequest = {
      email: 'test@example.com',
      password: 'password123',
    };

    const mockSessionInformation: SessionInformation = {
      token: 'sample-token',
      type: 'Bearer',
      id: 1,
      username: 'testuser',
      firstName: 'Test',
      lastName: 'User',
      admin: false,
    };

    // Configure AuthService.login to return an observable with mock data
    authService.login.mockReturnValue(of(mockSessionInformation));

    // Set form values
    component.form.setValue(mockLoginRequest);

    // Call submit method
    component.submit();

    // Expectations
    expect(authService.login).toHaveBeenCalledWith(mockLoginRequest); // Vérifie que login a été appelé
    expect(sessionService.logIn).toHaveBeenCalledWith(mockSessionInformation); // Vérifie que logIn est appelé
    expect(router.navigate).toHaveBeenCalledWith(['/sessions']); // Vérifie que la navigation a eu lieu
  });

  it('should set onError to true if AuthService.login fails', () => {
    // Mock input data
    const mockLoginRequest = {
      email: 'test@example.com',
      password: 'wrongpassword',
    };

    // Configure AuthService.login to throw an error
    authService.login.mockReturnValue(
      throwError(() => new Error('Invalid credentials'))
    );

    // Set form values
    component.form.setValue(mockLoginRequest);

    // Call submit method
    component.submit();

    // Expectations
    expect(authService.login).toHaveBeenCalledWith(mockLoginRequest); // Vérifie que login a été appelé
    expect(component.onError).toBe(true); // Vérifie que onError est passé à true
  });

  it('should not call AuthService.login if the form is invalid', () => {
    // Set form to invalid state
    component.form.setValue({
      email: '',
      password: '',
    });

    // Call submit method
    component.submit();

    // Expectations
    expect(authService.login).not.toHaveBeenCalled(); // Vérifie que login n'a pas été appelé
  });
});