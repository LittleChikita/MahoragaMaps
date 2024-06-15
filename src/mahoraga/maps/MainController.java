package mahoraga.maps;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import mahoraga.maps.entities.CSVUtils;
import mahoraga.maps.entities.Municipio;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class MainController implements Initializable {

    @FXML
    private Button buttonAtualizar;

    @FXML
    private void switchToUpdate(ActionEvent event) throws IOException {
        Parent telaUpdate = FXMLLoader.load(getClass().getResource("Atualizacao.fxml"));
        Scene scene2 = new Scene(telaUpdate);
        Stage janelaAtual = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        janelaAtual.setScene(scene2);
        janelaAtual.show();
    }
 
    @FXML
    private TableColumn<Municipio, String> municipioAreaTableColumn;

    @FXML
    private TextField municipioBusca;

    @FXML
    private TableColumn<Municipio, String> municipioCodigoTableColumn;

    @FXML
    private TableColumn<Municipio, String> municipioDomiciliosTableColumn;

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
    private TableColumn<Municipio, String> municipioPEADiaTableColumn;

    @FXML
    private TableColumn<Municipio, String> municipioPibTotalTableColumn;

    @FXML
    private TableColumn<Municipio, String> municipioPopulacaoTableColumn;

    @FXML
    private TableColumn<Municipio, String> municipioRegiaoTableColumn;

    @FXML
    private TableColumn<Municipio, String> municipioRendaMediaTableColumn;

    @FXML
    private TableColumn<Municipio, String> municipioDensidadeDemograficaTableColumn;

    @FXML
    private TableColumn<Municipio, String> municipioPibPerCapitaTableColumn;

    @FXML
    private TableColumn<Municipio, String> municipioRendaNominalTableColumn;

    @FXML
    private TableView<Municipio> municipiosTableView;

    private ObservableList<Municipio> municipioObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Municipio> lista = CSVUtils.lerCSV();

        municipioCodigoTableColumn.setCellValueFactory(new PropertyValueFactory<>("codigoIBGE"));
        municipioNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        municipioMicrorregiaoTableColumn.setCellValueFactory(new PropertyValueFactory<>("microRegiao"));
        municipioEstadoTableColumn.setCellValueFactory(new PropertyValueFactory<>("estado"));
        municipioRegiaoTableColumn.setCellValueFactory(new PropertyValueFactory<>("regiaoGeografica"));
        municipioAreaTableColumn.setCellValueFactory(new PropertyValueFactory<>("areaKm"));
        municipioPopulacaoTableColumn.setCellValueFactory(new PropertyValueFactory<>("populacao"));
        municipioDensidadeDemograficaTableColumn.setCellValueFactory(new PropertyValueFactory<>("densidadeDemografica"));
        municipioDomiciliosTableColumn.setCellValueFactory(new PropertyValueFactory<>("domicilios"));
        municipioPibTotalTableColumn.setCellValueFactory(new PropertyValueFactory<>("pibTotal"));
        municipioPibPerCapitaTableColumn.setCellValueFactory(new PropertyValueFactory<>("PibPerCapita"));
        municipioRendaMediaTableColumn.setCellValueFactory(new PropertyValueFactory<>("rendaMedia"));
        municipioRendaNominalTableColumn.setCellValueFactory(new PropertyValueFactory<>("rendaNominal"));
        municipioPEADiaTableColumn.setCellValueFactory(new PropertyValueFactory<>("peaDia"));
        municipioIDHTableColumn.setCellValueFactory(new PropertyValueFactory<>("idh"));
        municipioClassificacaoIDHTableColumn.setCellValueFactory(new PropertyValueFactory<>("classificacaoIDH"));
        municipioIDHEducacaoTableColumn.setCellValueFactory(new PropertyValueFactory<>("idhEducacao"));
        municipioClassificacaoIDHETableColumn.setCellValueFactory(new PropertyValueFactory<>("classificacaoIDHE"));
        municipioIDHLongevidadeTableColumn.setCellValueFactory(new PropertyValueFactory<>("idhLongevidade"));
        municipioClassificacaoIDHLTableColumn.setCellValueFactory(new PropertyValueFactory<>("classificacaoIDHL"));

        municipioObservableList.setAll(lista);
        municipiosTableView.setItems(municipioObservableList);

        FilteredList<Municipio> filteredData = new FilteredList<>(municipioObservableList, b -> true);

        municipioBusca.textProperty().addListener(((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Municipio -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                if (Municipio.getCodigoIBGE().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (Municipio.getNome().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (Municipio.getMicroRegiao().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (Municipio.getEstado().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (Municipio.getRegiaoGeografica().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else {
                    return false;
                }
            });
        }));

        SortedList<Municipio> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(municipiosTableView.comparatorProperty());

        municipiosTableView.setItems(sortedData);
    }
}
