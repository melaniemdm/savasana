/// <reference types="cypress" />

describe('create session spec', () => {
  it('create session successfull', () => {
    cy.visit('/login')

    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        username: 'userName',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: true
      },
    })

    cy.intercept('GET', '/api/session', []).as('sessions');



    cy.intercept('GET', '/api/teacher', {
      body: [
        { id: 1, firstName: 'Margot', lastName: 'DELAHAYE' },
        { id: 2, firstName: 'John', lastName: 'DOE' },
      ],
    }).as('createSession');


    cy.intercept('POST', '/api/session', {
      statusCode: 201,
      body: {
        id: 1,
        name: 'Yoga fun',
        date: new Date().toISOString().split('T')[0],
        teacher_id: 1,
        description: 'New Yoga fun session',
      },
    }).as('createSession');

    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)


    cy.contains('Create').click();
    cy.url().should('include', '/sessions/create');

    cy.get('input[formControlName=name]').type("Yoga fun")
    cy.get('input[formControlName=date]').type(new Date().toISOString().split('T')[0])

    cy.get('mat-select[formControlName=teacher_id]').click(); // Ouvrir la liste
    cy.contains('Margot DELAHAYE').click(); // Sélectionne Margot


    cy.get('textarea[formControlName=description]').type('New Yoga fun session');

    cy.contains('Save').click()

    cy.url().should('include', '/sessions')
  })


  it('error display in the absence of a required field', () => {
    cy.visit('/login')

    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        username: 'userName',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: true
      },
    })

    cy.intercept('GET', '/api/session', []).as('sessions');

    cy.intercept('GET', '/api/teacher', {
      body: [
        { id: 1, firstName: 'Margot', lastName: 'DELAHAYE' },
        { id: 2, firstName: 'John', lastName: 'DOE' },
      ],
    }).as('createSession');

    cy.intercept('POST', '/api/session', {
      statusCode: 201,
      body: {
        id: 1,
        name: 'Yoga fun',
        date: new Date().toISOString().split('T')[0],
        teacher_id: 1,
        description: 'New Yoga fun session',
      },
    }).as('createSession');

    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    cy.contains('Create').click();
    cy.url().should('include', '/sessions/create');

    //cy.get('input[formControlName=name]').type("Yoga fun")
    cy.get('input[formControlName=date]').type(new Date().toISOString().split('T')[0])

    cy.get('mat-select[formControlName=teacher_id]').click(); // Ouvrir la liste
    cy.contains('Margot DELAHAYE').click(); // Sélectionner l'option

    cy.get('textarea[formControlName=description]').type('New Yoga fun session');

    cy.contains('Save').should('be.disabled')

    //pour que l'erreur apparaisse
    cy.get('input[formControlName=name').click()
    cy.get('mat-select[formControlName=teacher_id]').click()


    cy.contains('Name').should('have.css', 'color', 'rgb(244, 67, 54)')
  })

});