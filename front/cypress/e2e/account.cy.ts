/// <reference types="cypress" />


describe('Account spec', () => {
  it('connection information correctly displayed', () => {
    cy.visit('/login')
    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        username: 'userName',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: false
      },
    }).as('login')

    cy.intercept('GET', '/api/session', {
      statusCode: 200,
      body: [],
    }).as('session')

    cy.intercept('DELETE', '/api/user/1', {
      statusCode: 200,
    }).as('deleteUser')

    cy.intercept('GET', '/api/user/1', {
      status: 200,
      body: {
        id: 1,
        email: 'mela@me.com',
        firstName: 'Mela',
        lastName: 'Melanie',
        updatedAt: '2020-12-10T00:00:00.000Z',
        createdAt: '2024-12-13T00:00:00.000Z',
        admin: false
      }
    }).as('userInfo')

    cy.get('input[formControlName=email]').type("mela@me.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    cy.url().should('include', '/session')

    cy.contains('Account').click()

    cy.url().should('include', '/me')

    // Attendre la requête GET pour les informations utilisateur
    cy.wait('@userInfo');

    // test que les elements sont visibles
    cy.contains('Email').should('be.visible')
    cy.contains('Name').should('be.visible')
    cy.contains('Create at').should('be.visible')
    cy.contains('Last update').should('be.visible')

    cy.contains('Email: mela@me.com').should('be.visible')
    cy.get('.p2').contains(/Create at: .*2020/).should('be.visible');

    cy.get('.p2').contains(/Last update: .*2020/).should('be.visible');
  })

  it('delete button appears for a user', () => {
    cy.contains('Detail').should('be.visible')
  })

  it('delete account', () => {
    cy.visit('/login')
    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        username: 'userName',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: false
      },
    }).as('login')

    cy.intercept('GET', '/api/session', {
      statusCode: 200,
      body: [],
    }).as('session')


    cy.intercept('GET', '/api/user/1', {
      status: 200,
      body: {
        id: 1,
        email: 'yoga@studio.com',
        firstName: 'melanie',
        lastName: 'melanie',
        updatedAt: '2020-12-10T00:00:00.000Z',
        createdAt: '2024-12-13T00:00:00.000Z',
        admin: false
      }
    }).as('userInfo')

    // Interception de la requête DELETE pour supprimer une session
    cy.intercept('DELETE', '/api/user/1', {
      statusCode: 200,
    }).as('deleteUser')

    cy.get('input[formControlName=email]').type("mela@me.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    cy.url().should('include', '/session')

    cy.contains('Account').click()

    cy.url().should('include', '/me')

    // Attendre la requête GET pour les informations utilisateur
    cy.wait('@userInfo');
    cy.contains('Detail').click()
    cy.url().should('include', '/')
    cy.contains('Your account has been deleted !').should('be.visible')
  })


  it('delete button does not appear for an admin', () => {
    cy.visit('/login')
    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        username: 'userName',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: true
      },
    }).as('login')

    cy.intercept('GET', '/api/session', {
      statusCode: 200,
      body: [],
    }).as('session')


    cy.intercept('GET', '/api/user/1', {
      status: 200,
      body: {
        id: 1,
        email: 'yoga@studio.com',
        firstName: 'yoga',
        lastName: 'yoga',
        updatedAt: '2020-12-10T00:00:00.000Z',
        createdAt: '2024-12-13T00:00:00.000Z',
        admin: true
      }
    }).as('userInfo')

    cy.get('input[formControlName=email]').type("mela@me.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    cy.url().should('include', '/session')

    cy.contains('Account').click()

    cy.url().should('include', '/me')

    // Attendre la requête GET pour les informations utilisateur
    cy.wait('@userInfo');
    cy.contains('Detail').should('not.exist')
  })


})