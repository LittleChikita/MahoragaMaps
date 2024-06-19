package mahoraga.maps;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mahoraga.maps.entities.CSVUtils;
import mahoraga.maps.entities.Municipio;

public class AtualizacaoController implements Initializable {

    private MainController mainController;
    private Municipio municipioSelecionado;
    private ObservableList<Municipio> municipios;

    private final String inCSVPath = "C:\\Users\\Chê Chikita\\Desktop\\inCSV";
    private final String outCSVPath = "C:\\Users\\Chê Chikita\\Desktop\\outCSV";
    private final String changelogPath = "C:\\Users\\Chê Chikita\\Desktop\\changelog\\changelog.csv";

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

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
    private Button buttonChangelog;

    @FXML
    private TextField txtArea;

    @FXML
    private TextField txtCodigoIBGE;

    @FXML
    private TextField txtDomicilios;

    @FXML
    private TextField txtEstado;

    @FXML
    private TextField txtIdh;

    @FXML
    private TextField txtIdhEducacao;

    @FXML
    private TextField txtIdhLongevidade;

    @FXML
    private TextField txtMicrorregiao;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtPeaDia;

    @FXML
    private TextField txtPibTotal;

    @FXML
    private TextField txtPopulacao;

    @FXML
    private TextField txtRendaMedia;

    @FXML
    private TextField txtRendaNominal;

    @FXML
    private ComboBox<String> CBMunicipios;

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
            // Aqui você pode implementar uma mensagem de erro ao usuário se a troca de cena falhar
        }
    }

    @FXML
    private void selecionarMunicipio(ActionEvent event) {
        String selectedMunicipioName = CBMunicipios.getSelectionModel().getSelectedItem();
        municipioSelecionado = municipios.stream().filter(m -> m.getNome().equals(selectedMunicipioName)).findFirst().orElse(null);
        exibirDetalhesMunicipio();
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
    void switchToChangelog(ActionEvent event) {
        try {
            Parent telaUpdate = FXMLLoader.load(getClass().getResource("Changelog.fxml"));
            Scene scene3 = new Scene(telaUpdate);
            Stage janelaAtual = (Stage) buttonChangelog.getScene().getWindow();
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
    void salvarAlteracao(ActionEvent event) {
        if (municipioSelecionado == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setHeaderText("Nenhum município selecionado");
            alert.setContentText("Por favor, selecione um município antes de salvar as alterações.");
            alert.showAndWait();
            return;
        }
        if (isAnyFieldEmpty()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setHeaderText("Campos vazios");
            alert.setContentText("Por favor, preencha todos os campos antes de salvar as alterações.");
            alert.showAndWait();
            return;
        }
        if (containsSpecialCharacters()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText("Caracteres especiais não permitidos");
            alert.setContentText("Por favor, remova todos os caracteres especiais dos campos.");
            alert.showAndWait();
            return;
        }
      

        String oldPopulacao = municipioSelecionado.getPopulacao();
        String oldDomicilios = municipioSelecionado.getDomicilios();
        String oldPibTotal = municipioSelecionado.getPibTotal();
        String oldIdh = municipioSelecionado.getIdh();
        String oldRendaMedia = municipioSelecionado.getRendaMedia();
        String oldRendaNominal = municipioSelecionado.getRendaNominal();
        String oldPeaDia = municipioSelecionado.getPeaDia();
        String oldIdhEducacao = municipioSelecionado.getIdhEducacao();
        String oldIdhLongevidade = municipioSelecionado.getIdhLongevidade();

        municipioSelecionado.setPopulacao(txtPopulacao.getText());
        municipioSelecionado.setDomicilios(txtDomicilios.getText());
        municipioSelecionado.setPibTotal(txtPibTotal.getText());
        municipioSelecionado.setIdh(txtIdh.getText());
        municipioSelecionado.setRendaMedia(txtRendaMedia.getText());
        municipioSelecionado.setRendaNominal(txtRendaNominal.getText());
        municipioSelecionado.setPeaDia(txtPeaDia.getText());
        municipioSelecionado.setIdhEducacao(txtIdhEducacao.getText());
        municipioSelecionado.setIdhLongevidade(txtIdhLongevidade.getText());

        CSVUtils.escreverCSV(new ArrayList<>(municipios), outCSVPath + "\\arquivo_saida.csv");
        writeChangelogEntry("Populacao", oldPopulacao, txtPopulacao.getText());
        writeChangelogEntry("Domicilios", oldDomicilios, txtDomicilios.getText());
        writeChangelogEntry("PibTotal", oldPibTotal, txtPibTotal.getText());
        writeChangelogEntry("Idh", oldIdh, txtIdh.getText());
        writeChangelogEntry("RendaMedia", oldRendaMedia, txtRendaMedia.getText());
        writeChangelogEntry("RendaNominal", oldRendaNominal, txtRendaNominal.getText());
        writeChangelogEntry("PeaDia", oldPeaDia, txtPeaDia.getText());
        writeChangelogEntry("IdhEducacao", oldIdhEducacao, txtIdhEducacao.getText());
        writeChangelogEntry("IdhLongevidade", oldIdhLongevidade, txtIdhLongevidade.getText());

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText("Os valores do município foram atualizados com sucesso!");
        alert.showAndWait();
    }

    private void writeChangelogEntry(String campo, String valorAntigo, String valorNovo) {
        if (Objects.equals(valorAntigo, valorNovo)) {
            return;
        }
        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dataHora = agora.format(formatter);
        String municipio = municipioSelecionado.getNome();

        try (FileWriter fw = new FileWriter(changelogPath, true); BufferedWriter bw = new BufferedWriter(fw); PrintWriter out = new PrintWriter(bw)) {

            out.println(dataHora + ";" + municipio + ";" + campo + ";" + valorAntigo + ";" + valorNovo);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isAnyFieldEmpty() {
        return txtPopulacao.getText().isEmpty()
                || txtDomicilios.getText().isEmpty()
                || txtPibTotal.getText().isEmpty()
                || txtIdh.getText().isEmpty()
                || txtRendaMedia.getText().isEmpty()
                || txtRendaNominal.getText().isEmpty()
                || txtPeaDia.getText().isEmpty()
                || txtIdhEducacao.getText().isEmpty()
                || txtIdhLongevidade.getText().isEmpty();
    }

    private boolean containsSpecialCharacters() {
        String regex = "^[0-9.,]+$";
        return !txtPopulacao.getText().matches(regex)
                || !txtDomicilios.getText().matches(regex)
                || !txtPibTotal.getText().matches(regex)
                || !txtIdh.getText().matches(regex)
                || !txtRendaMedia.getText().matches(regex)
                || !txtRendaNominal.getText().matches(regex)
                || !txtPeaDia.getText().matches(regex)
                || !txtIdhEducacao.getText().matches(regex)
                || !txtIdhLongevidade.getText().matches(regex);
    }
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadData();
        CBMunicipios.setItems(municipios.stream().map(Municipio::getNome).collect(Collectors.toCollection(FXCollections::observableArrayList)));
    }

    private void loadData() {
        File inDir = new File(inCSVPath);
        File outDir = new File(outCSVPath);

        File[] inFiles = inDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));
        File[] outFiles = outDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));

        if (inFiles == null || inFiles.length == 0) {
            // Se inCSV estiver vazio, carrega os dados de outCSV
            loadFromDirectory(outFiles);
            return;
        }

        if (outFiles != null && outFiles.length > 0) {
            // Verifica o arquivo mais recente em outCSV
            File mostRecentOutFile = Arrays.stream(outFiles)
                    .max((f1, f2) -> Long.compare(f1.lastModified(), f2.lastModified()))
                    .orElse(null);

            if (mostRecentOutFile != null) {
                // Verifica se o arquivo mais recente em outCSV é mais novo que todos em inCSV
                long mostRecentOutModified = mostRecentOutFile.lastModified();
                boolean inCsvOlderThanOutCsv = Arrays.stream(inFiles)
                        .allMatch(inFile -> inFile.lastModified() < mostRecentOutModified);

                if (inCsvOlderThanOutCsv) {
                    // Carrega os dados do arquivo mais recente em outCSV
                    loadFromFile(mostRecentOutFile);
                    return;
                }
            }
        }

        // Carrega os dados do primeiro arquivo em inCSV por padrão
        if (inFiles.length > 0) {
            loadFromFile(inFiles[0]);
        }
    }

    private void loadFromDirectory(File[] files) {
        if (files != null && files.length > 0) {
            File mostRecentFile = Arrays.stream(files)
                    .max((f1, f2) -> Long.compare(f1.lastModified(), f2.lastModified()))
                    .orElse(null);
            if (mostRecentFile != null) {
                loadFromFile(mostRecentFile);
            }
        }
    }

    private void loadFromFile(File file) {
        List<Municipio> lista = CSVUtils.lerCSV(file.getAbsolutePath());
        municipios = FXCollections.observableArrayList(lista);
    }

    private void exibirDetalhesMunicipio() {
        if (municipioSelecionado != null) {
            txtCodigoIBGE.setText(municipioSelecionado.getCodigoIBGE());
            txtCodigoIBGE.setDisable(true);
            txtNome.setText(municipioSelecionado.getNome());
            txtNome.setDisable(true);
            txtEstado.setText(municipioSelecionado.getEstado());
            txtEstado.setDisable(true);
            txtMicrorregiao.setText(municipioSelecionado.getMicroRegiao());
            txtMicrorregiao.setDisable(true);
            txtArea.setText(municipioSelecionado.getAreaKm());
            txtArea.setDisable(true);
            txtPopulacao.setText(municipioSelecionado.getPopulacao());
            txtDomicilios.setText(municipioSelecionado.getDomicilios());
            txtPibTotal.setText(municipioSelecionado.getPibTotal());
            txtIdh.setText(municipioSelecionado.getIdh());
            txtRendaMedia.setText(municipioSelecionado.getRendaMedia());
            txtRendaNominal.setText(municipioSelecionado.getRendaNominal());
            txtPeaDia.setText(municipioSelecionado.getPeaDia());
            txtIdhEducacao.setText(municipioSelecionado.getIdhEducacao());
            txtIdhLongevidade.setText(municipioSelecionado.getIdhLongevidade());
        }
    }
}