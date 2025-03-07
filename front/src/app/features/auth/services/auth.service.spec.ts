import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AuthService } from './auth.service';
import { LoginRequest } from '../interfaces/loginRequest.interface';
import { RegisterRequest } from '../interfaces/registerRequest.interface';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';
import { expect } from '@jest/globals';

describe('AuthService', () => {
  let service: AuthService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AuthService],
    });

    service = TestBed.inject(AuthService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify(); // Vérifie qu'il n'y a pas de requêtes HTTP non vérifiées
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should send a POST request for register', () => {
    const mockRegisterRequest: RegisterRequest = {
      firstName: 'testuser',
      lastName: 'testuser',
      email: 'testuser@example.com',
      password: 'password123',
    };

    service.register(mockRegisterRequest).subscribe((response) => {
      expect(response).toBeUndefined(); // La réponse est vide puisque le backend n'est pas lancé
    });

    const req = httpMock.expectOne('api/auth/register');
    expect(req.request.method).toBe('POST'); // Vérifie que la méthode est POST
    expect(req.request.body).toEqual(mockRegisterRequest); // Vérifie le corps de la requête
    req.flush(null); // vide le contenu de requete
  });

  it('should send a POST request for login and return SessionInformation', () => {
    const mockLoginRequest: LoginRequest = {
      email: 'testuser@toto.com',
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

    service.login(mockLoginRequest).subscribe((response) => {
      expect(response).toEqual(mockSessionInformation); // Vérifie que la réponse correspond au mock
    });

    const req = httpMock.expectOne('api/auth/login');
    expect(req.request.method).toBe('POST'); // Vérifie que la méthode est POST
    expect(req.request.body).toEqual(mockLoginRequest); // Vérifie le corps de la requête
    req.flush(mockSessionInformation); // Simule une réponse contenant SessionInformation
  });
});
