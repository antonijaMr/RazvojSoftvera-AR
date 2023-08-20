package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Student;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

import javafx.scene.control.PasswordField;

import javafx.scene.control.ChoiceBox;

public class administrator_updateStudentController  implements Initializable {
	private MySQLConnection mysql = new MySQLConnection();
	private SceneLoader s = new SceneLoader();
	
	private Student stud;
	
	@FXML
	private TextField ime_tf;
	@FXML
	private TextField prezime_tf;
	@FXML
	private TextField email_tf;
	@FXML
	private PasswordField lozinka;
	@FXML
	private ChoiceBox<String> godinaChoice;
	@FXML
	private TextField ects;
	@FXML
	private ChoiceBox<String> smjerChoice;
	@FXML
	private ChoiceBox<String> statusChoice;
	@FXML
	private Button cancelButton;
	private String[] godina = {"1","2","3","4"};
	private String[] status = { "Redovan", "Vanredan", "Apsolvent", "Imatrikulant" };
	private String[] smjer = { "AR", "RI", "EEMS", "ESKE", "TK", "Ostalo" };

	
	public void setData(Student s) {
		stud = s;
		ime_tf.setText(s.getIme());
		prezime_tf.setText(s.getPrezime());
		email_tf.setText(s.getEmail());
		ects.setText(s.getOstvareniECTS());
		smjerChoice.getItems().addAll(smjer);
		smjerChoice.setValue(s.getSifUsmjerenja());
		statusChoice.getItems().addAll(status);
		statusChoice.setValue(s.getStatusStud());
		godinaChoice.getItems().addAll(godina);
		godinaChoice.setValue(s.getGodStudija());
	}

	@FXML
	public void delete(ActionEvent event) {
		try {
			mysql.pst = mysql.con.prepareStatement("delete from student where brojIndeksa= ?");
			mysql.pst.setString(1, stud.getId());
			int rowsAffected = mysql.pst.executeUpdate();
			if (rowsAffected > 0) {
				s.alert("Student je izbrisan!");
			} else {
				s.alertEror("Doslo je do greske.");
			}
		} catch (SQLException e) {
			s.alertEror("Studnet se ne moze izbrisati");
			e.printStackTrace();
		}
	}

	@FXML
	public void update(ActionEvent event) {
	}

	@FXML
	public void cancel(ActionEvent event) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		mysql.Connect();
		
	}
}
