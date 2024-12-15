/// <reference types="cypress" />

describe('Register spec', () => {
    it('Register successfull', () => {
        cy.visit('/register')

          // Interception de la requête POST pour l'inscription
          cy.intercept('POST', '/api/auth/register', {
            statusCode: 200, // Répondre avec un statut 200
        }).as('registerRequest'); // Nommer l'interception

        cy.get('input[formControlName=firstName]').type("melanie")
        cy.get('input[formControlName=lastName]').type("lisanna")
        cy.get('input[formControlName=email]').type("mel@studio.com")
        cy.get('input[formControlName=password]').type("test!1234")
        
        cy.contains('Submit').click()

        // Attendre que la requête POST pour l'inscription soit déclenchée
        cy.wait('@registerRequest');
        
       cy.url().should('include', '/login')
        
    })

    it('error handling in the absence of a mandatory field', () => {
      cy.visit('/register')

      cy.get('input[formControlName=firstName]').type("melanie")
      cy.get('input[formControlName=lastName]').type("lisanna")
      cy.get('input[formControlName=email]').type("mel@studio.com")

   // Vérifier que le bouton de soumission est désactivé
      cy.contains('Submit').should('be.disabled')

      cy.get('input[formControlName=password').click()
      cy.get('input[formControlName=email').click()

     cy.contains('Password').should('have.css', 'color', 'rgb(244, 67, 54)')
  })
    
});