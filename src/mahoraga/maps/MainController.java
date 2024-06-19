package mahoraga.maps;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import mahoraga.maps.entities.CSVUtils;
import mahoraga.maps.entities.Municipio;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import static mahoraga.maps.entities.Municipio.converter;

public class MainController implements Initializable {

    @FXML
    private Label labelPiorPibPerCapita;

    @FXML
    private Label labelPibPerCapita;

    @FXML
    private Button buttonAtualizar;

    @FXML
    private Button buttonDelete;

    @FXML
    private Button buttonDeSaida;

    @FXML
    private Button buttonDeRanking;

    @FXML
    private Button buttonChangelog;

    @FXML
    private TableView<Municipio> municipiosTableView;

    @FXML
    private TableColumn<Municipio, String> municipioCodigoTableColumn;

    @FXML
    private TableColumn<Municipio, Double> municipioDomiciliosTableColumn;

    @FXML
    private TableColumn<Municipio, String> municipioEstadoTableColumn;

    @FXML
    private TableColumn<Municipio, String> municipioClassificacaoIDHETableColumn;

    @FXML
    private TableColumn<Municipio, String> municipioClassificacaoIDHLTableColumn;

    @FXML
    private TableColumn<Municipio, String> municipioClassificacaoIDHTableColumn;

    @FXML
    private TableColumn<Municipio, String> municipioIDHEducacaoTableColumn;

    @FXML
    private TableColumn<Municipio, String> municipioIDHLongevidadeTableColumn;

    @FXML
    private TableColumn<Municipio, String> municipioIDHTableColumn;

    @FXML
    private TableColumn<Municipio, String> municipioMicrorregiaoTableColumn;

    @FXML
    private TableColumn<Municipio, String> municipioNameTableColumn;

    @FXML
    private TableColumn<Municipio, Double> municipioPEADiaTableColumn;

    @FXML
    private TableColumn<Municipio, Double> municipioPibTotalTableColumn;

    @FXML
    private TableColumn<Municipio, Double> municipioPopulacaoTableColumn;

    @FXML
    private TableColumn<Municipio, String> municipioRegiaoTableColumn;

    @FXML
    private TableColumn<Municipio, String> municipioRendaMediaTableColumn;

    @FXML
    private TableColumn<Municipio, Double> municipioDensidadeDemograficaTableColumn;

    @FXML
    private TableColumn<Municipio, Double> municipioPibPerCapitaTableColumn;

    @FXML
    private TableColumn<Municipio, Double> municipioRendaNominalTableColumn;

    @FXML
    private TableColumn<Municipio, Double> municipioAreaTableColumn;

    @FXML
    private TextField municipioBusca;

    private ObservableList<Municipio> municipioObservableList = FXCollections.observableArrayList();

    private final String inCSVPath = "C:\\Users\\Chê Chikita\\Desktop\\inCSV";
    private final String outCSVPath = "C:\\Users\\Chê Chikita\\Desktop\\outCSV";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTableColumns();
        loadData();
        setupSearchFilter();
        updateHighestPibPerCapita();
        updateLowestPibPerCapita();

    }

    //SoDeusSabe
    private Locale localeBR = new Locale("pt", "BR");
    private NumberFormat numeroBR = NumberFormat.getNumberInstance(localeBR);
    private StringConverter<Number> converter = new NumberStringConverter(numeroBR);

    private Callback<TableColumn<Municipio, Double>, TableCell<Municipio, Double>> criarCellFactory() {
        return column -> new TableCell<Municipio, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item == 0.0) {
                    setText(null);
                } else {
                    setText(converter.toString(item));
                }
            }
        };
    }

    private NumberFormat criarNumberFormat() {
        NumberFormat numeroBR = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
        numeroBR.setMinimumFractionDigits(2);
        numeroBR.setMaximumFractionDigits(2);
        return numeroBR;
    }

    private String formatarNumeroBR(double valor) {
        Locale localeBR = new Locale("pt", "BR");
        NumberFormat numeroBR = NumberFormat.getNumberInstance(localeBR);
        numeroBR.setMinimumFractionDigits(2);
        numeroBR.setMaximumFractionDigits(2);
        return numeroBR.format(valor);
    }

    private void initTableColumns() {

        municipioCodigoTableColumn.setCellValueFactory(new PropertyValueFactory<>("codigoIBGE"));
        municipioNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        municipioMicrorregiaoTableColumn.setCellValueFactory(new PropertyValueFactory<>("microRegiao"));
        municipioEstadoTableColumn.setCellValueFactory(new PropertyValueFactory<>("estado"));
        municipioRegiaoTableColumn.setCellValueFactory(new PropertyValueFactory<>("regiaoGeografica"));
        municipioAreaTableColumn.setCellValueFactory(cellData -> {
            String areaString = cellData.getValue().getAreaKm();
            double value = converter(areaString);
            return value != 0.0 ? new SimpleDoubleProperty(value).asObject() : null;
        });
        municipioPopulacaoTableColumn.setCellValueFactory(cellData -> {
            String populacaoString = cellData.getValue().getPopulacao();
            double value = converter(populacaoString);
            return value != 0.0 ? new SimpleDoubleProperty(value).asObject() : null;

        });
        municipioPopulacaoTableColumn.setCellFactory(criarCellFactory());
        municipioDensidadeDemograficaTableColumn.setCellValueFactory(cellData -> {
            String areaDensidade = cellData.getValue().getDensidadeDemografica();
            double value = converter(areaDensidade);
            return value != 0.0 ? new SimpleDoubleProperty(value).asObject() : null;
        });
        municipioDensidadeDemograficaTableColumn.setCellFactory(criarCellFactory());
        municipioDomiciliosTableColumn.setCellValueFactory(cellData -> {
            String areaDomicilios = cellData.getValue().getDomicilios();
            double value = converter(areaDomicilios);
            return value != 0.0 ? new SimpleDoubleProperty(value).asObject() : null;
        });
        municipioDomiciliosTableColumn.setCellFactory(criarCellFactory());
        municipioPibTotalTableColumn.setCellValueFactory(cellData -> {
            String areaPibTotal = cellData.getValue().getPibTotal();
            double value = converter(areaPibTotal);
            return value != 0.0 ? new SimpleDoubleProperty(value).asObject() : null;
        });
        municipioPibTotalTableColumn.setCellFactory(criarCellFactory());
        municipioPibPerCapitaTableColumn.setCellValueFactory(new PropertyValueFactory<>("PibPerCapita"));
        municipioPibPerCapitaTableColumn.setCellFactory(criarCellFactory());
        municipioRendaMediaTableColumn.setCellValueFactory(cellData -> {
            String areaRendaMendia = cellData.getValue().getRendaMedia();
            double value = converter(areaRendaMendia);
            if (value != 0.0) {
                String formattedValue = criarNumberFormat().format(value);
                return new SimpleStringProperty(formattedValue);
            } else {
                return null;
            }
        });
        municipioRendaNominalTableColumn.setCellValueFactory(cellData -> {
            String areaRendaNominal = cellData.getValue().getRendaNominal();
            double value = converter(areaRendaNominal);
            return value != 0.0 ? new SimpleDoubleProperty(value).asObject() : null;
        });
        municipioRendaNominalTableColumn.setCellFactory(criarCellFactory());
        municipioPEADiaTableColumn.setCellValueFactory(cellData -> {
            String areaPeadia = cellData.getValue().getPeaDia();
            double value = converter(areaPeadia);
            return value != 0.0 ? new SimpleDoubleProperty(value).asObject() : null;
        });
        municipioPEADiaTableColumn.setCellFactory(criarCellFactory());
        municipioIDHTableColumn.setCellValueFactory(cellData -> {
            String areaidh = cellData.getValue().getIdh();
            double value = converter(areaidh);
            if (value != 0.0) {
                String formattedValue = criarNumberFormat().format(value);
                return new SimpleStringProperty(formattedValue);
            } else {
                return null;
            }
        });
        municipioClassificacaoIDHTableColumn.setCellValueFactory(new PropertyValueFactory<>("classificacaoIDH"));
        municipioIDHEducacaoTableColumn.setCellValueFactory(cellData -> {
            String areamidhE = cellData.getValue().getIdhEducacao();
            double value = converter(areamidhE);
            if (value != 0.0) {
                String formattedValue = criarNumberFormat().format(value);
                return new SimpleStringProperty(formattedValue);
            } else {
                return null;
            }
        });
        municipioClassificacaoIDHETableColumn.setCellValueFactory(new PropertyValueFactory<>("classificacaoIDHE"));
        municipioIDHLongevidadeTableColumn.setCellValueFactory(cellData -> {
            String areaIDHL = cellData.getValue().getIdhLongevidade();
            double value = converter(areaIDHL);
            if (value != 0.0) {
                String formattedValue = criarNumberFormat().format(value);
                return new SimpleStringProperty(formattedValue);
            } else {
                return null;
            }
        });
        municipioClassificacaoIDHLTableColumn.setCellValueFactory(new PropertyValueFactory<>("classificacaoIDHL"));

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
        municipioObservableList.clear();
        municipioObservableList.addAll(lista);
        municipiosTableView.setItems(municipioObservableList);
    }

    private void setupSearchFilter() {
        FilteredList<Municipio> filteredData = new FilteredList<>(municipioObservableList, b -> true);

        municipioBusca.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(municipio -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                return municipioMatchesFilter(municipio, lowerCaseFilter);
            });
        });

        SortedList<Municipio> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(municipiosTableView.comparatorProperty());
        municipiosTableView.setItems(sortedData);
    }

    private boolean municipioMatchesFilter(Municipio municipio, String filter) {
        return municipio.getCodigoIBGE().toLowerCase().contains(filter)
                || municipio.getNome().toLowerCase().contains(filter)
                || municipio.getMicroRegiao().toLowerCase().contains(filter)
                || municipio.getEstado().toLowerCase().contains(filter)
                || municipio.getRegiaoGeografica().toLowerCase().contains(filter);
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
    private void switchToDelete(ActionEvent event) {
        try {
            Parent telaUpdate = FXMLLoader.load(getClass().getResource("Excluir.fxml"));
            Scene scene3 = new Scene(telaUpdate);
            Stage janelaAtual = (Stage) buttonDelete.getScene().getWindow();
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

    // daki pra baixo só deus sabe
    private void updateHighestPibPerCapita() {
        if (!municipioObservableList.isEmpty()) {
            Municipio highestPibPerCapitaMunicipio = municipioObservableList.stream()
                    .max(Comparator.comparing(Municipio::getPibPerCapita))
                    .orElse(null);

            if (highestPibPerCapitaMunicipio != null) {
                labelPibPerCapita.setText(formatarNumeroBR(highestPibPerCapitaMunicipio.getPibPerCapita()));
                labelMunicipio.setText(String.format("%s", highestPibPerCapitaMunicipio.getNome()));
            }
        }
    }

    private void updateLowestPibPerCapita() {
        if (!municipioObservableList.isEmpty()) {
            Municipio lowestPibPerCapitaMunicipio = municipioObservableList.stream()
                    .min(Comparator.comparing(Municipio::getPibPerCapita))
                    .orElse(null);

            if (lowestPibPerCapitaMunicipio != null) {
                labelPiorPibPerCapita.setText(formatarNumeroBR(lowestPibPerCapitaMunicipio.getPibPerCapita()));
                labelMunicipioPior.setText(String.format("%s", lowestPibPerCapitaMunicipio.getNome()));
            }
        }
    }

    @FXML
    private void handleUpdatePibPerCapita(ActionEvent event) {
        updateHighestPibPerCapita();
    }

    @FXML
    private void updateLoestPibPerCapita(ActionEvent event) {
        updateLowestPibPerCapita();
    }

    @FXML
    private Label labelMunicipio;

    @FXML
    private Label labelMunicipioPior;

    
}
