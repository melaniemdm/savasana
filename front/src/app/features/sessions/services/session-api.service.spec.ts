import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { describe, expect } from '@jest/globals';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { SessionApiService } from './session-api.service';
import { Session } from '../interfaces/session.interface';

describe('SessionsService', () => {
  let service: SessionApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[
        HttpClientModule
      ]
    });
    service = TestBed.inject(SessionApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

describe('SessionsServiceComplementaryTest', () => {
  let service: SessionApiService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [SessionApiService],
    });
    service = TestBed.inject(SessionApiService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => httpMock.verify());

  it('should send a DELETE request to delete a session', () => {
    service.delete('123').subscribe();
    const req = httpMock.expectOne('api/session/123');
    expect(req.request.method).toBe('DELETE');
    req.flush(null);
  });

  it('should send a POST request to create a session', () => {
    const session: Session = {
      name: 'Test Session',
      description: 'This is a test session',
      date: new Date('2024-12-01'),
      teacher_id: 101,
      users: [201, 202, 203],
    };
    service.create(session).subscribe((response) => expect(response).toEqual(session));
    const req = httpMock.expectOne('api/session');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(session);
    req.flush(session);
  });

  it('should send a PUT request to update a session', () => {
    const session: Session = {
      name: 'Updated Session',
      description: 'Updated description',
      date: new Date('2024-12-10'),
      teacher_id: 102,
      users: [301, 302],
    };
    service.update('123', session).subscribe((response) => expect(response).toEqual(session));
    const req = httpMock.expectOne('api/session/123');
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(session);
    req.flush(session);
  });

  it('should send a POST request for participation', () => {
    service.participate('123', '456').subscribe();
    const req = httpMock.expectOne('api/session/123/participate/456');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toBeNull();
    req.flush(null);
  });

  it('should send a DELETE request for unparticipation', () => {
    service.unParticipate('123', '456').subscribe();
    const req = httpMock.expectOne('api/session/123/participate/456');
    expect(req.request.method).toBe('DELETE');
    req.flush(null);
  });
});