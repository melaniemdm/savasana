/// <reference types="cypress" />

describe('Sessions spec', () => {
    it('display sessions list', () => {
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
          },
          {
            id: 2,
            name: 'Yoga pour adulte',
            date: new Date().toISOString().split('T')[0],
            teacher_id: 2,
            description: 'New Yoga fun ',
          }
        ],
        }).as('session')
    
            
       
        cy.get('input[formControlName=email]').type("yoga@studio.com")
        cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)
        
        cy.wait('@session')
        cy.url().should('include', '/sessions')
         
    // Vérifier que la session est affichée dans la liste
  cy.contains('Yoga fun bébé').should('be.visible')
  cy.contains('Yoga pour adulte').should('be.visible')
  cy.contains('New Yoga fun session for babies').should('be.visible')
  cy.contains('New Yoga fun ').should('be.visible')
       
    })

    it('appearance of the detail and edit button on the admin session', () => {   
  // Vérifier que les bouttons Detail et edit sont visibles                   
cy.contains('Detail').should('be.visible')
cy.contains('Edit').should('be.visible')

    })

    it('no appearance of the edit button  and apparence of the the detail button on the user session', () => {
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
              id: 2,
              name: 'Yoga fun bébé',
              date: new Date().toISOString().split('T')[0],
              teacher_id: 2,
              description: 'New Yoga fun session for babies 3 mois',
            }],
          }).as('session')

          cy.intercept('GET', '/api/session/2', {
            statusCode: 200,
            body: {
              id: 2,
              name: 'Yoga fun bébé',
              date: new Date().toISOString().split('T')[0],
              teacher_id: 2,
              description: 'New Yoga fun session for babies',
            },
          }).as('sessionDetail')

          cy.get('input[formControlName=email]').type("mel@studio.com")
          cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)
        cy.contains('Detail').should('be.visible')
       cy.contains('Edit').should('not.exist')
    })


})