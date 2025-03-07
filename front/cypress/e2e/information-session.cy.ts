/// <reference types="cypress" />

describe('informations sessions spec', () => {
  it('session information is correctly displayed', () => {
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

    cy.intercept('GET', '/api/session', {
      statusCode: 200,
      body: [{
        id: 1,
        name: 'Yoga fun bébé',
        date: new Date().toISOString().split('T')[0],
        teacher_id: 1,
        description: 'New Yoga fun session for babies',
      }
      ],
    }).as('session')

    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    cy.url().should('include', '/sessions')

    //Vérification des informations de la session
    cy.contains('Yoga fun bébé').should('be.visible')
    cy.contains('New Yoga fun session for babies').should('be.visible')
    cy.contains('Detail').should('be.visible')
    cy.contains('Edit').should('be.visible')
    cy.contains('Session on ' + new Date().toLocaleDateString('en-US', { year: 'numeric', month: 'long', day: 'numeric' })).should('be.visible');
    cy.get('img')
      .should('be.visible')
      .and('have.attr', 'src', 'assets/sessions.png')
      .and('have.attr', 'alt', 'Yoga session');

  })
  it('delete button appears if the user is an admin', () => {

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

    cy.intercept('GET', '/api/session', {
      statusCode: 200,
      body: [{
        id: 1,
        name: 'Yoga fun bébé',
        date: new Date().toISOString().split('T')[0],
        teacher_id: 1,
        description: 'New Yoga fun session for babies',
      }],
    }).as('session')


    cy.intercept('GET', '/api/session/1', {
      statusCode: 200,
      body: {
        id: 1,
        name: 'Yoga fun bébé',
        date: new Date().toISOString().split('T')[0],
        teacher_id: 1,
        description: 'New Yoga fun session for babies',
      },
    }).as('sessionDetail')
    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    cy.get('button:has(span.ml1:contains("Detail"))').click()
    cy.contains('delete').should('be.visible')
  })
  it('delete button not appears if the user is not an admin', () => {

    cy.visit('/login')

    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        username: 'userName',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: false
      },
    })

    cy.intercept('GET', '/api/session', {
      statusCode: 200,
      body: [{
        id: 1,
        name: 'Yoga fun bébé',
        date: new Date().toISOString().split('T')[0],
        teacher_id: 1,
        description: 'New Yoga fun session for babies',
      }],
    }).as('session')


    cy.intercept('GET', '/api/session/1', {
      statusCode: 200,
      body: {
        id: 1,
        name: 'Yoga fun bébé',
        date: new Date().toISOString().split('T')[0],
        teacher_id: 1,
        description: 'New Yoga fun session for babies',
      },
    }).as('sessionDetail')

    cy.get('input[formControlName=email]').type("mela@me.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    cy.get('button:has(span.ml1:contains("Detail"))').click()
    cy.contains('delete').should('not.exist')
  })

})