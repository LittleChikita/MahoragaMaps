package mahoraga.maps;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import mahoraga.maps.entities.CSVUtils;
import mahoraga.maps.entities.Municipio;

public class MainController implements Initializable {

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

    ObservableList<Municipio> municipioObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resource) {
        List<Municipio> lista = CSVUtils.lerCSV("C:\\Users\\Chê Chikita\\Desktop\\inCSV\\arquivo.csv");

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
    }
}
