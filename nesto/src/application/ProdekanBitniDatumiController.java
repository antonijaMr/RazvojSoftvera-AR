package application;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import models.Nastavnik;
import models.Predmet;

public class ProdekanBitniDatumiController implements Initializable {
	private MySQLConnection mysql = new MySQLConnection();
	private SceneLoader s = new SceneLoader();

	@FXML
	private Button logout;
	@FXML
	private Button btn_nastavnici;
	@FXML
	private Button btn_predmeti;
	@FXML
	private Button btn_zahtjevi;
	@FXML
	private Button btn_noviplan;
	@FXML
	private Button btn_bitniDatumi;
	@FXML
	private Button btn_prijavljeniPredmeti;

	@FXML
	private DatePicker PocetakRegistracije;
	@FXML
	private DatePicker KrajRegistracije;
	@FXML
	private Button btn_potvrdi;

	String query = null;
	ResultSet res = null;
	Predmet predmet = null;

	ObservableList<Nastavnik> List = FXCollections.observableArrayList();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		mysql.Connect();

	}
	
	private boolean empty() {
		return PocetakRegistracije.getValue() == null || KrajRegistracije.getValue() == null;
	}

	@FXML
	private void potvrdi(ActionEvent e) {
		if(!empty()) {
			try {
				
		String query="SELECT COUNT(*) FROM periodRegistracije WHERE akademskaGodina=YEAR(NOW())";	
		mysql.pst = mysql.con.prepareStatement(query);
		res=mysql.pst.executeQuery();
		res.next();
		if(res.getInt("COUNT(*)")==0) {
		LocalDate pocetak = PocetakRegistracije.getValue();
		LocalDate kraj = KrajRegistracije.getValue();
	    query = "INSERT INTO periodRegistracije (akademskaGodina,datumPocetka,datumZavrsetka) VALUES (YEAR(NOW()),?,?)";
		    mysql.pst= mysql.con.prepareStatement(query);

			mysql.pst.setDate(1, java.sql.Date.valueOf(pocetak));
			mysql.pst.setDate(2, java.sql.Date.valueOf(kraj));
	
			int rowsAffected = mysql.pst.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Data inserted successfully.");
			} else {
				System.out.println("Insertion failed.");
			}
		}else {
			s.alert("Period registracije je vec unesen za ovu akademsku godinu");
		}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		}else {
			s.alert("Ispunite sva polja!");
		}
	}

	@FXML
	private void logout(MouseEvent e) {
		s.logout(e);
	}

	@FXML
	private void nastavnici(MouseEvent e) {
		s.loadProdekan_nastavnici(e);
	}

	@FXML
	private void predmeti(MouseEvent e) {
		s.loadProdekan_to_predmeti(e);
	}

	@FXML
	private void to_zahtjevi(MouseEvent e) {
		s.loadProdekan_to_zahtjevi(e);
	}

	@FXML
	private void to_plan_realizacije(MouseEvent e) {
		s.loadProdekan_to_plan_realizacije(e);
	}

	@FXML
	private void to_prijavljeni_predmeti(MouseEvent e) {
		s.loadProdekan_to_prijavljeni_predmeti(e);
	}

	@FXML
	private void to_bitni_datumi(MouseEvent e) {
		s.loadProdekan_to_bitni_datumi(e);
	}
}
