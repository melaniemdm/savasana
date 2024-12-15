/// <reference types="cypress" />

describe('not-found spec', ()=>{
    it('display the Not Found page for an invalid URL', ()=>{
        // Visiter une URL invalide
     cy.visit('/invalid-url', { failOnStatusCode: false }); // Empêche Cypress d'échouer sur un 404
 
     // Vérifier que l'URL contient bien l'URL invalide
     cy.url().should('include', '/404');
 
     // Vérifier que le titre de la page "Not Found" est visible
     cy.contains('Page not found !').should('be.visible'); // Si votre page affiche "404"
 
     
     })
})