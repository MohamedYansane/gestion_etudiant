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
public class EditerEtudiantController implements Initializable {

    @FXML
    private TextField prenom_tfd;
    @FXML
    private TextField Age_tfd;
    @FXML
    private TextField name_tfd;
    @FXML
    private TextField Telephone_tfd;
    @FXML
    private Button btnModifier;
    @FXML
    private ComboBox<String> cbbClasse;
    
    Student sd = null;
    
    EtudiantViewController etu;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO
        cbbClasse.setItems(FXCollections.observableArrayList(getData()));
         //LoadData();
        EtudiantViewController et = new EtudiantViewController();
        et.tableStudent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue,
        newValue) -> {
         try 
         {
            if (newValue != null && newValue != oldValue) {

                sd=newValue;
                name_tfd.setText(String.valueOf(newValue.getNom()));
                prenom_tfd.setText(String.valueOf(newValue.getPrenom()));
                Age_tfd.setText(String.valueOf(newValue.getAge()));
                Telephone_tfd.setText(String.valueOf(newValue.getTelephone()));
                cbbClasse.setValue(String.valueOf(getValueForKey(newValue.getIdClasse())));
            }
            else {
            }
         } catch (Exception ex) {
            ex.printStackTrace();
         }
         }
         );
    }    

    @FXML
    private void btnEditer_Click(ActionEvent event) {
        sd.setNom(name_tfd.getText());
        sd.setPrenom(prenom_tfd.getText());
        sd.setAge(Integer.parseInt(Age_tfd.getText()));
        sd.setTelephone(Telephone_tfd.getText());
        sd.setIdClasse(getKeyForValue(cbbClasse.getValue()));
        Session ss= HibernateConfiguration.getSessionFactory().openSession();
        ss.beginTransaction();
        ss.merge(sd);
       // ss.saveOrUpdate(sd);
       
        ss.getTransaction().commit();
        ss.close();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Modification reussi avec success" +sd.getIdClasse());
        alert.show();
        etu.LoadData();
        
    }
    
    private int getKeyForValue(String value) {
        int id=0;
        Session ss= HibernateConfiguration.getSessionFactory().openSession();
        //Classes classe = ss.createQuery("SELECT s FROM Classes s",Classes.class).getResultList();
        String sql = "Select e from " + Classe.class.getName() + " e " + " where e.Designation= :Designation ";
        Query<Classe> query = ss.createQuery(sql);
        query.setParameter("Designation", value);
        Classe cls = query.getSingleResult();
        id = cls.getIdClasse();
        ss.close();
        return id;
    }
     public void setTextField(String name, String prenom, String telephone, int age, int classe)
    {
        name_tfd.setText(name);
        prenom_tfd.setText(prenom);
        Telephone_tfd.setText(telephone);
        Age_tfd.setText(String.valueOf(age));
        cbbClasse.setValue(String.valueOf(getValueForKey(classe)));
    }
     
      private String getValueForKey(int value) {
        String Id="";
        Session ss= HibernateConfiguration.getSessionFactory().openSession();
        //Classe classe = ss.createQuery("SELECT s FROM Classes s",Classes.class).getResultList();
        String sql = "Select e from " + Classe.class.getName() + " e " + " where e.idClasse= :Id ";
        Query<Classe> query = ss.createQuery(sql);
        query.setParameter("Id", value);
        Classe cls = query.getSingleResult();
        Id = cls.getDesignation();
        ss.close();
        return Id;
    }
      
     private List<String> getData() {
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
