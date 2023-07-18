/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.big3_2020.gestionetudiant.Controller;

import com.big3_2020.gestionetudiant.Model.Classe;
import com.big3_2020.gestionetudiant.Model.Student;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 * FXML Controller class
 *
 * @author user
 */
public class EtudiantViewController implements Initializable {

    @FXML
    private TextField rechercheNom_tfd;
    @FXML
    public TableView<Student> tableStudent;
    @FXML
    private TableColumn<Student, String> name_cell;
    @FXML
    private TableColumn<Student, String> prenom_cell;
    @FXML
    private TableColumn<Student, String> age_cell;
    @FXML
    private TableColumn<Student, String> telephone_cell;
    @FXML
    private TableColumn<Student, String> classe_cell;
    @FXML
    private TableColumn<Student, String> action_cell;
    @FXML
    private Button btnAjouter;
    @FXML
    private Button btnRefresh;
    @FXML
    private Button btnRechercher;
    
    Student sd = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         LoadData();
    }    

    @FXML
    private void getInsertStudent(MouseEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/AjouterEtudiant.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
        
    }

    @FXML
    private void btnRefresh_Click(ActionEvent event) {
        LoadData();
    }

    @FXML
    private void btnRechercher_Click(ActionEvent event) {
        LoadDataRecherche();
    }
    
    public void LoadData()
    {
        name_cell.setCellValueFactory((TableColumn.CellDataFeatures<Student, String> parametre) -> {
            Student s = parametre.getValue();
            return Bindings.createStringBinding(() -> String.valueOf(s.getNom()));
        });
        
        prenom_cell.setCellValueFactory((TableColumn.CellDataFeatures<Student, String> parametre) -> {
            Student s = parametre.getValue();
            return Bindings.createStringBinding(() -> String.valueOf(s.getPrenom()));
        });
        
        age_cell.setCellValueFactory((TableColumn.CellDataFeatures<Student, String> parametre) -> {
            Student s = parametre.getValue();
            return Bindings.createStringBinding(() -> String.valueOf(s.getAge()));
        });
        
        telephone_cell.setCellValueFactory((TableColumn.CellDataFeatures<Student, String> parametre) -> {
            Student s = parametre.getValue();
            return Bindings.createStringBinding(() -> String.valueOf(s.getTelephone()));
        });
        classe_cell.setCellValueFactory((TableColumn.CellDataFeatures<Student, String> parametre) -> {
            Student s = parametre.getValue();
            return Bindings.createStringBinding(() -> String.valueOf(getValueForKey(s.getIdClasse())));
        });
        
        Callback<TableColumn<Student, String>, TableCell<Student, String>> cellFoctory=(param)-> {
              
                // faire de tel sorte que les lignes  contiennent des buttons
                final TableCell<Student, String> cell = new TableCell<Student, String>() {
                  // Image imgEdit = new Image(getClass().getResourceAsStream("fxml.flaticonJava/edit(1).png"));
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
                            final Button editIcon = new Button("Editer");
                            deleteIcon.setStyle(
                            
                            "-fx-background-color:red;"
                            + "-fx-text-fill:white;"
                            );
                            
                           editIcon.setStyle(
                                    "-fx-background-color:green;"
                                    + "-fx-text-fill:white;"
                            );
                             //editIcon.setStyle("-fx-background-color:transparent;");
                            deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                                sd = tableStudent.getSelectionModel().getSelectedItem();
                                Session ss= HibernateConfiguration.getSessionFactory().openSession();
                                ss.beginTransaction();
                                Student st = ss.get(Student.class, sd.getId());
                                ss.delete(st);
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setContentText("L'etudiant " +sd.getNom() + " " +sd.getPrenom()+ " a été supprimé avec success");
                                alert.show();
                                ss.getTransaction().commit();
                                ss.close();
                                LoadData();
                                
                                
                            });
                            editIcon.setOnMouseClicked((MouseEvent event)->{
                                sd = tableStudent.getSelectionModel().getSelectedItem();
                                Parent parent = null;
                                FXMLLoader loader = new FXMLLoader ();
                                loader.setLocation(getClass().getResource("/fxml/EditerEtudiant.fxml"));
                                try {
                                    loader.load();
                                } catch (IOException ex) {
                                    Logger.getLogger(EditerEtudiantController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                EditerEtudiantController etu = loader.getController();
                                etu.setTextField(sd.getNom(), sd.getPrenom(),sd.getTelephone(), (int) sd.getAge(), sd.getIdClasse());
                                
                                Parent p = loader.getRoot();
                                Stage stage = new Stage();
                                stage.setScene(new Scene(p));
                                stage.initStyle(StageStyle.UTILITY);
                                stage.show();
                            
                            });
                            /*ImageView iv = new ImageView();
                            iv.setImage(imgEdit);
                            iv.setPreserveRatio(true);
                            iv.setSmooth(true);
                            iv.setCache(true);
                            editIcon.setGraphic(iv);*/
                            HBox managebtn = new HBox(editIcon, deleteIcon);
                            managebtn.setStyle("-fx-alignment:center");
                            
                            setGraphic(managebtn);
                            
                            setText(null);
                            
                        }
                    }
                };
                return cell;
            
            
        };
        action_cell.setCellFactory(cellFoctory);
        
        
        Session ss= HibernateConfiguration.getSessionFactory().openSession();
        List<Student> students = ss.createQuery("SELECT s FROM Student s",Student.class).getResultList();
        ss.close();
        tableStudent.setItems(FXCollections.observableArrayList(students));
        
       
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
    
    private int getKeyForValue(String value) {
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
    private void LoadDataRecherche()
    {
        String rechercherNom = rechercheNom_tfd.getText();
        String rechercherPreNom = rechercheNom_tfd.getText();

        name_cell.setCellValueFactory((TableColumn.CellDataFeatures<Student, String> parametre) -> {
        Student s = parametre.getValue();
        return Bindings.createStringBinding(() -> String.valueOf(s.getNom()));
        });

        prenom_cell.setCellValueFactory((TableColumn.CellDataFeatures<Student, String> parametre) -> {
        Student s = parametre.getValue();
        return Bindings.createStringBinding(() -> String.valueOf(s.getPrenom()));
        });

        age_cell.setCellValueFactory((TableColumn.CellDataFeatures<Student, String> parametre) -> {
        Student s = parametre.getValue();
        return Bindings.createStringBinding(() -> String.valueOf(s.getAge()));
        });

        telephone_cell.setCellValueFactory((TableColumn.CellDataFeatures<Student, String>
        parametre) -> {
        Student s = parametre.getValue();
        return Bindings.createStringBinding(() -> String.valueOf(s.getTelephone()));
        });

        classe_cell.setCellValueFactory((TableColumn.CellDataFeatures<Student, String> parametre) -> {
        Student s = parametre.getValue();
        return Bindings.createStringBinding(() -> String.valueOf(getValueForKey(s.getIdClasse())));
        });
        Session ss= HibernateConfiguration.getSessionFactory().openSession();
        String sql = "Select e from " + Student.class.getName() + " e "+ " where e.nom like :nom or e.prenom like :prenom ";
        Query<Student> query = ss.createQuery(sql);
        query.setParameter("nom", "%" + rechercherNom+ "%");
        query.setParameter("prenom", "%" + rechercherPreNom+ "%");
        List<Student> students = query.getResultList();
        //List<Student> students = ss.createQuery("SELECT s FROM Student s",Student.class).getResultList();
        ss.close();
        tableStudent.setItems(FXCollections.observableArrayList(students));


     }


}
