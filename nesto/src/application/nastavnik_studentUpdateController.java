package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import models.Predmet;
import models.Student;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;

public class nastavnik_studentUpdateController implements Initializable{
	private MySQLConnection mysql = new MySQLConnection();
	
	private Predmet predmet;

	@FXML
	private Label studID;
	@FXML
	private Label ime;
	@FXML
	private Label prezime;
	@FXML
	private Label smjer;
	@FXML
	private Label sifPred;
	@FXML
	private Label nazivPred;
	@FXML
	private TextField tf_bodovi;
	@FXML
	private TextField tf_ocjena;

	public void setData(Student s) {
		studID.setText(s.getId());
		ime.setText(s.getIme());
		prezime.setText(s.getPrezime());
		smjer.setText(s.getSifUsmjerenja());
		tf_bodovi.setText(s.getSlusaPred().getBodovi());
		tf_ocjena.setText(s.getSlusaPred().getOcjena());
		sifPred.setText(predmet.getSifraPred());
		nazivPred.setText(predmet.getNazivPred());
	}
	
	public void update() {
		try {
			String query = "update slusaPred set bodovi = ?,ocjena=? "
					+ "where idStud = ? and sifPred =?;";
			mysql.pst = mysql.con.prepareStatement(query);
			mysql.pst.setString(1, tf_bodovi.getText());
			mysql.pst.setString(2, tf_ocjena.getText());
			mysql.pst.setString(3, studID.getText());
			mysql.pst.setString(4, predmet.getSifraPred());
			mysql.pst.executeUpdate();
			

		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		mysql.Connect();
		predmet = DataSingleton.getInstance().getPredmet();
		
	}
}
