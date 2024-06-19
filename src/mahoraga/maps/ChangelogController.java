package mahoraga.maps;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ChangelogController implements Initializable {

    private final String changelogPath = "C:\\Users\\Chê Chikita\\Desktop\\changelog\\changelog.csv";

    @FXML
    private TableView<LogEntry> tableView;

    @FXML
    private TableColumn<LogEntry, String> colDate;

    @FXML
    private TableColumn<LogEntry, String> colMunicipio;

    @FXML
    private TableColumn<LogEntry, String> colField;

    @FXML
    private TableColumn<LogEntry, String> colOldValue;

    @FXML
    private TableColumn<LogEntry, String> colNewValue;

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
        colDate.setCellValueFactory(new PropertyValueFactory<>("dataHora"));
        colMunicipio.setCellValueFactory(new PropertyValueFactory<>("municipio"));
        colField.setCellValueFactory(new PropertyValueFactory<>("campo"));
        colOldValue.setCellValueFactory(new PropertyValueFactory<>("valorAntigo"));
        colNewValue.setCellValueFactory(new PropertyValueFactory<>("valorNovo"));

        tableView.setItems(loadLogEntries());
    }

    private ObservableList<LogEntry> loadLogEntries() {
        List<LogEntry> logEntries = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(changelogPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                if (values.length == 5) {
                    logEntries.add(new LogEntry(values[0], values[1], values[2], values[3], values[4]));
                } else if (values.length == 3 && values[2].equals("Dados excluídos")) {
                    logEntries.add(new LogEntry(values[0], values[1], values[2]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return FXCollections.observableArrayList(logEntries);
    }

    public static class LogEntry {

        private String dataHora;
        private String municipio;
        private String campo;
        private String valorAntigo;
        private String valorNovo;

        public LogEntry(String dataHora, String municipio, String campo, String valorAntigo, String valorNovo) {
            this.dataHora = dataHora;
            this.municipio = municipio;
            this.campo = campo;
            this.valorAntigo = valorAntigo;
            this.valorNovo = valorNovo;
        }

        public LogEntry(String dataHora, String municipio, String acao) {
            this.dataHora = dataHora;
            this.municipio = municipio;
            this.campo = "Dados Excluídos";
            this.valorAntigo = "";
            this.valorNovo = acao;
        }

        public String getDataHora() {
            return dataHora;
        }

        public String getMunicipio() {
            return municipio;
        }

        public String getCampo() {
            return campo;
        }

        public String getValorAntigo() {
            return valorAntigo;
        }

        public String getValorNovo() {
            return valorNovo;
        }
    }
}
