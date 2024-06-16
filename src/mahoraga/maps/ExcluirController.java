/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mahoraga.maps;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mahoraga.maps.entities.CSVUtils;
import mahoraga.maps.entities.Municipio;

/**
 * FXML Controller class
 *
 * @author Josias Junior Santos  <josiajrsantos@gmail.com>
 */
public class ExcluirController implements Initializable {

    private Municipio municipioSelecionado;
    private ObservableList<Municipio> municipios;

    private final String inCSVPath = "C:\\Users\\Chê Chikita\\Desktop\\inCSV";
    private final String outCSVPath = "C:\\Users\\Chê Chikita\\Desktop\\outCSV";

    @FXML
    private ComboBox<String> CBMunicipios;

    @FXML
    private Button buttonAtualizar;

    @FXML
    private Button buttonDeSaida;

    @FXML
    private Button buttonExcluir;

    @FXML
    private Button buttonDeRanking;

    @FXML
    private Button buttonChangelog;

    @FXML
    private Button buttonListMunicipo;

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
    void deletarRegistro(ActionEvent event) {
        municipioSelecionado.setPopulacao(null);
        municipioSelecionado.setDomicilios(null);
        municipioSelecionado.setPibTotal(null);
        municipioSelecionado.setIdh(null);
        municipioSelecionado.setRendaMedia(null);
        municipioSelecionado.setRendaNominal(null);
        municipioSelecionado.setPeaDia(null);
        municipioSelecionado.setIdhEducacao(null);
        municipioSelecionado.setIdhLongevidade(null);
        CSVUtils.escreverCSV(new ArrayList<>(municipios), outCSVPath + "\\arquivo_saida.csv");

    }

    @FXML
    void selecionarMunicipio(ActionEvent event) {
        String selectedMunicipioName = CBMunicipios.getSelectionModel().getSelectedItem();
        municipioSelecionado = municipios.stream().filter(m -> m.getNome().equals(selectedMunicipioName)).findFirst().orElse(null);
        exibirDetalhesMunicipio();
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
            // Aqui você pode implementar uma mensagem de erro ao usuário se a troca de cena falhar
        }
    }

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

    @Override
    public void initialize(URL url, ResourceBundle rb
    ) {
        loadData();
        CBMunicipios.getItems().addAll(municipios.stream().map(Municipio::getNome).collect(Collectors.toList()));
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
            txtPopulacao.setDisable(true);
            txtDomicilios.setText(municipioSelecionado.getDomicilios());
            txtDomicilios.setDisable(true);
            txtPibTotal.setText(municipioSelecionado.getPibTotal());
            txtPibTotal.setDisable(true);
            txtIdh.setText(municipioSelecionado.getIdh());
            txtIdh.setDisable(true);
            txtRendaMedia.setText(municipioSelecionado.getRendaMedia());
            txtRendaMedia.setDisable(true);
            txtRendaNominal.setText(municipioSelecionado.getRendaNominal());
            txtRendaNominal.setDisable(true);
            txtPeaDia.setText(municipioSelecionado.getPeaDia());
            txtPeaDia.setDisable(true);
            txtIdhEducacao.setText(municipioSelecionado.getIdhEducacao());
            txtIdhEducacao.setDisable(true);
            txtIdhLongevidade.setText(municipioSelecionado.getIdhLongevidade());
            txtIdhLongevidade.setDisable(true);
        }
    }
}
