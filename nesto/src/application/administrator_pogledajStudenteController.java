package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;

import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;

import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.Pane;
import models.Nastavnik;
import models.Student;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class administrator_pogledajStudenteController implements Initializable {
	MySQLConnection mysql = new MySQLConnection();
	private SceneLoader s = new SceneLoader();

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
	private TableColumn<Student, Button> actionC;

	@FXML
	public void studenti(MouseEvent event) {
		s.loadAdminStudent(event);
	}

	@FXML
	public void nastavnici(MouseEvent event) {
		s.loadAdminNastavnik(event);
	}

	@FXML
	public void predmeti(MouseEvent event) {
		s.loadAdminPredmet(event);
	}

	@FXML
	public void prodekan(MouseEvent event) {
		s.loadAdminProdekan(event);
	}

	@FXML
	public void logout(MouseEvent event) {
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

		ObservableList<Student> studenti = FXCollections.observableArrayList();
		try {
			mysql.pst = mysql.con.prepareStatement("Select * from student;");
			ResultSet rs = mysql.pst.executeQuery();
			{
				while (rs.next()) {
					Student s = new Student();
					s.setId(rs.getString("brojIndeksa"));
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
			indexC.setCellValueFactory(f -> f.getValue().idProperty());
			imeC.setCellValueFactory(f -> f.getValue().imeProperty());
			prezimeC.setCellValueFactory(f -> f.getValue().prezimeProperty());
			emailC.setCellValueFactory(f -> f.getValue().emailProperty());
			godinaC.setCellValueFactory(f -> f.getValue().godStudijaProperty());
			statusC.setCellValueFactory(f -> f.getValue().statusStudProperty());
			smjerC.setCellValueFactory(f -> f.getValue().sifUsmjerenjaProperty());
			ectsC.setCellValueFactory(f -> f.getValue().ostvareniECTSProperty());

			actionC.setCellValueFactory(
					cellData -> new ReadOnlyObjectWrapper<Button>(cellData.getValue().getActionButton()));
			actionC.setCellFactory(param -> new TableCell<Student, Button>() {
				@Override
				protected void updateItem(Button item, boolean empty) {
					super.updateItem(item, empty);
					if (empty || item == null) {
						setGraphic(null);
					} else {
						setGraphic(item);
						item.setOnAction(event -> {
							int rowIndex = getIndex();
							Student selected = getTableView().getItems().get(rowIndex);
							openDialog(selected, event);
						});
					}
				}
			});

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void openDialog(Student p, ActionEvent e) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("administrator_updateStudent.fxml"));
			DialogPane odgovoriDialog;
			odgovoriDialog = loader.load();
			System.out.println("eror");
			administrator_updateStudentController cont = loader.getController();
			System.out.println("eror");
			cont.setData(p);
			Dialog<ButtonType> dialog = new Dialog<>();
			dialog.setDialogPane(odgovoriDialog);
			dialog.setTitle("Promjena studenta");

			dialog.showAndWait();
			tableStud();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void setupSearch() {
		FilteredList<Student> filteredData = new FilteredList<>(studentiT.getItems(), p -> true);

		search_tf.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(student -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = search_tf.getText().toLowerCase();

				if (student.getIme().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (student.getPrezime().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				}
				return false;
			});
		});

		SortedList<Student> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(studentiT.comparatorProperty());

		studentiT.setItems(sortedData);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		mysql.Connect();
		tableStud();
		setupSearch();
	}
}
