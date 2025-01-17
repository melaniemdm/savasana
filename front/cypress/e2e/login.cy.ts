/// <reference types="cypress" />

describe('Login spec', () => {
  it('Login connexion', () => {
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

    cy.intercept(
      {
        method: 'GET',
        url: '/api/session',
      },
      []).as('session')

    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    cy.url().should('include', '/sessions')
  })

  it('Login fail', () => {
    // Visiter la page de login
    cy.visit('/login')

    // Interception de la requête POST /api/auth/login avec une erreur
    cy.intercept('POST', '/api/auth/login', {
      statusCode: 401, // Erreur d'authentification
      body: {
        error: 'Invalid credentials',
      },
    }).as('loginFail')

    // Entrer les identifiants incorrects
    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type("wrongpassword")

    // Soumettre le formulaire
    cy.get('button[type=submit]').click()

    // Attendre la requête POST /api/auth/login
    cy.wait('@loginFail')

    // Vérifier que l'URL ne change pas (l'utilisateur reste sur /login)
    cy.url().should('include', '/login')

    // Vérifier que le message d'erreur s'affiche
    cy.contains('An error occurred').should('be.visible')

    cy.visit('/sessions')
    //retourne sur login
    cy.url().should('include', '/login')
  })


  it('error handling in the absence of a mandatory field', () => {
    // Visiter la page de login
    cy.visit('/login')

    // Entrer un email mais laisser le champ "password" vide
    cy.get('input[formControlName=email]').type("yoga@studio.com")

    // Vérifier que le bouton de soumission est désactivé
    cy.contains('Submit').should('be.disabled')

    cy.get('input[formControlName=password]').click()
    cy.get('input[formControlName=email]').click()

    cy.contains('Password').should('have.css', 'color', 'rgb(244, 67, 54)')


  });

})  