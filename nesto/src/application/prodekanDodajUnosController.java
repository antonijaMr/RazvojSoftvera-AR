package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import models.Predmet;

public class prodekanDodajUnosController implements Initializable {
	
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
	private Button dodajUnosButton;
	@FXML 
	private Button potvrdiUnosButton;
	@FXML 
	private Button PregledPlanaButton;
	@FXML
	private Button btn_prijavljeniPredmeti;
	@FXML
	private Button btn_bitniDatumi;
	
	@FXML
	private ComboBox<String> nastavnik_choice;
	@FXML
	private ComboBox<String> predmet_choice;
	@FXML
	private ChoiceBox<String> nosioc_choice;
	
	String query=null;
	Connection con=null;
	PreparedStatement pst=null;
	ResultSet res=null;
	
	
	private ObservableList<String> nastavnici = FXCollections.observableArrayList();
	private ObservableList<String> predmeti = FXCollections.observableArrayList();
	private String[] nosioc= {"Da","Ne"};
	
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
		ucitajNastavnike();
		ucitajPredmete();
		nosioc_choice.getItems().addAll(nosioc);
	}
	
	private void ucitajNastavnike() {
		try {
			String query = "SELECT ime,prezime,zvanje FROM nastavnik";
	        PreparedStatement preparedStatement = con.prepareStatement(query);
	        ResultSet resultSet = preparedStatement.executeQuery();

	        
	        while (resultSet.next()) {
	            String nastavnik = resultSet.getString("ime")+" "+resultSet.getString("prezime")+", "+resultSet.getString("zvanje");
	            nastavnici.add(nastavnik);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	   nastavnik_choice.getItems().addAll(nastavnici);

	}
	
	private void ucitajPredmete() {
		try {
			String query = "SELECT nazivPred FROM predmeti";
	        PreparedStatement preparedStatement = con.prepareStatement(query);
	        ResultSet resultSet = preparedStatement.executeQuery();

	        
	        while (resultSet.next()) {
	            String predmet = resultSet.getString("nazivPred");
	            predmeti.add(predmet);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	   predmet_choice.getItems().addAll(predmeti);

	}
	
	
	@FXML 
	private void potvrdiUnos(ActionEvent e) throws SQLException {
		 String[] odabraniNastavnik = (String[])nastavnik_choice.getSelectionModel().getSelectedItem().split(",");
		 String odabraniPredmet = (String)predmet_choice.getSelectionModel().getSelectedItem();
		 String odabraniNosioc = (String)nosioc_choice.getSelectionModel().getSelectedItem();
		 String[] imePrezime=odabraniNastavnik[0].split(" ");
		 query="SELECT sifNast FROM nastavnik WHERE ime=? AND prezime=?";
		 pst=con.prepareStatement(query);
		 pst.setString(1, imePrezime[0]);
		 pst.setString(2, imePrezime[1]);
		 res=pst.executeQuery();
		 int id=0;
		
		 if(res.next()) {
		 id=res.getInt("sifNast"); 
	
		 }
		 query="SELECT sifPred FROM predmeti WHERE nazivPred=?";
		 pst=con.prepareStatement(query);
		 pst.setString(1, odabraniPredmet);
		 res=pst.executeQuery();
		 res.next();
		 String sifraP=res.getString("sifPred");
		
		
		 query="INSERT INTO predaje (sifNastavnik, sifPred,godina,nosioc) VALUES ( ?,?,YEAR(NOW()),?)";
		 pst=con.prepareStatement(query);
		 pst.setInt(1, id);
		 pst.setString(2, sifraP);
		
		// pst.setString(3, Year.now());
		 boolean n=false;
		 if(odabraniNosioc.equals("Da")) {
			 n=true;
		 }
		 pst.setBoolean(3, n);
		  int rowsAffected = pst.executeUpdate();
		 
		 if (rowsAffected > 0) {
             System.out.println("Row inserted successfully.");
         } else {
             System.out.println("Failed to insert row.");
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
	 private void to_pregled_plana(ActionEvent e) throws IOException {
		 root = FXMLLoader.load(getClass().getResource("prodekan_PregledPlana.fxml"));
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
