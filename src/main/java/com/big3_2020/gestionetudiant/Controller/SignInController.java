/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.big3_2020.gestionetudiant.Controller;

import com.big3_2020.gestionetudiant.Model.Utilisateur;
import com.big3_2020.gestionetudiant.Util.Message;
import com.big3_2020.gestionetudiant.Util.PasswordUtil;
import com.big3_2020.gestionetudiant.Util.Validator;
import java.io.IOException;
import java.net.URL;
import java.security.spec.InvalidKeySpecException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 * FXML Controller class
 *
 * @author user
 */
public class SignInController implements Initializable {

    
    private Utilisateur utilisateur;

    private Boolean rep=false;
    @FXML
    private AnchorPane lblReset;
    @FXML
    private TextField txtIdentifiant;
    @FXML
    private TextField txtPassword;
    @FXML
    private Button btnConecter;
    @FXML
    private Label lblIncorrect;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    



    @FXML
    private void resetTextField_click(MouseEvent event) {
        ClearForm();
    }
    private void ClearForm()
    {
        txtIdentifiant.setText("");
        txtPassword.setText("");
    }

    @FXML
    private void btnConecter_Click(ActionEvent event) throws InvalidKeySpecException, IOException {
        if("".equals(txtIdentifiant.getText().trim()) || "".equals(txtPassword.getText().trim())){
            Message.alerteWithoutHeaderText("Informations","Renseigner le nom d'utilisateur ou mot de passe!");
            return;
        }else
        {
            if(!Validator.isAlphaNumeric(txtIdentifiant.getText()))
            {
                Message.alerteWithoutHeaderText("Informations","Le format du nom d'utilisateur est incorrect!");
                ClearForm();
                return;
            }
            Session ss= HibernateConfiguration.getSessionFactory().openSession();
            String sql = "Select e from " + Utilisateur.class.getName() + " e "//
            + " where e.identifiant= :identifiant ";
            Query<Utilisateur> query = ss.createQuery(sql);
            query.setParameter("identifiant", txtIdentifiant.getText());
            utilisateur = query.getSingleResult();
            ss.close();
            rep = PasswordUtil.verifyUserPassword(txtPassword.getText(), utilisateur.getMotdepasse(), utilisateur.getSaltPassword());
            if(rep)
            {
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/StudentDashboard.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Gestion Etablissment");
                stage.setScene(scene);
                stage.show();
                ((Node)(event.getSource())).getScene().getWindow().hide();
            }
            else
            {
                //Message.alerteWithoutHeaderText("Informations","Nom d'utilisateur ou mot de passe incorrect!");
                lblIncorrect.setText("Nom d'utilisateur ou mot de passe incorrect!");
                ClearForm();
                return;
            }
        }
    }

    @FXML
    private void img_exit(MouseEvent event) {
        System.exit(0);
    }

    
}
