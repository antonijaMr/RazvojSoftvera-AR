package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.Pane;
import models.Nastavnik;
import models.Predmet;
import models.Preduslov;
import models.Student;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
					p.getPredmet().setPredavanja_sati(rs.getInt("satiPredavanja"));
					p.getPredmet().setAv_sati(rs.getInt("satiAV"));
					p.getPredmet().setLab_sati(rs.getInt("satiLV"));
					p.getPredmet().setECTS(rs.getInt("ECTS"));
					p.getPredmet().setSemestar(rs.getString("semestar"));
					p.getPredmet().getNastavnikN().setIme(rs.getString("nastavnikN.ime"));
					p.getPredmet().getNastavnikN().setPrezime(rs.getString("nastavnikN.prezime"));
					p.getPredmet().getNastavnikP().setIme(rs.getString("nastavnikP.ime"));
					p.getPredmet().getNastavnikP().setPrezime(rs.getString("nastavnikP.prezime"));

					preduslovi.add(p);
				}

			}

			preduslovT.setItems(preduslovi);

			imePredmetaC.setCellValueFactory(new PropertyValueFactory<>("nazivPred"));
			pC.setCellValueFactory(new PropertyValueFactory<>("predavanja_sati"));
			avC.setCellValueFactory(new PropertyValueFactory<>("av_sati"));
			lvC.setCellValueFactory(new PropertyValueFactory<>("lab_sati"));
			ectsC.setCellValueFactory(new PropertyValueFactory<>("ECTS"));
			semestarC.setCellValueFactory(new PropertyValueFactory<>("semestar"));
			nosiocC.setCellValueFactory(new PropertyValueFactory<>("nastavnikN.getIme()" +" " + "nastavnikN.getPrezime()"));
			predavateljC.setCellValueFactory(new PropertyValueFactory<>("nastavnikP.getIme()" + "  " + "nastavnikP.getPrezime()"));
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
					st.setId(rs.getInt("student_id"));
					st.setIme(rs.getString("ime"));
					st.setPrezime(rs.getString("prezime"));
					st.setEmail(rs.getString("email"));
					st.setGodinaStudija(rs.getString("godStudija"));
					st.setStatus(rs.getString("statusStud"));
					st.setSifUsmjerena(rs.getString("imeUsmjerenja"));
					st.setOstvareniECTS(rs.getString("ostvareniECTS"));
					st.getSlusaPredmet().setBodovi(rs.getDouble("bodovi"));
					st.getSlusaPredmet().setOcjena(rs.getInt("ocjena"));
					students.add(st);
				}

			}

			studentiT.setItems(students);

			imeSC.setCellValueFactory(new PropertyValueFactory<>("ime"));
			prezimeSC.setCellValueFactory(new PropertyValueFactory<>("prezime"));
			godinaC.setCellValueFactory(new PropertyValueFactory<>("godinaStudija"));
			statusC.setCellValueFactory(new PropertyValueFactory<>("status"));
			usmjerenjeC.setCellValueFactory(new PropertyValueFactory<>("sifUsmjerena"));
			bodoviC.setCellValueFactory(new PropertyValueFactory<>("slusaPredmet.bodovi"));
			ocjenaC.setCellValueFactory(new PropertyValueFactory<>("slusaPredmet.ocjena"));

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

