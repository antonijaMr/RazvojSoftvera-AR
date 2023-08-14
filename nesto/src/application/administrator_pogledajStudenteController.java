package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.layout.HBox;

import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.Pane;
import models.Student;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class administrator_pogledajStudenteController implements Initializable {
	MySQLConnection mysql = new MySQLConnection();
	private SceneLoader s = new SceneLoader();

	@FXML
	private HBox root;
	@FXML
	private AnchorPane side_anchorpane;
	@FXML
	private Pane inner_pane;
	@FXML
	private Button btn_logout;
	@FXML
	private TextField search_tf;
	@FXML
	private TableView<Student> studentiT;
	@FXML
	private TableColumn<Student, String> indexC;
	@FXML
	private TableColumn<Student, String> imeC;
	@FXML
	private TableColumn<Student, String> prezimeC;
	@FXML
	private TableColumn<Student, String> emailC;
	@FXML
	private TableColumn<Student, String> godinaC;
	@FXML
	private TableColumn<Student, String> statusC;
	@FXML
	private TableColumn<Student, String> smjerC;
	@FXML
	private TableColumn<Student, String> ectsC;

	@FXML
	public void studenti(ActionEvent event) {
		s.loadAdminStudent(event);
	}

	@FXML
	public void nastavnici(ActionEvent event) {
		s.loadAdminNastavnik(event);
	}

	@FXML
	public void predmeti(ActionEvent event) {
		s.loadAdminPredmet(event);
	}

	@FXML
	public void prodekan(ActionEvent event) {
		s.loadAdminProdekan(event);
	}

	@FXML
	public void logout(ActionEvent event) {
		s.logout(event);
	}

	@FXML
	public void PogledajStudente(ActionEvent event) {
		s.loadAdminStudenti(event);
	}

	@FXML
	public void dodajStudenta(ActionEvent event) {
		s.loadAdminStudent(event);
	}

	public void tableStud() {
		mysql.Connect();

		ObservableList<Student> studenti= FXCollections.observableArrayList();
		try {
			mysql.pst = mysql.con.prepareStatement("Select * from student;");
			ResultSet rs = mysql.pst.executeQuery();
			{
				while (rs.next()) {
					Student s = new Student();
					s.setId(rs.getString("student_id"));
					s.setPrezime(rs.getString("prezime"));
					s.setIme(rs.getString("ime"));
					s.setEmail(rs.getString("email"));
					s.setGodStudija(rs.getString("godStudija"));
					s.setStatusStud(rs.getString("statusStud"));
					s.setSifUsmjerenja(rs.getString("sifUsmjerenja"));
					s.setOstvareniECTS(rs.getString("ostvareniECTS"));

					studenti.add(s);
				}

			}

			studentiT.setItems(studenti);
			indexC.setCellValueFactory(f->f.getValue().idProperty());
			imeC.setCellValueFactory(f->f.getValue().imeProperty());
			prezimeC.setCellValueFactory(f->f.getValue().prezimeProperty());
			emailC.setCellValueFactory(f->f.getValue().emailProperty());
			godinaC.setCellValueFactory(f->f.getValue().godStudijaProperty());
			statusC.setCellValueFactory(f->f.getValue().statusStudProperty());
			smjerC.setCellValueFactory(f->f.getValue().sifUsmjerenjaProperty());
			ectsC.setCellValueFactory(f -> f.getValue().ostvareniECTSProperty());

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		mysql.Connect();
		tableStud();
	}
}