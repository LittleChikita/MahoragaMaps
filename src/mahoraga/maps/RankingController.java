package mahoraga.maps;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
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
import javafx.scene.control.Label;
import javafx.stage.Stage;
import mahoraga.maps.entities.CSVUtils;
import mahoraga.maps.entities.Municipio;

public class RankingController implements Initializable {

    @FXML
    private Button buttonDeExcluir;

    @FXML
    private Button buttonListMunicipo;

    @FXML
    private Button buttonDeSaida;

    @FXML
    private Button buttonAtualizar;

    @FXML
    private Button buttonChangelog;

    @FXML
    private Label labelMaiorArea;

    @FXML
    private Label labelValorArea;

    @FXML
    private Label labelMaiorPopulacao;

    @FXML
    private Label labelMaiorDensidade;

    @FXML
    private Label labelMaiorPibTotal;

    @FXML
    private Label labelMaiorPibPerCapita;

    @FXML
    private Label labelMaiorIdh;

    @FXML
    private Label labelMaiorIdhEducacao;

    @FXML
    private Label labelMaiorDomicilios;

    @FXML
    private Label labelMaiorRendaNominal;

    @FXML
    private Label labelMaiorRendaMedia;

    @FXML
    private Label labelMaiorIdhLongevidade;

    @FXML
    private Label labelMaiorPeaDia;

    @FXML
    private Label labelValorPopulacao;

    @FXML
    private Label labelValorDensidade;

    @FXML
    private Label labelValorPibTotal;

    @FXML
    private Label labelValorPibPerCapita;

    @FXML
    private Label labelValorIdh;

    @FXML
    private Label labelValorIdhEducacao;

    @FXML
    private Label labelValorDomicilios;

    @FXML
    private Label labelValorRendaNominal;

    @FXML
    private Label labelValorRendaMedia;

    @FXML
    private Label labelValorIdhLongevidade;

    @FXML
    private Label labelValorPeaDia;

    //MENOR
    @FXML
    private Label labelMenorArea;

    @FXML
    private Label labelValorMenorArea;

    @FXML
    private Label labelMenorPopulacao;

    @FXML
    private Label labelValorMenorPopulacao;

    @FXML
    private Label labelMenorDensidade;

    @FXML
    private Label labelValorMenorDensidade;

    @FXML
    private Label labelMenorPibTotal;

    @FXML
    private Label labelValorMenorPibTotal;


    @FXML
    private Label labelMenorIdh;

    @FXML
    private Label labelValorMenorIdh;

    @FXML
    private Label labelMenorIdhEducacao;

    @FXML
    private Label labelValorMenorIdhEducacao;

    @FXML
    private Label labelMenorDomicilios;

    @FXML
    private Label labelValorMenorDomicilios;

    @FXML
    private Label labelMenorRendaNominal;

    @FXML
    private Label labelValorMenorRendaNominal;

    @FXML
    private Label labelMenorRendaMedia;

    @FXML
    private Label labelValorMenorRendaMedia;

    @FXML
    private Label labelMenorIdhLongevidade;

    @FXML
    private Label labelValorMenorIdhLongevidade;

    @FXML
    private Label labelMenorPeaDia;

    @FXML
    private Label labelValorMenorPeaDia;

    private ObservableList<Municipio> municipioObservableList;
    private final String inCSVPath = "C:\\Users\\Chê Chikita\\Desktop\\inCSV";
    private final String outCSVPath = "C:\\Users\\Chê Chikita\\Desktop\\outCSV";

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
    public void initialize(URL url, ResourceBundle rb) {
        loadData();
        updateLabels();
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
        municipioObservableList = FXCollections.observableArrayList(lista);
    }

    private void updateLabels() {
        if (municipioObservableList == null || municipioObservableList.isEmpty()) {
            return;
        }

        Municipio maiorArea = municipioObservableList.stream()
                .max(Comparator.comparingDouble(m -> Municipio.converter(m.getAreaKm())))
                .orElse(null);

        Municipio maiorPopulacao = municipioObservableList.stream()
                .max(Comparator.comparingDouble(m -> Municipio.converter(m.getPopulacao())))
                .orElse(null);

        Municipio maiorDensidade = municipioObservableList.stream()
                .max(Comparator.comparingDouble(m -> {
                    String densidade = m.getDensidadeDemografica();
                    return densidade != null ? Municipio.converter(densidade) : 0.0;
                }))
                .orElse(null);

        Municipio maiorPibTotal = municipioObservableList.stream()
                .max(Comparator.comparingDouble(m -> {
                    String pibTotal = m.getPibTotal();
                    return pibTotal != null ? Municipio.converter(pibTotal) : 0.0;
                }))
                .orElse(null);

        Municipio maiorIdh = municipioObservableList.stream()
                .max(Comparator.comparingDouble(m -> {
                    String idh = m.getIdh();
                    return idh != null ? Municipio.converter(idh) : 0.0;
                }))
                .orElse(null);

        Municipio maiorIdhEducacao = municipioObservableList.stream()
                .max(Comparator.comparingDouble(m -> {
                    String idhEducacao = m.getIdhEducacao();
                    return idhEducacao != null ? Municipio.converter(idhEducacao) : 0.0;
                }))
                .orElse(null);

        Municipio maiorDomicilios = municipioObservableList.stream()
                .max(Comparator.comparingDouble(m -> {
                    String domicilios = m.getDomicilios();
                    return domicilios != null ? Municipio.converter(domicilios) : 0.0;
                }))
                .orElse(null);

        Municipio maiorRendaNominal = municipioObservableList.stream()
                .max(Comparator.comparingDouble(m -> {
                    String rendaNominal = m.getRendaNominal();
                    return rendaNominal != null ? Municipio.converter(rendaNominal) : 0.0;
                }))
                .orElse(null);

        Municipio maiorRendaMedia = municipioObservableList.stream()
                .max(Comparator.comparingDouble(m -> {
                    String rendaMedia = m.getRendaMedia();
                    return rendaMedia != null ? Municipio.converter(rendaMedia) : 0.0;
                }))
                .orElse(null);

        Municipio maiorIdhLongevidade = municipioObservableList.stream()
                .max(Comparator.comparingDouble(m -> {
                    String idhLongevidade = m.getIdhLongevidade();
                    return idhLongevidade != null ? Municipio.converter(idhLongevidade) : 0.0;
                }))
                .orElse(null);

        Municipio maiorPeaDia = municipioObservableList.stream()
                .max(Comparator.comparingDouble(m -> {
                    String peaDia = m.getPeaDia();
                    return peaDia != null ? Municipio.converter(peaDia) : 0.0;
                }))
                .orElse(null);

        // Atualize as labels para refletir os dados obtidos
        if (maiorArea != null) {
            labelMaiorArea.setText(maiorArea.getNome());
            labelValorArea.setText(maiorArea.getAreaKm());
        }

        if (maiorPopulacao != null) {
            labelMaiorPopulacao.setText(maiorPopulacao.getNome());
            labelValorPopulacao.setText(maiorPopulacao.getPopulacao());
        }

        if (maiorDensidade != null) {
            labelMaiorDensidade.setText(maiorDensidade.getNome());
            labelValorDensidade.setText(maiorDensidade.getDensidadeDemografica());
        }

        if (maiorPibTotal != null) {
            labelMaiorPibTotal.setText(maiorPibTotal.getNome());
            labelValorPibTotal.setText(maiorPibTotal.getPibTotal());
        }

        if (maiorIdh != null) {
            labelMaiorIdh.setText(maiorIdh.getNome());
            labelValorIdh.setText(maiorIdh.getIdh());
        }

        if (maiorIdhEducacao != null) {
            labelMaiorIdhEducacao.setText(maiorIdhEducacao.getNome());
            labelValorIdhEducacao.setText(maiorIdhEducacao.getIdhEducacao());
        }

        if (maiorDomicilios != null) {
            labelMaiorDomicilios.setText(maiorDomicilios.getNome());
            labelValorDomicilios.setText(maiorDomicilios.getDomicilios());
        }

        if (maiorRendaNominal != null) {
            labelMaiorRendaNominal.setText(maiorRendaNominal.getNome());
            labelValorRendaNominal.setText(maiorRendaNominal.getRendaNominal());
        }

        if (maiorRendaMedia != null) {
            labelMaiorRendaMedia.setText(maiorRendaMedia.getNome());
            labelValorRendaMedia.setText(maiorRendaMedia.getRendaMedia());
        }

        if (maiorIdhLongevidade != null) {
            labelMaiorIdhLongevidade.setText(maiorIdhLongevidade.getNome());
            labelValorIdhLongevidade.setText(maiorIdhLongevidade.getIdhLongevidade());
        }

        if (maiorPeaDia != null) {
            labelMaiorPeaDia.setText(maiorPeaDia.getNome());
            labelValorPeaDia.setText(maiorPeaDia.getPeaDia());
        }

        // Menor
        Municipio menorArea = municipioObservableList.stream()
                .min(Comparator.comparingDouble(m -> Municipio.converter(m.getAreaKm())))
                .orElse(null);

        Municipio menorPopulacao = municipioObservableList.stream()
                .min(Comparator.comparingDouble(m -> Municipio.converter(m.getPopulacao())))
                .orElse(null);

        Municipio menorDensidade = municipioObservableList.stream()
                .min(Comparator.comparingDouble(m -> {
                    String densidade = m.getDensidadeDemografica();
                    return densidade != null ? Municipio.converter(densidade) : Double.MAX_VALUE;
                }))
                .orElse(null);

        Municipio menorPibTotal = municipioObservableList.stream()
                .min(Comparator.comparingDouble(m -> {
                    String pibTotal = m.getPibTotal();
                    return pibTotal != null ? Municipio.converter(pibTotal) : Double.MAX_VALUE;
                }))
                .orElse(null);

        Municipio menorIdh = municipioObservableList.stream()
                .min(Comparator.comparingDouble(m -> {
                    String idh = m.getIdh();
                    return idh != null ? Municipio.converter(idh) : Double.MAX_VALUE;
                }))
                .orElse(null);

        Municipio menorIdhEducacao = municipioObservableList.stream()
                .min(Comparator.comparingDouble(m -> {
                    String idhEducacao = m.getIdhEducacao();
                    return idhEducacao != null ? Municipio.converter(idhEducacao) : Double.MAX_VALUE;
                }))
                .orElse(null);

        Municipio menorDomicilios = municipioObservableList.stream()
                .min(Comparator.comparingDouble(m -> {
                    String domicilios = m.getDomicilios();
                    return domicilios != null ? Municipio.converter(domicilios) : Double.MAX_VALUE;
                }))
                .orElse(null);

        Municipio menorRendaNominal = municipioObservableList.stream()
                .min(Comparator.comparingDouble(m -> {
                    String rendaNominal = m.getRendaNominal();
                    return rendaNominal != null ? Municipio.converter(rendaNominal) : Double.MAX_VALUE;
                }))
                .orElse(null);

        Municipio menorRendaMedia = municipioObservableList.stream()
                .min(Comparator.comparingDouble(m -> {
                    String rendaMedia = m.getRendaMedia();
                    return rendaMedia != null ? Municipio.converter(rendaMedia) : Double.MAX_VALUE;
                }))
                .orElse(null);

        Municipio menorIdhLongevidade = municipioObservableList.stream()
                .min(Comparator.comparingDouble(m -> {
                    String idhLongevidade = m.getIdhLongevidade();
                    return idhLongevidade != null ? Municipio.converter(idhLongevidade) : Double.MAX_VALUE;
                }))
                .orElse(null);

        Municipio menorPeaDia = municipioObservableList.stream()
                .min(Comparator.comparingDouble(m -> {
                    String peaDia = m.getPeaDia();
                    return peaDia != null ? Municipio.converter(peaDia) : Double.MAX_VALUE;
                }))
                .orElse(null);

        // Atualize as labels para refletir os dados obtidos
        if (menorArea != null) {
            labelMenorArea.setText(menorArea.getNome());
            labelValorMenorArea.setText(menorArea.getAreaKm());
        }

        if (menorPopulacao != null) {
            labelMenorPopulacao.setText(menorPopulacao.getNome());
            labelValorMenorPopulacao.setText(menorPopulacao.getPopulacao());
        }

        if (menorDensidade != null) {
            labelMenorDensidade.setText(menorDensidade.getNome());
            labelValorMenorDensidade.setText(menorDensidade.getDensidadeDemografica());
        }

        if (menorPibTotal != null) {
            labelMenorPibTotal.setText(menorPibTotal.getNome());
            labelValorMenorPibTotal.setText(menorPibTotal.getPibTotal());
        }

        if (menorIdh != null) {
            labelMenorIdh.setText(menorIdh.getNome());
            labelValorMenorIdh.setText(menorIdh.getIdh());
        }

        if (menorIdhEducacao != null) {
            labelMenorIdhEducacao.setText(menorIdhEducacao.getNome());
            labelValorMenorIdhEducacao.setText(menorIdhEducacao.getIdhEducacao());
        }

        if (menorDomicilios != null) {
            labelMenorDomicilios.setText(menorDomicilios.getNome());
            labelValorMenorDomicilios.setText(menorDomicilios.getDomicilios());
        }

        if (menorRendaNominal != null) {
            labelMenorRendaNominal.setText(menorRendaNominal.getNome());
            labelValorMenorRendaNominal.setText(menorRendaNominal.getRendaNominal());
        }

        if (menorRendaMedia != null) {
            labelMenorRendaMedia.setText(menorRendaMedia.getNome());
            labelValorMenorRendaMedia.setText(menorRendaMedia.getRendaMedia());
        }

        if (menorIdhLongevidade != null) {
            labelMenorIdhLongevidade.setText(menorIdhLongevidade.getNome());
            labelValorMenorIdhLongevidade.setText(menorIdhLongevidade.getIdhLongevidade());
        }

        if (menorPeaDia != null) {
            labelMenorPeaDia.setText(menorPeaDia.getNome());
            labelValorMenorPeaDia.setText(menorPeaDia.getPeaDia());
        }

    }
}
