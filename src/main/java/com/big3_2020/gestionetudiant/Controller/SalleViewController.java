/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.big3_2020.gestionetudiant.Controller;

import com.big3_2020.gestionetudiant.Model.Classe;
import com.big3_2020.gestionetudiant.Model.Student;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.hibernate.Session;

/**
 * FXML Controller class
 *
 * @author user
 */
public class SalleViewController implements Initializable {

    @FXML
    private Button btnAjouter;
    @FXML
    private TableView<Classe> tableClasses;
    @FXML
    private TableColumn<Classe, String> cell_id;
    @FXML
    private TableColumn<Classe, String> cell_Designation;
    @FXML
    private TableColumn Action_cell;
    @FXML
    private TextField designation_tfd;
    @FXML
    private Button btnEffacer;
    @FXML
    private Button btnModifier;
    
    Classe cl = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
            LoadData(); 
            tableClasses.getSelectionModel().selectedItemProperty().addListener((observable, oldValue,
            newValue) -> {
            try {
                if (newValue != null && newValue != oldValue) {

                    cl=newValue;
                    designation_tfd.setText(String.valueOf(newValue.getDesignation()));
                } else {
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }    

    @FXML
    private void btnEnregistrer_Click(ActionEvent event) {
        Classe cla = new Classe();
        cla.setDesignation(designation_tfd.getText());
        Session ss= HibernateConfiguration.getSessionFactory().openSession();
        ss.save(cla);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Insertion reussi avec success");
        alert.setHeaderText("Message d'Insertion");
        alert.show();
        ss.close();
        LoadData();
    }

    @FXML
    private void btnEffacer_Click(ActionEvent event) {
        designation_tfd.setText("");
    }

    @FXML
    private void btnModifier_Click(ActionEvent event) {
        cl.setDesignation(designation_tfd.getText());
        Session ss= HibernateConfiguration.getSessionFactory().openSession();
        ss.beginTransaction();
        ss.merge(cl);
        ss.getTransaction().commit();
        ss.close();
        LoadData();
    }
    
    private void LoadData()
    {
        cell_id.setCellValueFactory((TableColumn.CellDataFeatures<Classe, String> parametre) -> {
        Classe s = parametre.getValue();
            return Bindings.createStringBinding(() -> String.valueOf(s.getIdClasse()));
        });

        cell_Designation.setCellValueFactory((TableColumn.CellDataFeatures<Classe, String>
       parametre) -> {
        Classe s = parametre.getValue();
        return Bindings.createStringBinding(() -> String.valueOf(s.getDesignation()));
        });
           
        Callback<TableColumn<Student, String>, TableCell<Student, String>> cellFoctory=(param)-> {
              
                // faire de tel sorte que les lignes  contiennent des buttons
                final TableCell<Student, String> cell = new TableCell<Student, String>() {
                   
                    @Override
                    public void updateItem(String item, boolean empty) {
                       super.updateItem(item, empty);
                        if (empty)
                        {
                            setGraphic(null);
                            setText(null);
                            
                        }
                        else
                        {
                            
                            //Creating a Button
                            
                            final Button deleteIcon = new Button("Supprimer");
                          
                            deleteIcon.setStyle(
                            
                            "-fx-background-color:red;"
                            + "-fx-text-fill:white;"
                            );
                            
                           
                            deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                                cl = tableClasses.getSelectionModel().getSelectedItem();
                                Session ss= HibernateConfiguration.getSessionFactory().openSession();
                                ss.beginTransaction();
                                Classe cla = ss.get(Classe.class, cl.getIdClasse());
                                ss.delete(cla);
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setContentText("Suppression effectuée avec success");
                                alert.show();
                                ss.getTransaction().commit();
                                ss.close();
                                LoadData();
                                
                                
                            });
                           
                            HBox managebtn = new HBox(deleteIcon);
                            managebtn.setStyle("-fx-alignment:center");
                            
                            setGraphic(managebtn);
                            
                            setText(null);
                            
                        }
                    }
                };
                return cell;
            
            
        };
        Action_cell.setCellFactory(cellFoctory);
        
        
        Session ss= HibernateConfiguration.getSessionFactory().openSession();
        List<Classe> students = ss.createQuery("SELECT s FROM Classe s",Classe.class).getResultList();
        ss.close();
        tableClasses.setItems(FXCollections.observableArrayList(students));

        //ClearForm();
    }
    
}
