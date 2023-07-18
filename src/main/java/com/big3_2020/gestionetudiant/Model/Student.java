package com.big3_2020.gestionetudiant.Model;

import javax.persistence.*;

@Entity
public class Student {

   
    
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(name = "nom",nullable = false,length = 50)
    private String nom;

    @Column(name = "prenom",nullable = false,length = 50)
    private String prenom;

    private int age;
    
    @Column(name = "telephone",nullable = false,length = 50)
    private String telephone;
 
     
    private int idClasse;
    
    public int getIdClasse() {
    return idClasse;
    }
    public void setIdClasse(int idClasse) {
    this.idClasse = idClasse;
    }
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
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

    public long getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    

    @Override
    public String toString() {
        return nom+ " "+ prenom+" "+age;
    }
    
   
}
