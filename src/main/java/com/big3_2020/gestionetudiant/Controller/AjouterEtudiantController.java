/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.big3_2020.gestionetudiant.Controller;

import com.big3_2020.gestionetudiant.Model.Classe;
import com.big3_2020.gestionetudiant.Model.Student;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 * FXML Controller class
 *
 * @author user
 */
public class AjouterEtudiantController implements Initializable {

    @FXML
    private TextField prenom_tfd;
    @FXML
    private TextField Age_tfd;
    @FXML
    private TextField name_tfd;
    @FXML
    private TextField Telephone_tfd;
    @FXML
    private Button btnEnregistrer;
    @FXML
    private ComboBox<String> cbbClasse;
    
    EtudiantViewController etu;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbbClasse.setItems(FXCollections.observableArrayList(getData()));
        // TODO
    }    

    @FXML
    private void btnEnregistrer_Click(ActionEvent event) {
        
        Student st = new Student();
        st.setNom(name_tfd.getText());
        st.setPrenom(prenom_tfd.getText());
        st.setAge(Integer.parseInt(Age_tfd.getText()));
        st.setTelephone(Telephone_tfd.getText());
        st.setIdClasse(getKeyForValue(cbbClasse.getValue()));
        Session ss= HibernateConfiguration.getSessionFactory().openSession();
        ss.save(st);
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Insertion reussi avec success");
        alert.show();
        ss.close();
        etu.LoadData();
    }
    
    private void ClearForm()
    {
       name_tfd.setText("");
       prenom_tfd.setText("");
       Age_tfd.setText("");
       Telephone_tfd.setText("");
       cbbClasse.setValue("Selectionnez");

    }
    private int getKeyForValue(String value) 
    {
        int id=0;
        Session ss= HibernateConfiguration.getSessionFactory().openSession();
        //Classes classe = ss.createQuery("SELECT s FROM Classes s",Classes.class).getResultList();
        String sql = "Select e from " + Classe.class.getName() + " e "//
        + " where e.Designation= :Designation ";
        Query<Classe> query = ss.createQuery(sql);
        query.setParameter("Designation", value);
        Classe cls = query.getSingleResult();
        id = cls.getIdClasse();
        ss.close();
        return id;
    }
     private List<String> getData() 
    {
        List<String> options = new ArrayList<>();
        options.add("Selectionnez");
        Session ss= HibernateConfiguration.getSessionFactory().openSession();
        List<Classe> classes = ss.createQuery("SELECT s FROM Classe s",Classe.class).getResultList();
        for (Classe cls : classes) {
        options.add(cls.getDesignation());
        }
        ss.close();

        return options;
   }
    
}
