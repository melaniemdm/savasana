import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { BrowserAnimationsModule, NoopAnimationsModule } from '@angular/platform-browser/animations'; // Import nécessaire pour les animations
import { SessionService } from 'src/app/services/session.service';
import { describe, expect } from '@jest/globals';
import { of } from 'rxjs';
import { MeComponent } from './me.component';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';


describe('MeComponent', () => {
  let component: MeComponent;
  let fixture: ComponentFixture<MeComponent>;

  const mockSessionService = {
    sessionInformation: {
      admin: true,
      id: 1
    }
  }
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MeComponent],
      imports: [
        MatSnackBarModule,
        HttpClientModule,
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule
      ],
      providers: [{ provide: SessionService, useValue: mockSessionService }],
    })
      .compileComponents();

    fixture = TestBed.createComponent(MeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

describe('MeComponent Integration Test', () => {
  let component: MeComponent;
  let fixture: ComponentFixture<MeComponent>;
  let userService: UserService;


  const mockSessionService = {
    sessionInformation: {
      id: 1,
      admin: true,
    },
    logOut: jest.fn(),
  };

  const mockRouter = {
    navigate: jest.fn(),
  } as Partial<Router> as jest.Mocked<Router>;

  // mock des données renvoyées par getById
  const mockUser = {
    id: 1,
    firstName: 'melanie',
    lastName: 'lisanna',
    email: 'mel.lisa@example.com',
    admin: true,
    password: 'secret',
    createdAt: new Date('2023-01-01'),
  };


  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MeComponent],
      imports: [
        HttpClientModule,
        MatSnackBarModule,
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,
        BrowserAnimationsModule,
        NoopAnimationsModule,
      ],
      providers: [

        { provide: SessionService, useValue: mockSessionService },
        { provide: Router, useValue: mockRouter },

      ],
    }).compileComponents();

    fixture = TestBed.createComponent(MeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    
    userService = TestBed.inject(UserService);

   
    //Espoinne les méthodes du vrai service.
       jest.spyOn(userService, 'getById').mockReturnValue(of(mockUser));
    jest.spyOn(userService, 'delete').mockReturnValue(of(null));
   

    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should call UserService.getById on initialization', () => {
    component.ngOnInit();
    expect(userService.getById).toHaveBeenCalledWith("1");
    expect(component.user).toEqual(mockUser);
  });

  it('should navigate back when back() is called', () => {
    const historyBackSpy = jest.spyOn(window.history, 'back');
    component.back();
    expect(historyBackSpy).toHaveBeenCalled();
  });

  it('should call UserService.delete and navigate to "/" on delete()', () => {
    component.delete();

    expect(userService.delete).toHaveBeenCalledWith('1');

    expect(mockSessionService.logOut).toHaveBeenCalled();

    expect(mockRouter.navigate).toHaveBeenCalledWith(['/']);
  });
});