package application;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

public class preduslovController implements Initializable {
	private MySQLConnection mysql = new MySQLConnection();
	private SceneLoader s = new SceneLoader();

	String query = null;
	ResultSet res = null;

	@FXML
	private TextField preduslov_tf;
	@FXML
	private Button potvrdi_preduslov;

	private Stage stage;
	private String sifraPredmet;

	public void setSifraPredmet(String sifraPred) {
		sifraPredmet = sifraPred;
	}

	public void setStage(Stage s) {
		stage = s;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		mysql.Connect();

	}

	@FXML
	private void potvrdi(ActionEvent e) {
		if (!empty()) {

			query = "INSERT INTO preduslov (sifPred, sifPreduslov) VALUES ( ?, ?)";

			try {
				mysql.pst = mysql.con.prepareStatement(query);
				mysql.pst.setString(1, sifraPredmet);
				mysql.pst.setString(2, preduslov_tf.getText());
				int rowsAffected = mysql.pst.executeUpdate();

				if (rowsAffected > 0) {
					System.out.println("Row inserted successfully.");
				} else {
					System.out.println("Failed to insert row.");
				}

			} catch (SQLException e1) {

				e1.printStackTrace();
			}
			stage.close();
		} else

		{
			s.alert("Ispunite sva polja!");
		}
	}

	private boolean empty() {
		return preduslov_tf.getText().isEmpty();
	}

}
