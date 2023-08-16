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

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.layout.AnchorPane;

import javafx.scene.control.PasswordField;

import javafx.scene.control.ChoiceBox;

import javafx.scene.layout.Pane;

public class administratorController implements Initializable {
	MySQLConnection mysql = new MySQLConnection();
	private SceneLoader s = new SceneLoader();

	private String[] status = { "Redovan", "Vanredan", "Apsolvent", "Imatrikulant" };
	private String[] smjer = { "AR", "RI", "EEMS", "ESKE", "TK" };
	private String[] predmetiZaPrvu = { "FIZ1", "MM1" };

	@FXML
	private HBox root;
	@FXML
	private AnchorPane side_anchorpane;
	@FXML
	private Pane inner_pane;
	@FXML
	private Button btn_logout;
	@FXML
	private TextField ime_tf;
	@FXML
	private TextField prezime_tf;
	@FXML
	private Label email;
	@FXML
	private PasswordField lozinka;
	@FXML
	private ChoiceBox<String> statusChoice;
	@FXML
	private ChoiceBox<String> smjerChoice;

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

	@FXML
	public void dodaj(ActionEvent event) {
		if (!empty()) {
			dodajUBazu();
			dodajPredmete();
			refresh();
		} else
			s.alertEror("Ispunite sva polja.");
	}

	private void dodajPredmete() {
		try {
			int id = 0;
			mysql.pst = mysql.con.prepareStatement("select student_id from student where email = ?;");
			mysql.pst.setString(1, email.getText());
			ResultSet rs = mysql.pst.executeQuery();
			if (rs.next())
				id = rs.getInt("student_id");

			for (String e : predmetiZaPrvu) {
				mysql.pst = mysql.con.prepareStatement("insert into slusaPred(idStud,sifPred) values (?,?);");
				mysql.pst.setInt(1, id);
				mysql.pst.setString(2, e);

				int rowsAffected = mysql.pst.executeUpdate();
				if (rowsAffected != 1)
					s.alertEror("Doslo je do greske");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private int provjeri() {
		try {
			mysql.pst = mysql.con.prepareStatement("select count(1) from student where ime = ? and prezime=?;");
			mysql.pst.setString(1, ime_tf.getText());
			mysql.pst.setString(2, prezime_tf.getText());
			ResultSet rs = mysql.pst.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 999;
	}

	private void dodajUBazu() {
		try {
			mysql.pst = mysql.con.prepareStatement(
					"insert into student(ime,prezime,lozinka,email,godStudija,statusStud,sifUsmjerenja,ostvareniECTS)"
							+ " values (?,?,?,?,?,?,?,?);");
			mysql.pst.setString(1, ime_tf.getText());
			mysql.pst.setString(2, prezime_tf.getText());
			mysql.pst.setString(3, lozinka.getText());
			mysql.pst.setString(4, email.getText());
			mysql.pst.setInt(5, 1);
			mysql.pst.setString(6, statusChoice.getValue());
			mysql.pst.setString(7, smjerChoice.getValue());
			mysql.pst.setInt(8, 0);

			int rowsAffected = mysql.pst.executeUpdate();
			if (rowsAffected > 0) {
				s.alert("Student uspjesno dodan!");
			} else {
				s.alertEror("Doslo je do greske");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void changeEmail() {
		int b = provjeri();
		if (b != 999) {
			if (b == 0)
				email.setText(ime_tf.getText().toLowerCase() + "." + prezime_tf.getText().toLowerCase() + "@fet.ba");
			else
				email.setText(
						ime_tf.getText().toLowerCase() + "." + prezime_tf.getText().toLowerCase() + b + "@fet.ba");
		}
	}

	private boolean empty() {
		return ime_tf.getText().isEmpty() || prezime_tf.getText().isEmpty() || lozinka.getText().isEmpty()
				|| statusChoice.getValue() == null || smjerChoice.getValue() == null;
	}

	private class TextChangeListener implements ChangeListener<String> {
		@Override
		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			changeEmail();
		}
	}

	private void setData() {
		statusChoice.getItems().addAll(status);
		smjerChoice.getItems().addAll(smjer);
	}

	private void refresh() {
		ime_tf.setText("");
		prezime_tf.setText("");
		lozinka.setText("");
		email.setText("ime.prezime@fet.ba");
		statusChoice.getSelectionModel().clearSelection();
		smjerChoice.getSelectionModel().clearSelection();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		mysql.Connect();
		setData();

		ime_tf.textProperty().addListener(new TextChangeListener());
		prezime_tf.textProperty().addListener(new TextChangeListener());

	}
}
