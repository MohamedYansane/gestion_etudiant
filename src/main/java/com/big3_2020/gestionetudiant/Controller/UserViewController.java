/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.big3_2020.gestionetudiant.Controller;

import com.big3_2020.gestionetudiant.Model.Utilisateur;
import com.big3_2020.gestionetudiant.Util.PasswordUtil;
import java.net.URL;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.hibernate.Session;

/**
 * FXML Controller class
 *
 * @author user
 */
public class UserViewController implements Initializable {

    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtPrenom;
    @FXML
    private TextField txtIdentifiant;
    @FXML
    private ComboBox<String> cbbRole;
    @FXML
    private Button btnAjouter;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnAnnuler;
    @FXML
    private Button btnRestPassword;
    @FXML
    private TableView<Utilisateur> tableUtilisateur;
    @FXML
    private TableColumn<Utilisateur, String> cell_nom;
    @FXML
    private TableColumn<Utilisateur, String> cell_prenom;
    @FXML
    private TableColumn<Utilisateur, String> cell_identifiant;
    @FXML
    private TableColumn<Utilisateur, String> cell_role;
    
    Utilisateur sd = null;
    @FXML
    private Button btnUpdate;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        btnRestPassword.setDisable(true);
        cbbRole.setItems(FXCollections.observableArrayList(getData()));
        LoadData();

        tableUtilisateur.getSelectionModel().selectedItemProperty().addListener((observable, oldValue,
       newValue) -> {
        try {
        if (newValue != null && newValue != oldValue)
        {
            btnAjouter.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
            btnRestPassword.setDisable(false);
            sd=newValue;
            txtNom.setText(String.valueOf(newValue.getNom()));
            txtPrenom.setText(String.valueOf(newValue.getPrenom()));
            txtIdentifiant.setText(String.valueOf(newValue.getIdentifiant()));
            cbbRole.setValue(String.valueOf(newValue.getRole()));
        }else
        {
        }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        });
    }    

    @FXML
    private void Ajouter_Click(ActionEvent event) throws InvalidKeySpecException {
        Utilisateur st = new Utilisateur();
        st.setNom(txtNom.getText());
        st.setPrenom(txtPrenom.getText());
        st.setIdentifiant(txtIdentifiant.getText());
        String salt = PasswordUtil.getSalt(30);
        String crypted = PasswordUtil.generateSecurePassword("passer123",salt);
        st.setMotdepasse(crypted);
        st.setSaltPassword(salt);
        st.setRole(cbbRole.getValue());
        Session ss= HibernateConfiguration.getSessionFactory().openSession();
        ss.save(st);
        ss.close();
        LoadData();
    }

    @FXML
    private void Modifier_Click(ActionEvent event) {
        sd.setNom(txtNom.getText());
        sd.setPrenom(txtPrenom.getText());
        sd.setIdentifiant(txtIdentifiant.getText());
        sd.setRole(cbbRole.getValue());
        Session ss= HibernateConfiguration.getSessionFactory().openSession();
        ss.beginTransaction();
        ss.merge(sd);
        ss.getTransaction().commit();
        ss.close();
        LoadData();

    }

    @FXML
    private void Delete_Click(ActionEvent event) {
        Session ss= HibernateConfiguration.getSessionFactory().openSession();
        ss.beginTransaction();
        Utilisateur st = ss.get(Utilisateur.class, sd.getId());
        ss.delete(st);
        ss.getTransaction().commit();
        ss.close();
        LoadData();

    }

    @FXML
    private void Annuler_Click(ActionEvent event) {
        ClearForm();
    }

    @FXML
    private void ResetPassword_Click(ActionEvent event) throws InvalidKeySpecException {
        String salt = PasswordUtil.getSalt(30);
        String crypted = PasswordUtil.generateSecurePassword("passer123",salt);
        sd.setMotdepasse(crypted);
        sd.setSaltPassword(salt);
        Session ss= HibernateConfiguration.getSessionFactory().openSession();
        ss.beginTransaction();
        ss.merge(sd);
        ss.getTransaction().commit();
        ss.close();
        LoadData(); 
    }
    
    private void LoadData()
    {
        cell_nom.setCellValueFactory((TableColumn.CellDataFeatures<Utilisateur, String> parametre) ->
        {
            Utilisateur s = parametre.getValue();
            return Bindings.createStringBinding(() -> String.valueOf(s.getNom()));
        });

        cell_prenom.setCellValueFactory((TableColumn.CellDataFeatures<Utilisateur, String>parametre) -> {
            Utilisateur s = parametre.getValue();
            return Bindings.createStringBinding(() -> String.valueOf(s.getPrenom()));
        });

        cell_identifiant.setCellValueFactory((TableColumn.CellDataFeatures<Utilisateur, String>parametre) -> {
            Utilisateur s = parametre.getValue();
            return Bindings.createStringBinding(() -> String.valueOf(s.getIdentifiant()));
        });

        cell_role.setCellValueFactory((TableColumn.CellDataFeatures<Utilisateur, String> parametre) ->
        {
            Utilisateur s = parametre.getValue();
            return Bindings.createStringBinding(() -> String.valueOf(s.getRole()));
        });

        Session ss= HibernateConfiguration.getSessionFactory().openSession();
        List<Utilisateur> utilisateurs = ss.createQuery("SELECT s FROM Utilisateur s",Utilisateur.class).getResultList();
        ss.close();
        tableUtilisateur.setItems(FXCollections.observableArrayList(utilisateurs));

        ClearForm();
    }
    
    private void ClearForm()
    {
        txtNom.setText("");
        txtPrenom.setText("");
        txtIdentifiant.setText("");
        cbbRole.setValue("Selectionnez");
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        btnAjouter.setDisable(false);
        
    }
    
    private List<String> getData() {
        List<String> options = new ArrayList<>();
        options.add("Selectionnez");
        options.add("admin");
        options.add("invite");

        return options;
    }
    
    


    
}
