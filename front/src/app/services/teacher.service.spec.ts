import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { TeacherService } from './teacher.service';
import { SessionService } from './session.service';
import { SessionInformation } from '../interfaces/sessionInformation.interface';

describe('TeacherService', () => {
  let service: TeacherService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[
        HttpClientModule
      ]
    });
    service = TestBed.inject(TeacherService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

describe('TeacherServiceComplementaryTest', () => {
  let service: SessionService;

  const session: SessionInformation = {
    token: 'sampleToken',
    type: 'user',
    id: 1,
    username: 'testUser',
    firstName: 'Test',
    lastName: 'User',
    admin: false,
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SessionService],
    });
    service = TestBed.inject(SessionService);
  });

  it('should emit true after logIn', (done) => {
    service.logIn(session);
    service.$isLogged().subscribe((isLogged) => {
      expect(isLogged).toBe(true);
      done();
    });
  });

  it('should emit false after logOut', (done) => {
    service.logIn(session);
    service.logOut();
    service.$isLogged().subscribe((isLogged) => {
      expect(isLogged).toBe(false);
      done();
    });
  });
});

