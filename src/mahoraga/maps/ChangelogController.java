package mahoraga.maps;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ChangelogController implements Initializable {

    @FXML
    private Button buttonDeExcluir;

    @FXML
    private Button buttonListMunicipo;

    @FXML
    private Button buttonDeSaida;

    @FXML
    private Button buttonAtualizar;

    @FXML
    private Button buttonDeRanking;

    @FXML
    void switchToMain(ActionEvent event) {
        try {
            Parent telaUpdate = FXMLLoader.load(getClass().getResource("Main.fxml"));
            Scene scene2 = new Scene(telaUpdate);
            Stage janelaAtual = (Stage) buttonListMunicipo.getScene().getWindow();
            janelaAtual.setScene(scene2);
            janelaAtual.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void switchToDelete(ActionEvent event) {
        try {
            Parent telaUpdate = FXMLLoader.load(getClass().getResource("Excluir.fxml"));
            Scene scene3 = new Scene(telaUpdate);
            Stage janelaAtual = (Stage) buttonDeExcluir.getScene().getWindow();
            janelaAtual.setScene(scene3);
            janelaAtual.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToUpdate(ActionEvent event) {
        try {
            Parent telaUpdate = FXMLLoader.load(getClass().getResource("Atualizacao.fxml"));
            Scene scene2 = new Scene(telaUpdate);
            Stage janelaAtual = (Stage) buttonAtualizar.getScene().getWindow();
            janelaAtual.setScene(scene2);
            janelaAtual.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void switchToRanking(ActionEvent event) {
        try {
            Parent telaUpdate = FXMLLoader.load(getClass().getResource("Ranking.fxml"));
            Scene scene3 = new Scene(telaUpdate);
            Stage janelaAtual = (Stage) buttonDeRanking.getScene().getWindow();
            janelaAtual.setScene(scene3);
            janelaAtual.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void closeApp(ActionEvent event) {
        Stage stage = (Stage) buttonDeSaida.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
