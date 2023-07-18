/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.big3_2020.gestionetudiant.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author user
 */
public class StudentViewController implements Initializable {

    @FXML
    private TextField prenom_tfd;
    @FXML
    private TextField Age_tfd;
    @FXML
    private TableView<?> tableStudent;
    @FXML
    private TableColumn<?, ?> name_cell;
    @FXML
    private TableColumn<?, ?> prenom_cell;
    @FXML
    private TableColumn<?, ?> age_cell;
    @FXML
    private TableColumn<?, ?> telephone_cell;
    @FXML
    private Button btnModifier;
    @FXML
    private Button btnEffacer;
    @FXML
    private Button btnQuitter;
    @FXML
    private TextField name_tfd;
    @FXML
    private TextField Telephone_tfd;
    @FXML
    private Button btnAjouter;
    @FXML
    private Button btnSupprimer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void Modifier_clik(ActionEvent event) {
    }

    @FXML
    private void Effacer_click(ActionEvent event) {
    }

    @FXML
    private void Quitter_clik(ActionEvent event) {
    }

    @FXML
    private void Ajouter_click(ActionEvent event) {
    }

    @FXML
    private void Supprimer_clik(ActionEvent event) {
    }
    
}
