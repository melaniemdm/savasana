/// <reference types="cypress" />

describe('not-found spec', () => {
    it('display the Not Found page for an invalid URL', () => {
        // Visite une URL invalide
        cy.visit('/invalid-url', { failOnStatusCode: false }); // Empêche Cypress d'échouer sur un 404

        // Vérifie que l'URL contient bien l'URL invalide
        cy.url().should('include', '/404');

        cy.contains('Page not found !').should('be.visible');


    })
})