/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.big3_2020.gestionetudiant.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author user
 */
public class StudentDashboardController implements Initializable {

    @FXML
    private BorderPane MenuPrincipal;
    @FXML
    private Pane MenuVertical;
    @FXML
    private Button btnProfesseurs;
    @FXML
    private Button btnEtudiant;
    @FXML
    private Button btnClasse;
    @FXML
    private Button btnCompte;
    @FXML
    private ImageView imgProjet;
    private BorderPane borderPanel2Menu;
    public static Label lblBienvenue11;
    @FXML
    private Button btnUtilisateur;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnProfesseur_Click(MouseEvent event) {
    }

    @FXML
    private void btnEtudiant_Click(MouseEvent event) throws IOException {
        AnchorPane view = FXMLLoader.load(getClass().getResource("/fxml/EtudiantView.fxml"));
        MenuPrincipal.setCenter(view);
    }


    @FXML
    private void btnClasses_Click(MouseEvent event) throws IOException {
        AnchorPane view = FXMLLoader.load(getClass().getResource("/fxml/SalleView.fxml"));
        MenuPrincipal.setCenter(view);
        
    }

    @FXML
    private void handleBtnClasses(ActionEvent event) {
    }


    @FXML
    private void btnCompte_Click(MouseEvent event) throws IOException {
        AnchorPane view = FXMLLoader.load(getClass().getResource("/fxml/SignIn.fxml"));
        MenuPrincipal.setCenter(view);
    }

    @FXML
    private void handleBtnCompte(ActionEvent event) {
    }

    @FXML
    private void imgProjet_click(MouseEvent event) {
    }

    @FXML
    private void btnUtilisateur_Click(MouseEvent event) throws IOException {
        AnchorPane view = FXMLLoader.load(getClass().getResource("/fxml/UserView.fxml"));
        MenuPrincipal.setCenter(view);
    }
    
}
