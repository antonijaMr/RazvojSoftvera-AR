package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;

import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

import javafx.scene.layout.AnchorPane;

import javafx.scene.control.PasswordField;

import javafx.scene.layout.Pane;

public class administrator_dodajPredmetController implements Initializable {
	private MySQLConnection mysql = new MySQLConnection();
	private SceneLoader s = new SceneLoader();
	private String[] semestar = {"Ljetni","Zimski"};

	@FXML
	private HBox root;
	@FXML
	private AnchorPane side_anchorpane;
	@FXML
	private Pane inner_pane;
	@FXML
	private Button btn_logout;
	@FXML
	private TextField sifraPred_tf;
	@FXML
	private TextField kratica_tf;
	@FXML
	private TextField naziv_tf;
	@FXML
	private TextField naucnaOblast_tf;	
	@FXML
	private TextField p_tf;
	@FXML
	private TextField av_tf;
	@FXML
	private TextField lv_tf;
	@FXML
	private TextField ects_tf;
	@FXML
	private TextField semestar_tf;
	@FXML
	private ChoiceBox<String> semestarChoice;

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
	public void pogledajPredmete(ActionEvent event) {
		s.loadAdminPredmeti(event);
	}

	@FXML
	public void dodajPredmet(ActionEvent event) {
		s.loadAdminPredmet(event);
	}

	@FXML
	public void dodaj(ActionEvent event) {
		if (provjeri()) {
			dodajUBazu();
			refresh();
		} else {
			System.out.println("sifra vec postoji");
		}
	}

	private boolean provjeri() {
		try {
			mysql.pst = mysql.con.prepareStatement("select count(1) from predmet where sifPred= ?;");
			mysql.pst.setString(1, sifraPred_tf.getText());
			ResultSet rs = mysql.pst.executeQuery();
			if (rs.next()) {
				if (rs.getInt(1) == 0)
					return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	private void dodajUBazu() {
		try {
			mysql.pst = mysql.con.prepareStatement(
					"insert into predmet (sifPred,kratPred,nazivPred,uzaNaucnaOblast,satiPredavanja,satiAV,satiLV,ECTS,semestar)"
							+ " values (?,?,?,?,?,?,?,?,?);");
			mysql.pst.setString(1, sifraPred_tf.getText());
			mysql.pst.setString(2, kratica_tf.getText());
			mysql.pst.setString(3, naziv_tf.getText());
			mysql.pst.setString(4, naucnaOblast_tf.getText());
			mysql.pst.setInt(5, !p_tf.getText().isEmpty()? Integer.parseInt(p_tf.getText()):0);
			mysql.pst.setInt(6, !av_tf.getText().isEmpty()? Integer.parseInt(av_tf.getText()):0);
			mysql.pst.setInt(7, !lv_tf.getText().isEmpty()? Integer.parseInt(lv_tf.getText()):0);
			mysql.pst.setInt(8, !ects_tf.getText().isEmpty()? Integer.parseInt(ects_tf.getText()):0);
			mysql.pst.setString(9, semestarChoice.getValue());

			int rowsAffected = mysql.pst.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("Inserted successfully.");
				refresh();
			} else {
				System.out.println("Failed to insert.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void setData() {
		semestarChoice.getItems().addAll(semestar);
	}

	private void refresh() {
		sifraPred_tf.setText("");
		kratica_tf.setText("");
		naziv_tf.setText("");
		naucnaOblast_tf.setText("");
		p_tf.setText("");
		av_tf.setText("");
		lv_tf.setText("");
		ects_tf.setText("");
		
		semestarChoice.getSelectionModel().clearSelection();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		mysql.Connect();
		setData();

	}
}
