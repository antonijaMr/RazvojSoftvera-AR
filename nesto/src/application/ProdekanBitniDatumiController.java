package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Nastavnik;
import models.Predmet;

public class ProdekanBitniDatumiController implements Initializable {

	private Stage stage;
	private Scene scene;
	private Parent root;
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

	
	String query=null;
	Connection con=null;
	PreparedStatement pst=null;
	ResultSet res=null;
	Predmet predmet=null;
	
	
	ObservableList<Nastavnik>List=FXCollections.observableArrayList();
	private ObservableList<Nastavnik> filteredList = FXCollections.observableArrayList();
	public  void Connect() {
		
		try {
		
			con=DriverManager.getConnection("jdbc:mysql://localhost/projekat","root","");
		}

		catch(SQLException exe) {
			exe.printStackTrace();
		}
		
	}

		
		
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {	
			Connect();	  
		
		}
	
		@FXML
		private void potvrdi(ActionEvent e) {
			LocalDate pocetak = PocetakRegistracije.getValue();
			LocalDate kraj = KrajRegistracije.getValue();
			String query = "INSERT INTO periodregistracije (akademskaGodina,datumPocetka,datumZavrsetka) VALUES (YEAR(NOW()),?,?)";
            try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
           
                preparedStatement.setDate(1, java.sql.Date.valueOf(pocetak));
                preparedStatement.setDate(2, java.sql.Date.valueOf(kraj));
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Data inserted successfully.");
                } else {
                    System.out.println("Insertion failed.");
                }
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		}
	
	
	
	
	
	 @FXML
	    private void logout(ActionEvent e) throws IOException {
	    	root = FXMLLoader.load(getClass().getResource("Arnela.fxml"));
			stage=(Stage)((Node)e.getSource()).getScene().getWindow();
			scene=new Scene(root);
			stage.setScene(scene);
			stage.show();
	    }
	 @FXML
	    private void nastavnici(ActionEvent e) throws IOException {
	    	root = FXMLLoader.load(getClass().getResource("prodekan_nastavnici.fxml"));
			stage=(Stage)((Node)e.getSource()).getScene().getWindow();
			scene=new Scene(root);
			stage.setScene(scene);
			stage.show();
	    }

	 
	 @FXML
	    private void predmeti(ActionEvent e) throws IOException {
	    	root = FXMLLoader.load(getClass().getResource("prodekan.fxml"));
			stage=(Stage)((Node)e.getSource()).getScene().getWindow();
			scene=new Scene(root);
			stage.setScene(scene);
			stage.show();
	    }
	 @FXML
	 private void to_zahtjevi(ActionEvent e) throws IOException {
	    	root = FXMLLoader.load(getClass().getResource("prodekan_zahtjevi.fxml"));
			stage=(Stage)((Node)e.getSource()).getScene().getWindow();
			scene=new Scene(root);
			stage.setScene(scene);
			stage.show();
	    }
	 @FXML
	 private void to_plan_realizacije(ActionEvent e) throws IOException {
	    	root = FXMLLoader.load(getClass().getResource("prodekan_DodajUnos.fxml"));
			stage=(Stage)((Node)e.getSource()).getScene().getWindow();
			scene=new Scene(root);
			stage.setScene(scene);
			stage.show();
	    }
	 @FXML
	 private void to_prijavljeni_predmeti(ActionEvent e) throws IOException {
	    	root = FXMLLoader.load(getClass().getResource("ProdekanPrijavljeniPredmeti.fxml"));
			stage=(Stage)((Node)e.getSource()).getScene().getWindow();
			scene=new Scene(root);
			stage.setScene(scene);
			stage.show();
	    }
	 @FXML
	 private void to_bitni_datumi(ActionEvent e) throws IOException {
	    	root = FXMLLoader.load(getClass().getResource("ProdekanBitniDatumi.fxml"));
			stage=(Stage)((Node)e.getSource()).getScene().getWindow();
			scene=new Scene(root);
			stage.setScene(scene);
			stage.show();
	    }

}
