/// <reference types="cypress" />

describe('update session', ()=>{
    it('update session', ()=>{

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
      
      // Intercepter l'API des professeurs
      cy.intercept('GET', '/api/teacher', {
        body: [
        { id: 1, firstName: 'Margot', lastName: 'DELAHAYE' },
        { id: 2, firstName: 'John', lastName: 'DOE' },
        ],
        }).as('teachers');
      
      // Interception de la requête GET pour le détail d'une session
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
      
      
       // Interception de la requête PUT pour le détail d'une session
       cy.intercept('PUT', '/api/session/1', {
        statusCode: 200,
        body: {
          id: 1,
          name: 'Yoga super fun bébé',
          date: new Date().toISOString().split('T')[0],
          teacher_id: 2,
          description: 'New Yoga super fun session for babies',
        },
      }).as('updateSession')
         

      cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)
            cy.contains("Edit").click()
           
            cy.url().should('include', '/sessions')
             
      cy.contains('Margot').should('be.visible')
            cy.get('mat-select[formControlName=teacher_id]').click(); // Ouvrir la liste
           
         

      cy.contains('John DOE').click();
      cy.contains('John DOE').should('be.visible')
    cy.contains('Save').click()
     cy.contains('Session updated').should('be.visible');
      cy.url().should('include', '/sessions')
      })


it('display info session', ()=>{
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
  
  // Intercepter l'API des professeurs
  cy.intercept('GET', '/api/teacher', {
    body: [
    { id: 1, firstName: 'Margot', lastName: 'DELAHAYE' },
    { id: 2, firstName: 'John', lastName: 'DOE' },
    ],
    }).as('teachers');
  
  // Interception de la requête GET pour le détail d'une session
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

cy.contains("Edit").click()
cy.get('input[formControlName=name]').clear()
cy.get('input[formControlName=date]').click()
cy.contains('Name').should('have.css', 'color', 'rgb(244, 67, 54)')
cy.contains('Save').should('be.disabled')
})

})