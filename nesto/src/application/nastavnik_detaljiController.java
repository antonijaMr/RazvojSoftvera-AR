package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.Nastavnik;
import models.Predmet;
import models.Preduslov;
import models.Student;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class nastavnik_detaljiController implements Initializable {
	MySQLConnection mysql = new MySQLConnection();
	private Nastavnik currentNastavnik;
	private Predmet predmet;
	private SceneLoader s = new SceneLoader();;

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
	private Label predavac;
	@FXML
	private Label predavacEmail;
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
	private TableView<Preduslov> preduslovT;
	@FXML
	private TableColumn<Preduslov, String> imePredmetaC;
	@FXML
	private TableColumn<Preduslov, String> pC;
	@FXML
	private TableColumn<Preduslov, String> avC;
	@FXML
	private TableColumn<Preduslov, String> lvC;
	@FXML
	private TableColumn<Preduslov, String> ectsC;
	@FXML
	private TableColumn<Preduslov, String> nosiocC;
	@FXML
	private TableColumn<Preduslov, String> predavateljC;
	@FXML
	private TableColumn<Preduslov, String> semestarC;

	@FXML
	private TableView<Student> studentiT;
	@FXML
	private TableColumn<Student, String> imeSC;
	@FXML
	private TableColumn<Student, String> prezimeSC;
	@FXML
	private TableColumn<Student, String> godinaC;
	@FXML
	private TableColumn<Student, String> statusC;
	@FXML
	private TableColumn<Student, String> usmjerenjeC;
	@FXML
	private TableColumn<Student, String> bodoviC;
	@FXML
	private TableColumn<Student, String> ocjenaC;
	@FXML
	private TableColumn<Student, String> detaljiC;

	public void setCurrentNastavnik() {
		currentNastavnik = DataSingleton.getInstance().getNastavnik();
		predmet = DataSingleton.getInstance().getPredmet();
		nastIme.setText(currentNastavnik.getIme() + " " + currentNastavnik.getPrezime());
	}

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

	public void tablePreduslovi() {
		ObservableList<Preduslov> preduslovi = FXCollections.observableArrayList();
		try {
			mysql.pst = mysql.con.prepareStatement(
					"select nazivPred,satiPredavanja,satiAV,satiLV,ECTS,nastavnikN.ime,nastavnikN.prezime,nastavnikP.ime,nastavnikP.prezime,semestar from preduslov\n"
							+ "inner join predmet on predmet.sifPred = preduslov.sifPreduslov\n"
							+ "left outer join predaje on predaje.sifPred = predmet.sifPred\n"
							+ "left outer join nastavnik nastavnikN on predaje.sifNastavnikNosioc = nastavnikN.nastavnik_id\n"
							+ "left outer join nastavnik nastavnikP on predaje.sifNastavnikPredaje = nastavnikP.nastavnik_id\n"
							+ "where preduslov.sifPred = ?;");
			mysql.pst.setString(1, predmet.getSifraPred());
			ResultSet rs = mysql.pst.executeQuery();
			{
				while (rs.next()) {
					Preduslov p = new Preduslov();
					p.getPredmet().setNazivPred(rs.getString("nazivPred"));
					p.getPredmet().setPredavanja_sati(rs.getString("satiPredavanja"));
					p.getPredmet().setAv_sati(rs.getString("satiAV"));
					p.getPredmet().setLab_sati(rs.getString("satiLV"));
					p.getPredmet().setECTS(rs.getString("ECTS"));
					p.getPredmet().setSemestar(rs.getString("semestar"));
					p.getNastavnikN().setIme(rs.getString("nastavnikN.ime"));
					p.getNastavnikN().setPrezime(rs.getString("nastavnikN.prezime"));
					p.getNastavnikP().setIme(rs.getString("nastavnikP.ime"));
					p.getNastavnikP().setPrezime(rs.getString("nastavnikP.prezime"));
					preduslovi.add(p);
				}

			}

			preduslovT.setItems(preduslovi);

			imePredmetaC.setCellValueFactory(f -> f.getValue().getPredmet().nazivPredProperty());
			pC.setCellValueFactory(f -> f.getValue().getPredmet().predavanja_satiProperty());
			avC.setCellValueFactory(f -> f.getValue().getPredmet().av_satiProperty());
			lvC.setCellValueFactory(f -> f.getValue().getPredmet().lab_satiProperty());
			ectsC.setCellValueFactory(f -> f.getValue().getPredmet().ECTSProperty());
			semestarC.setCellValueFactory(f -> f.getValue().getPredmet().semestarProperty());
			nosiocC.setCellValueFactory(f -> f.getValue().getNastavnikN().imeProperty());
			predavateljC.setCellValueFactory(f -> f.getValue().getNastavnikN().imeProperty());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void tableStudenti() {
		ObservableList<Student> students = FXCollections.observableArrayList();
		try {
			mysql.pst = mysql.con.prepareStatement(
					"select student_id, ime, prezime,email,godStudija,statusStud,ostvareniECTS,imeUsmjerenja,bodovi,ocjena from student inner join slusaPred on student.student_id = slusaPred.idStud\n"
							+ "inner join usmjerenje on student.sifUsmjerenja = usmjerenje.sifUsmjerenja where sifPred = ?;");
			mysql.pst.setString(1, predmet.getSifraPred());
			ResultSet rs = mysql.pst.executeQuery();
			{
				while (rs.next()) {
					Student st = new Student();
					st.setId(rs.getString("student_id"));
					st.setIme(rs.getString("ime"));
					st.setPrezime(rs.getString("prezime"));
					st.setEmail(rs.getString("email"));
					st.setGodStudija(rs.getString("godStudija"));
					st.setStatusStud(rs.getString("statusStud"));
					st.setSifUsmjerenja(rs.getString("imeUsmjerenja"));
					st.setOstvareniECTS(rs.getString("ostvareniECTS"));
					st.slusaPred.setBodovi(rs.getString("bodovi"));
					st.slusaPred.setOcjena(rs.getString("ocjena"));
					students.add(st);
				}

			}

			studentiT.setItems(students);

			imeSC.setCellValueFactory(f -> f.getValue().imeProperty());
			prezimeSC.setCellValueFactory(f -> f.getValue().prezimeProperty());
			godinaC.setCellValueFactory(f -> f.getValue().godStudijaProperty());
			statusC.setCellValueFactory(f -> f.getValue().statusStudProperty());
			usmjerenjeC.setCellValueFactory(f -> f.getValue().sifUsmjerenjaProperty());
			bodoviC.setCellValueFactory(f -> f.getValue().slusaPred.bodoviProperty());
			ocjenaC.setCellValueFactory(f -> f.getValue().slusaPred.ocjenaProperty());

//			upisC.setCellValueFactory(
//					cellData -> new ReadOnlyObjectWrapper<Button>(cellData.getValue().getActionButton()));
//			upisC.setCellFactory(param -> new TableCell<Student, Button>() {
//				@Override
//				protected void updateItem(Button item, boolean empty) {
//					super.updateItem(item, empty);
//					if (empty || item == null) {
//						setGraphic(null);
//					} else {
//						setGraphic(item);
//						item.setOnAction(event -> {
//							int rowIndex = getIndex();
//							Student selectedStudent= getTableView().getItems().get(rowIndex);
//							System.out.println(selectedStudent.getEmail());
////							try {
////								openDialog(selectedZahtjev);
////							} catch (IOException e) {
////								// TODO Auto-generated catch block
////								e.printStackTrace();
////							}
//						});
//					}
//				}
//			});

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void setData() {
		imePred.setText(predmet.getNazivPred());
		sifPred.setText(predmet.getSifraPred());
		kratPred.setText(predmet.getKratPred());
		uzaNaucnaOblast.setText(predmet.getUzaNaucnaOblast());
		p.setText("P: " + predmet.getPredavanja_sati());
		av.setText("AV: " + predmet.getAv_sati());
		lv.setText("LV: " + predmet.getLab_sati());
		ects.setText("ECTS: " + predmet.getECTS());
		setProfesors();
	}

	public void setProfesors() {
		mysql.Connect();
		try {
			mysql.pst = mysql.con.prepareStatement(
					"select nastavnikN.ime, nastavnikN.prezime, nastavnikN.email, nastavnikP.ime,nastavnikP.prezime, nastavnikP.email from predaje\n"
							+ "inner join nastavnik nastavnikN on nastavnikN.nastavnik_id = predaje.sifNastavnikNosioc \n"
							+ "inner join nastavnik nastavnikP on nastavnikP.nastavnik_id = predaje.sifNastavnikPredaje\n"
							+ "where sifPred = ?;");
			mysql.pst.setString(1, predmet.getSifraPred());
			ResultSet rs = mysql.pst.executeQuery();
			if (rs.next()) {
				predavac.setText(
						"Predavac: " + rs.getString("nastavnikP.ime") + " " + rs.getString("nastavnikP.prezime"));
				predavacEmail.setText("Email: " + rs.getString("nastavnikP.email"));
				nosioc.setText("Nosioc: " + rs.getString("nastavnikN.ime") + " " + rs.getString("nastavnikN.prezime"));
				nosiocEmail.setText("Email: " + rs.getString("nastavnikN.email"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setCurrentNastavnik();
		mysql.Connect();
		setData();
		tablePreduslovi();
		tableStudenti();
	}
}