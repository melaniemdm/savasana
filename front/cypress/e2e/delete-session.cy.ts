/// <reference types="cypress" />

describe('delete session', () => {
  it('delete session', () => {

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

    cy.intercept('DELETE', '/api/session/1', {
      statusCode: 200,
    }).as('deleteSession')

    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    // Attend les données de session
    cy.wait('@session')

    cy.url().should('include', '/sessions')

    cy.get('button:has(span.ml1:contains("Detail"))').click()

    cy.url().should('include', 'sessions/detail/1')

    cy.contains('Delete').click()

    // Attend la requête update
    cy.wait('@deleteSession')

    cy.contains('Session deleted').should('be.visible');

    cy.url().should('include', '/sessions')
  })
});