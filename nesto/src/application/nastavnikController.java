package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.Nastavnik;
import models.Predmet;
import models.Student;
import models.SlusaPredmet;

public class nastavnikController implements Initializable {
	MySQLConnection mysql = new MySQLConnection();
	private Nastavnik currentNastavnik = new Nastavnik();
	private SceneLoader s = new SceneLoader();

	// ???
	int myIndex;
	int id;

	int predmetID;

	@FXML
	private Label nastIme;

	@FXML
	private AnchorPane side_anchorpane;
	@FXML
	private Pane inner_pane;
	@FXML
	private Button btn_predmeti;
	@FXML
	private Button btn_zahtjevi;
	@FXML
	private Button btn_logout;

	@FXML
	private TableView<Student> studTable;
	@FXML
	private TableView<Predmet> predTable;
	@FXML
	private TableColumn<Student, String> ImeC;
	@FXML
	private TableColumn<Student, String> PrezimeC;
	@FXML
	private TableColumn<Student, String> GodinaStudijaC;
	@FXML
	private TableColumn<Student, String> statusC;
	@FXML
	private TableColumn<Student, String> usmjerenjeC;
	@FXML
	private TableColumn<Student, String> bodoviC;
	@FXML
	private TableColumn<Student, String> ocjenaC;
	@FXML
	private TableColumn<Student, Button> upisC;

	@FXML
	private TableColumn<Predmet, String> sifPredC;
	@FXML
	private TableColumn<Predmet, String> nazPredC;
	@FXML
	private TableColumn<Predmet, String> pC;
	@FXML
	private TableColumn<Predmet, String> avC;
	@FXML
	private TableColumn<Predmet, String> lvC;
	@FXML
	private TableColumn<Predmet, String> ectsC;
	@FXML
	private TableColumn<Predmet, String> semestarC;
	@FXML
	private TableColumn<Predmet, Button> detaljiC;

	@FXML
	public void predmeti(ActionEvent e) throws IOException {
		s.loadPredmeti(e);
	}

	@FXML
	public void to_zahtjevi(ActionEvent e) throws IOException {
		s.loadZahtjevi(e);
	}

	@FXML
	public void to_predZahtjevi(ActionEvent e) throws IOException {
		s.loadPredZahtjevi(e);
	}

	@FXML
	public void logout(ActionEvent e) throws IOException {
		s.logout(e);
	}

	public void setCurrentNastavnik() {
		currentNastavnik = DataSingleton.getInstance().getNastavnik();
		nastIme.setText(currentNastavnik.getIme() + " " + currentNastavnik.getPrezime());
	}

	public void TablePred() {
		mysql.Connect();

		ObservableList<Predmet> predmeti = FXCollections.observableArrayList();
		try {
			mysql.pst = mysql.con.prepareStatement(
					"select predmet.sifPred,kratPred,nazivPred,uzaNaucnaOblast,satiPredavanja,satiAV,satiLV,ECTS,semestar,brojSemestara"
							+ " from predmet inner join predaje on predaje.sifPred = predmet.sifPred where sifNastavnikNosioc =?");
			mysql.pst.setString(1, Integer.toString(currentNastavnik.getSifNast()));
			ResultSet rs = mysql.pst.executeQuery();
			{
				while (rs.next()) {
					Predmet pr = new Predmet();
					pr.setSifraPred(rs.getString("sifPred"));
					pr.setKratPred(rs.getString("kratPred"));
					pr.setNazivPred(rs.getString("nazivPred"));
					pr.setUzaNaucnaOblast(rs.getString("uzaNaucnaOblast"));
					pr.setPredavanja_sati(rs.getInt("satiPredavanja"));
					pr.setLab_sati(rs.getInt("satiAV"));
					pr.setAv_sati(rs.getInt("satiLV"));
					pr.setECTS(rs.getInt("ECTS"));
					pr.setSemestar(rs.getString("semestar"));

					predmeti.add(pr);
				}

			}

			predTable.setItems(predmeti);

			//this is wird hope it works
			sifPredC.setCellValueFactory(new PropertyValueFactory<>("sifraPred"));
			nazPredC.setCellValueFactory(new PropertyValueFactory<>("nazivPred"));
			pC.setCellValueFactory(new PropertyValueFactory<>("predavanja_sati"));
			avC.setCellValueFactory(new PropertyValueFactory<>("av_sati"));
			lvC.setCellValueFactory(new PropertyValueFactory<>("lab_sati"));
			ectsC.setCellValueFactory(new PropertyValueFactory<>("ECTS"));
			semestarC.setCellValueFactory(new PropertyValueFactory<>("semestar"));

			detaljiC.setCellValueFactory(
					cellData -> new ReadOnlyObjectWrapper<Button>(cellData.getValue().getActionButton()));
			detaljiC.setCellFactory(param -> new TableCell<Predmet, Button>() {
				@Override
				protected void updateItem(Button item, boolean empty) {
					super.updateItem(item, empty);
					if (empty || item == null) {
						setGraphic(null);
					} else {
						setGraphic(item);
						item.setOnAction(event -> {
							int rowIndex = getIndex();
							Predmet selectedPredmet = getTableView().getItems().get(rowIndex);
							try {
								openDialog(selectedPredmet);
							} catch (IOException e) {
								e.printStackTrace();
							}
						});
					}
				}
			});

		} catch (SQLException e) {
			e.printStackTrace();
		}
		// ON ClICKED
		predTable.setRowFactory(tv -> {
			TableRow<Predmet> myRow = new TableRow<>();
			myRow.setOnMouseClicked(event -> {
				if (event.getClickCount() == 1 && (!myRow.isEmpty())) {
					myIndex = predTable.getSelectionModel().getSelectedIndex();

					predmetID = Integer.parseInt(String.valueOf(predTable.getItems().get(myIndex).getSifraPred()));
					TableStud();
				}
			});
			return myRow;
		});
	}

	public void TableStud() {
		mysql.Connect();

		ObservableList<Student> students = FXCollections.observableArrayList();
		try {
			mysql.pst = mysql.con.prepareStatement(
					"select student_id,ime,prezime,email,godStudija,statusStud,sifUsmjerenja,ostvareniECTS, bodovi, ocjena "
							+ "from student inner join slusaPred on idStud = student.student_id where slusaPred.sifPred = ?;");
			mysql.pst.setString(1, Integer.toString(predmetID));
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
					st.setSifUsmjerena(rs.getString("sifUsmjerenja"));
					st.setOstvareniECTS(rs.getString("ostvareniECTS"));
					st.getSlusaPredmet().setBodovi(rs.getDouble("bodovi"));
					st.getSlusaPredmet().setOcjena(rs.getInt("ocjena"));
					students.add(st);
				}

			}

			studTable.setItems(students);

			ImeC.setCellValueFactory(new PropertyValueFactory<>("ime"));
			PrezimeC.setCellValueFactory(new PropertyValueFactory<>("prezime"));
			GodinaStudijaC.setCellValueFactory(new PropertyValueFactory<>("godStudija"));
			statusC.setCellValueFactory(new PropertyValueFactory<>("status"));
			usmjerenjeC.setCellValueFactory(new PropertyValueFactory<>("sifUsmjerenja"));
			bodoviC.setCellValueFactory(new PropertyValueFactory<>("bodovi"));
			ocjenaC.setCellValueFactory(new PropertyValueFactory<>("ocjena"));

			upisC.setCellValueFactory(
					cellData -> new ReadOnlyObjectWrapper<Button>(cellData.getValue().getActionButton()));
			upisC.setCellFactory(param -> new TableCell<Student, Button>() {
				@Override
				protected void updateItem(Button item, boolean empty) {
					super.updateItem(item, empty);
					if (empty || item == null) {
						setGraphic(null);
					} else {
						setGraphic(item);
						item.setOnAction(event -> {
							int rowIndex = getIndex();
							Student selectedStudent = getTableView().getItems().get(rowIndex);
							System.out.println(selectedStudent.getEmail());
//							try {
//								openDialog(selectedZahtjev);
//							} catch (IOException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
						});
					}
				}
			});

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void openDialog(Predmet p) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		DataSingleton.getInstance().setPredmet(p);
		loader.setLocation(getClass().getResource("nastavnik_detalji.fxml"));
		Parent root = loader.load();

		Stage currentStage = (Stage) side_anchorpane.getScene().getWindow();
		Scene scene = new Scene(root);

		currentStage.setScene(scene);
		currentStage.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setCurrentNastavnik();
		mysql.Connect();
		TableStud();
		TablePred();
	}
}
