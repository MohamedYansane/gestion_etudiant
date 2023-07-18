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
@Entity
public class Classe {
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idClasse;
    @Column(name = "Designation",nullable = false,length = 50)

    private String Designation;
    public void setIdClasse(int x)
    {
        this.idClasse = x;
    }
    public void setDesignation( String y)
    {
        this.Designation= y;
    }
    public int getIdClasse()
    {
        return idClasse;
    }
    public String getDesignation()
    {
        return Designation;
    }
    public String toString()
    {
        return "L'identifiant est :" +idClasse + ", et le nom de la classe: " +Designation;
    }
   
}
