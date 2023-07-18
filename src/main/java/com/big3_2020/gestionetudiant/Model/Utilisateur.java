/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.big3_2020.gestionetudiant.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author user
 */
/**
*
* @author Administrateur
*/
@Entity
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    
    @Column(name = "nom",nullable = false,length = 50)
    private String nom;
    
    @Column(name = "prenom",nullable = false,length = 50)
    private String prenom;
    
    @Column(name = "identifiant",nullable = false,length = 50)
    private String identifiant;
    
    @Column(name = "role",nullable = false,length = 10)
    private String role;
    
    @Column(name = "motdepasse",nullable = false,length = 255)
    private String motdepasse;
    
    @Column(name = "saltPassword",columnDefinition = "text",nullable = false)
    private String saltPassword;
    
    public String getSaltPassword() {
        return saltPassword;
    }
    public void setSaltPassword(String saltPassword) {
        this.saltPassword = saltPassword;
    }
    public String getIdentifiant() {
        return identifiant;
    }
    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public int getId() {
        return Id;
    }
    public void setId(int Id) {
        this.Id = Id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getMotdepasse() {
        return motdepasse;
    }
    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }

    @Override
    public String toString() {
        return nom+ " "+ prenom+" "+role;
    }
}
