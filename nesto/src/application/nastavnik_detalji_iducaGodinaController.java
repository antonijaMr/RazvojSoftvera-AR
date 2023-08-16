package application;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.layout.HBox;

import javafx.scene.control.TextField;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.layout.AnchorPane;

import javafx.scene.control.TableView;

import javafx.scene.layout.Pane;

import javafx.scene.control.TableColumn;

public class nastavnik_detalji_iducaGodinaController {
	private SceneLoader s = new SceneLoader();
	
	@FXML
	private HBox root;
	@FXML
	private AnchorPane side_anchorpane;
	@FXML
	private Pane inner_pane;
	@FXML
	private Label nastIme;
	@FXML
	private Button btn_predmeti;
	@FXML
	private Button btn_zahtjevi;
	@FXML
	private Button btn_logout;
	@FXML
	private Label imePred;
	@FXML
	private Label novaGodina;
	@FXML
	private Label predavac;
	@FXML
	private Label nosioc;
	@FXML
	private Label nosiocEmail;
	@FXML
	private Label sifPred;
	@FXML
	private Label kratPred;
	@FXML
	private Label uzaNaucnaOblast;
	@FXML
	private Label p;
	@FXML
	private Label av;
	@FXML
	private Label lv;
	@FXML
	private Label ects;
	@FXML
	private TableView preduslovT;
	@FXML
	private TableColumn imePredmetaC;
	@FXML
	private TableColumn pC;
	@FXML
	private TableColumn avC;
	@FXML
	private TableColumn lvC;
	@FXML
	private TableColumn ectsC;
	@FXML
	private TableColumn nosiocC;
	@FXML
	private TableColumn semestarC;
	@FXML
	private TextField search_tf;
	@FXML
	private TableView studentiT;
	@FXML
	private TableColumn imeSC;
	@FXML
	private TableColumn prezimeSC;
	@FXML
	private TableColumn godinaC;
	@FXML
	private TableColumn statusC;
	@FXML
	private TableColumn obnovaC;
	@FXML
	private TableColumn smjerC;

	@FXML
	public void predmeti(ActionEvent e) {
		s.loadPredmeti(e);
	}

	@FXML
	public void to_zahtjevi(ActionEvent e) {
		s.loadZahtjevi(e);
	}

	@FXML
	public void to_predZahtjevi(ActionEvent e) {
		s.loadPredZahtjevi(e);
	}

	@FXML
	public void logout(ActionEvent e) {
		s.logout(e);
	}
}
