package mahoraga.maps;

import java.io.IOException;
import java.net.URL;
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

public class AtualizacaoController implements Initializable {

    private Municipio municipioSelecionado;
    private ObservableList<Municipio> municipios;

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
    private ComboBox<String> CBMunicipios;

    @FXML
    private void switchToMain(ActionEvent event) throws IOException {
        Parent telaMain = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene scene = new Scene(telaMain);
        Stage janelaAtual = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        janelaAtual.setScene(scene);
        janelaAtual.show();

    }

    @FXML
    private void selecionarMunicipio(ActionEvent event) {
        String selectedMunicipioName = CBMunicipios.getSelectionModel().getSelectedItem();
        municipioSelecionado = municipios.stream().filter(m -> m.getNome().equals(selectedMunicipioName)).findFirst().orElse(null);
        exibirDetalhesMunicipio();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Municipio> lista = CSVUtils.lerCSV();
        municipios = FXCollections.observableArrayList(lista);
        CBMunicipios.getItems().addAll(municipios.stream().map(Municipio::getNome).collect(Collectors.toList()));
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
