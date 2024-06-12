/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mahoraga.maps;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mahoraga.maps.entities.Usuario;

/**
 * FXML Controller class
 *
 * @author Josias Junior Santos  <josiajrsantos@gmail.com>
 */
public class Controller {

    private final Usuario usuario;

    public Controller() {
        usuario = new Usuario();
    }

    @FXML
    private TextField usuarioTextField;
    @FXML
    private PasswordField senhaPasswordField;
    @FXML
    private Button loginButton;

    @FXML
    private Label loginMessageLabel;

    @FXML
    private Button cancelButton;

    public void loginButtonOnAction(ActionEvent e) {
        if (usuarioTextField.getText().isBlank() == false && senhaPasswordField.getText().isBlank() == false) {
            loginMessageLabel.setText("Usuário ou Senha invalidos.");

            validateLogin();

        } else {
            loginMessageLabel.setText("Por favor insira nome de usuário e senha.");
        }
    }

    public void cancelButtonOnAction(ActionEvent e) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void validateLogin() {
        String usuarioInput = usuarioTextField.getText();
        String senhaInput = senhaPasswordField.getText();

        if (usuario.validarCredenciais(usuarioInput, senhaInput)) {
            loginMessageLabel.setText("Login bem-sucedido!");
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.close();
            // mainApp.iniciarTelaPrincipal(new Stage());
        } else {
            usuario.diminuirTentativa();
            int tentativasRestantes = usuario.getTentativasRestantes();
            if (tentativasRestantes > 0) {
                loginMessageLabel.setText("Usuário ou senha incorretos. Tentativas restantes: " + tentativasRestantes);
            } else {
                loginMessageLabel.setText("Número de tentativas excedido. O programa será fechado.");
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.close();
                System.exit(0);
            }
        }
    }
}
