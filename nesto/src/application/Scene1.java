package application;
import java.util.ArrayList;
import javafx.scene.control.Label;
import java.util.Optional;

import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.Predmet2;
import models.Student2;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class Scene1 implements Initializable {
	 private static String email;
	 
	 public void setEmail(String e) {
		 email=e;
		
	 }
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML private HBox PredmetiHBox = new HBox();
	@FXML private HBox RegistracijaHBox = new HBox(); 
	@FXML private HBox ZahtjeviHBox = new HBox(); 
	
	 //ArrayList<String> nizPredmet2a = new ArrayList<>();
	 //ArrayList<String> nizLjetni = new ArrayList<>();
	 //ArrayList<String> nizZimski = new ArrayList<>();
	 
	 ObservableList<String> Predmeti = FXCollections.observableArrayList();
	 ObservableList<String> Zimski = FXCollections.observableArrayList();
	 ObservableList<String> Ljetni = FXCollections.observableArrayList();
	 ObservableList<String> Trenutni = FXCollections.observableArrayList();
	 ObservableList<String> ListaZahtjeva = FXCollections.observableArrayList();
	 
	 @FXML private ListView<String> RegistrovaniPredmetiLista = new ListView<>(Trenutni);
	 @FXML private ListView<String> PredmetiTrenutni = new ListView<>(Trenutni);
	 @FXML private ListView<String> PredmetiLista = new ListView<>(Predmeti);
	 @FXML private ListView<String> PredmetiZimski = new ListView<>(Zimski);
	 @FXML private ListView<String> PredmetiLjetni = new ListView<>(Ljetni);
	 @FXML private ListView<String> ZahtjeviLista = new ListView<>(ListaZahtjeva);
	 @FXML private ComboBox<String> VrstaZahtjevaLista = new ComboBox<>();
	 @FXML private ComboBox<String> PrviPredmet = new ComboBox<>();
	 @FXML private ComboBox<String> DrugiPredmet = new ComboBox<>();
	 @FXML private ComboBox<String> NemaPreduslove = new ComboBox<>();
	 
	 @FXML private TextArea ZahtjevPoruka = new TextArea();
	 
	 @FXML private Label RegistracijaMenu;
	 @FXML private Label PredmetiMenu;
	 @FXML private Label ZahtjeviMenu;
	 @FXML public  Label ImePrezimeLabel;
	 @FXML public  Label InfoNaziv;
	 @FXML public  Label InfoSifra;
	 @FXML public  Label InfoNastavnik;
	 @FXML public  Label InfoSatiP;
	 @FXML public  Label InfoSatiAV;
	 @FXML public  Label InfoSatiLV;
	 @FXML public  Label InfoECTS;
	 @FXML public  Label InfoPreduslov;
	 @FXML public  Label InfoOcjena;
	 @FXML public  Label StatusStudenta;
	 
	 @FXML private Button PotvrdiButton;
	 @FXML private Button ZahtjevButton;
	 
	 
	 
	
	 
	Student2 stud=new Student2();
	private Connection getConnection() throws Exception {
	String url = "jdbc:mysql://localhost:3306/projekat"; //
    String user = "root";
    String password = "";
	
    return DriverManager.getConnection(url, user, password);
	}
	
	public ArrayList<Predmet2> getSubjects() {
		ArrayList<Predmet2> pList = new ArrayList<Predmet2>();
        try {
        
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	Connection connection = getConnection();
            Statement statement = connection.createStatement();
              
//           String query2 = "SELECT * FROM Predmet";
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Predmet");

            while (resultSet.next()) {
            	String nazivpred = resultSet.getString("nazivpred");
            	String sifraPred = resultSet.getString("sifpred");
            	String usmjerenje = resultSet.getString("uzaNaucnaOblast");
            	int predavanja_sati = resultSet.getInt("satiPredavanja");
            	int lab_sati = resultSet.getInt("satiLV");
            	int av_sati = resultSet.getInt("satiAV");
            	int ECTS = resultSet.getInt("ECTS");
            	String kratPred = resultSet.getString("kratpred");
            	String semestar = resultSet.getString("semestar");
            	
            	Predmet2 pred = new Predmet2(nazivpred,sifraPred,usmjerenje,predavanja_sati,lab_sati,av_sati,ECTS,semestar,kratPred);
                pList.add(pred);}
                
            

        } catch (Exception e) {
            e.printStackTrace();
        }
        return pList;
    }
	
    public  void getStudent() {
		
        try {  
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	Connection connection = getConnection();
        	String query="SELECT * FROM Student WHERE email=?";
            PreparedStatement statement = connection.prepareStatement(query);
            System.out.println(email);
            statement.setString(1,email) ;
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
            	System.out.println("tu sam");
            	stud.setBrojIndeksa(resultSet.getInt("brojIndeksa"));
            	stud.setIme(resultSet.getString("ime"));
            	stud.setPrezime(resultSet.getString("prezime"));
                stud.setEmail(resultSet.getString("email"));
            	stud.setGodStudija(resultSet.getInt("godStudija"));
            	stud.setStatusStud(resultSet.getString("statusStud"));
                stud.setSifUsmjerenja(resultSet.getString("sifUsmjerenja"));
            	stud.setOstvareniECTS(resultSet.getInt("ostvareniECTS"));
            	stud.setZavrsioReg(resultSet.getBoolean("zavrsioReg"));
            	
            	
            	
                 }
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        
    }
	
	private boolean obnavlja(int indeks, String pred) {
		boolean potvrda = false;
		try {  
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	Connection connection = getConnection();
            Statement statement = connection.createStatement();
              
            ResultSet resultSet = 
            statement.executeQuery("SELECT obnova FROM slusaPred WHERE idStud = "+indeks+" "
            		+ "AND sifPred= (SELECT sifpred FROM Predmet WHERE nazivPred = '"+pred+"')"
            				+ " AND godina = YEAR(current_date())");
            while (resultSet.next()) {
            	
            	potvrda = resultSet.getBoolean("obnova");
                 }
        } catch (Exception e) {
            e.printStackTrace();
        } return potvrda;
	}
	public boolean ImaPreduslove(String naziv) {
		 
		int potvrda = 0;
		try {  
	    	Class.forName("com.mysql.cj.jdbc.Driver");
	    	Connection connection = getConnection();
	        Statement statement = connection.createStatement();
	          
	        ResultSet resultSet = 
	        		statement.executeQuery(
	        			    "SELECT COUNT(*) " +
	        			    "FROM preduslov p " +
	        			    "LEFT JOIN slusaPred sp ON p.sifPreduslov = sp.sifPred " +
	        			    "WHERE p.sifPred = '" + getPredmet(naziv).getSifraPred() + "' " +
	        			    "      AND sp.idStud = '" + stud.getBrojIndeksa() + "' " +
	        			    "      AND (sp.sifPred IS NULL OR sp.ocjena < 6);"
	        			);
	        while (resultSet.next()) {
	        	
	        	potvrda = resultSet.getInt(1);
	             }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		return false;
	 }
	private boolean ZakljucanaRegistracija() {
		int potvrda = 0;
	try {  
    	Class.forName("com.mysql.cj.jdbc.Driver");
    	Connection connection = getConnection();
        Statement statement = connection.createStatement();
          
        ResultSet resultSet = 
        statement.executeQuery("SELECT COUNT(*) FROM periodRegistracije WHERE akademskaGodina = YEAR(CURRENT_DATE) AND\r\n"
        		+ "        		datumPocetka <= CURRENT_DATE() AND\r\n"
        		+ "        		datumZavrsetka > CURRENT_DATE();");
        while (resultSet.next()) {
        	
        	potvrda = resultSet.getInt(1);
             }
    } catch (Exception e) {
        e.printStackTrace();
    } if(potvrda == 0)
    	return true;
    else return false;
}
	private void insertQuery(String upit) {
		try {  
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	Connection connection = getConnection();
            Statement statement = connection.createStatement();
              
            String insertQuery = upit;
            int rowsAffected = statement.executeUpdate(insertQuery);
            	          
        } catch (Exception e) {
            e.printStackTrace();
        } 
	}
	
	private void RegisterSubject(Integer indeks, String pred) {
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        Connection connection = getConnection();
	        Statement statement = connection.createStatement();

	        String insertQuery = "INSERT INTO slusaPred (idStud, sifPred, godina, obnova, bodovi, ocjena) " +
	                            "VALUES (" + indeks + ", " +
	                            "(SELECT sifPred FROM Predmet WHERE nazivPred = '" + pred + "'), " +
	                            "YEAR(CURRENT_DATE()), false, 0.00, 5)";

	        int rowsAffected = statement.executeUpdate(insertQuery);
	        System.out.println("Registrovan je Predmet: "+pred+" " + "za ID:"+indeks.toString());

	        statement.close();
	        connection.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	private void UnregisterSubject(Integer indeks, String pred) {
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        Connection connection = getConnection();
	        Statement statement = connection.createStatement();

	        String deleteQuery = "DELETE FROM slusaPred WHERE idStud = " + indeks +
	                            " AND sifPred = (SELECT sifPred FROM Predmet WHERE nazivPred = '" + pred + "')" +
	                            " AND godina = YEAR(CURRENT_DATE())";

	        int rowsAffected = statement.executeUpdate(deleteQuery);
	        System.out.println("Obrisan je Predmet: "+pred+" " + "za ID:"+indeks.toString());

	        statement.close();
	        connection.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	ArrayList<String> getLjetniPredmeti(Student2 stud) {
	ArrayList<String> pList = new ArrayList<String>();
		try {  
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	Connection connection = getConnection();
            Statement statement = connection.createStatement();
              
            ResultSet resultSet = statement.executeQuery("SELECT nazivPred from Predmet JOIN"
            		+ " slusaPred on slusaPred.sifPred = Predmet.sifPred "
            		+ "WHERE idStud = "+stud.getBrojIndeksa()+" AND godina = YEAR(current_date())"
            		+ "AND semestar = 'ljetni';");
            while (resultSet.next()) {
            	pList.add(resultSet.getString("nazivPred"));        	         	        	
                 }
        } catch (Exception e) {
            e.printStackTrace();
        }
	    return pList;
	}
	private ArrayList<String> getPreduslov(Predmet2 pred) {
		ArrayList<String> preduslovi = new ArrayList<>();
		try {  
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	Connection connection = getConnection();
            Statement statement = connection.createStatement();
              
            ResultSet resultSet = statement.executeQuery("SELECT sifPreduslov FROM preduslov WHERE sifPred = '"+pred.getSifraPred()+"'");
            while (resultSet.next()) {
            	
            	String preduslov = resultSet.getString("sifPreduslov");
            	preduslovi.add(preduslov);
                 }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return preduslovi;
	}
	
	private ArrayList<String> getPredavaci(Predmet2 pred) {
		ArrayList<String> predavaci = new ArrayList<>();
		try {  
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	Connection connection = getConnection();
            Statement statement = connection.createStatement();
              
            ResultSet resultSet = statement.executeQuery("SELECT * FROM nastavnik JOIN predaje ON predaje.sifNastavnik = nastavnik.sifNast WHERE sifPred = '"+pred.getSifraPred()+"'");
            while (resultSet.next()) {	
            	String predavac = resultSet.getString("ime");
            	predavac = predavac +" "+ resultSet.getString("prezime");
            	predavaci.add(predavac);
                 }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return predavaci;
	}
	
	private ArrayList<String> getZahtjeviZaSlusanje(String naziv) {
		ArrayList<String> zahtjevi = new ArrayList<>();
		try {  
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	Connection connection = getConnection();
            Statement statement = connection.createStatement();
              
            ResultSet resultSet = statement.executeQuery("SELECT sifPred FROM zahtjevZaSlusanje "
            		+ "WHERE sifPred = '"+getPredmet(naziv).getSifraPred()+"' AND idStud='"+stud.getBrojIndeksa()+"'");
            while (resultSet.next()) {
            	
            	String zahtjev = resultSet.getString("sifPred");
            	zahtjevi.add(zahtjev);
                 }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return zahtjevi;
	}
	private Integer getBodoviOdprosleGodine(String naziv) {
		Integer bodovi = 0;
		try {  
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	Connection connection = getConnection();
            Statement statement = connection.createStatement();
              
            ResultSet resultSet = statement.executeQuery("SELECT bodovi FROM slusaPred WHERE sifPred='"+getPredmet(naziv).getSifraPred()+"'"
            		+ " AND idStud = '"+stud.getBrojIndeksa()+"' AND godina = YEAR(CURRENT_DATE())-1");
            while (resultSet.next()) {	
            	
            	bodovi = resultSet.getInt(1);
                 }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return bodovi;
	}
	private String getOdgovorni(Predmet2 pred) {
		String odgovorni = new String();
		try {  
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	Connection connection = getConnection();
            Statement statement = connection.createStatement();
              
            ResultSet resultSet = statement.executeQuery("SELECT sifNastavnik FROM predaje "
            		+ " WHERE sifPred = '"+pred.getSifraPred()+"' AND "
            				+ "nosioc = true");
            while (resultSet.next()) {	
            	System.out.println(resultSet.getString("sifNastavnik")); 
            	odgovorni = resultSet.getString("sifNastavnik");
                 }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return odgovorni;
	}
	private String getOcjena(Predmet2 pred) {
	    Integer ocjena = 5;
		try {  
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	Connection connection = getConnection();
            Statement statement = connection.createStatement();
              
            ResultSet resultSet = statement.executeQuery("SELECT ocjena FROM slusaPred WHERE sifPred = '"+pred.getSifraPred()+"' AND godina = YEAR(CURRENT_DATE()) AND idStud="+stud.getBrojIndeksa());
           if(resultSet.next())
            	ocjena = resultSet.getInt("ocjena");
          	 
        } catch (Exception e) {
            e.printStackTrace();
        }
		return ocjena.toString();
	}
	Predmet2 getPredmet(String naziv) {
		Predmet2 pred = new Predmet2();
		try {  
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	Connection connection = getConnection();
            Statement statement = connection.createStatement();
              
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Predmet WHERE nazivPred = '"+naziv+"'");
            while (resultSet.next()) {
            	String nazivpred = resultSet.getString("nazivpred");
            	String sifraPred = resultSet.getString("sifpred");
            	String usmjerenje = resultSet.getString("uzaNaucnaOblast");
            	Integer predavanja_sati = resultSet.getInt("satiPredavanja");
            	Integer lab_sati = resultSet.getInt("satiLV");
            	Integer av_sati = resultSet.getInt("satiAV");
            	Integer ECTS = resultSet.getInt("ECTS");
            	String kratPred = resultSet.getString("kratpred");
            	String semestar = resultSet.getString("semestar");
            	
            	pred = new Predmet2(nazivpred,sifraPred,usmjerenje,predavanja_sati,lab_sati,av_sati,ECTS,semestar,kratPred);
                 }
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return pred;
	}
	
	ArrayList<String> getZimskiPredmeti(Student2 stud) {
		ArrayList<String> pList = new ArrayList<String>();
		try {  
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	Connection connection = getConnection();
            Statement statement = connection.createStatement();
              
            ResultSet resultSet = statement.executeQuery("SELECT nazivPred from Predmet JOIN"
            		+ " slusaPred on slusaPred.sifPred = Predmet.sifPred "
            		+ "WHERE idStud = "+stud.getBrojIndeksa()+" AND godina = YEAR(current_date())"
            		+ "AND semestar = 'zimski';");
            while (resultSet.next()) {
            	pList.add(resultSet.getString("nazivPred"));        	         	        	
                 }
        } catch (Exception e) {
            e.printStackTrace();
        }
	    return pList;
	}
	

	
	public void ZahtjeviScene(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("Zahtjevi.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);	
		stage.show();
		
	}
	
	public void PotvrdiZahtjevButton(ActionEvent event) throws IOException {
		 Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		 alert.setTitle("Potvrda registracije");
		 alert.setHeaderText("Jeste li sigurni da zelite zavrsiti registraciju?");
		 alert.setContentText("Ukoliko naknadno budete htjeli modificirati registrovane Predmete morat cete slati zahtjev za izmjenu registrovanih Predmet2a prodekanu!");
		 Optional<ButtonType> result = alert.showAndWait();
		 if (result.isPresent() && result.get() == ButtonType.OK) {
			 PredmetiLista.setDisable(true);
			 PredmetiZimski.setDisable(true);
			 PredmetiLjetni.setDisable(true);
			 PredmetiLista.getSelectionModel().clearSelection();
			 PredmetiZimski.getSelectionModel().clearSelection();
			 PredmetiLjetni.getSelectionModel().clearSelection();
			 PotvrdiButton.setVisible(false);
			 PotvrdiButton.setDisable(true);
			 ZahtjevButton.setVisible(true);
		 }
	 }
	
	 void ZakljucajRegistraciju(){
		 PredmetiLista.setDisable(true);
		 PredmetiZimski.setDisable(true);
		 PredmetiLjetni.setDisable(true);
		 PredmetiLista.getSelectionModel().clearSelection();
		 PredmetiZimski.getSelectionModel().clearSelection();
		 PredmetiLjetni.getSelectionModel().clearSelection();
		 if(PotvrdiButton != null)PotvrdiButton.setDisable(true);
		 //RegistracijaButton.setDisable(true);
		// ZahtjevButton.setVisible(true);
	 }
	 
	 
	 
	 	 public void PotvrdiButton(ActionEvent event) throws IOException {
		 Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		 alert.setTitle("Potvrda registracije");
		 alert.setHeaderText("Jeste li sigurni da zelite zavrsiti registraciju?");
		 alert.setContentText("Ukoliko naknadno budete htjeli modificirati registrovane Predmet2e morat cete slati zahtjev za izmjenu registrovanih Predmeta prodekanu!");
		 Optional<ButtonType> result = alert.showAndWait();
		 if (result.isPresent() && result.get() == ButtonType.OK) {
			ZakljucajRegistraciju();
			stud.setZavrsioReg(true);
			insertQuery("UPDATE Student SET zavrsioReg = true WHERE brojIndeksa = "+stud.getBrojIndeksa());
		 }
	 }
	
	 public boolean JeBrucos() {
		 if(stud.getGodStudija()==1 && stud.getOstvareniECTS() == 0)
		 {
			 stud.setStatusStud("prvi put");
				insertQuery("UPDATE Student SET statusStud = 'prvi put' WHERE brojIndeksa = "+stud.getBrojIndeksa()+"");
				return true;
		 }
		 else
		 return false;
	 }
	 public void setStatusStudenta() {
		if(stud.getOstvareniECTS()>=(stud.getGodStudija()*60-12)) {
			stud.setGodStudija(stud.getGodStudija()+1);
			insertQuery("UPDATE Student SET godStudija="+stud.getGodStudija()+" WHERE brojIndeksa = "+stud.getBrojIndeksa()+"");
		}
		if(!ZakljucanaRegistracija() && stud.getOstvareniECTS()<stud.getGodStudija()*60-12 && !JeBrucos()) {
			stud.setStatusStud("obnova");
			insertQuery("UPDATE Student SET statusStud = 'obnova' WHERE brojIndeksa = "+stud.getBrojIndeksa()+"");
		}
		else if(!ZakljucanaRegistracija() && (stud.getOstvareniECTS()>=stud.getGodStudija()*60-12))
		{
			stud.setStatusStud("redovan");
			insertQuery("UPDATE Student SET statusStud = 'redovan' WHERE brojIndeksa = "+stud.getBrojIndeksa()+"");
		}
	 }
	 
	 public void ListaMogucihZahtjeva() {
         String selectedValue = VrstaZahtjevaLista.getValue();
		     if(selectedValue.equals("Za slusanje predmeta"))
		     {
		     NemaPreduslove.setVisible(true);	    
		     PrviPredmet.setVisible(false);
		     DrugiPredmet.setVisible(false);
		     ZahtjevPoruka.setDisable(false); }
		     if(selectedValue.equals("Za prenos bodova"))
		     {NemaPreduslove.setVisible(false);
		     PrviPredmet.setVisible(true);
		     DrugiPredmet.setVisible(false);
		     ZahtjevPoruka.setDisable(true);}
		     if(selectedValue.equals("Za zamjenu predmeta"))
		     {NemaPreduslove.setVisible(false);
		     PrviPredmet.setVisible(true);
		     DrugiPredmet.setVisible(true);
		     ZahtjevPoruka.setDisable(false);}
		};
	 public void PrviPredmetAction()
	 {
		 String selectedValue = PrviPredmet.getValue();
		 System.out.println(selectedValue);
	 }
	 public void DrugiPredmetAction()
	 {
		 String selectedValue = DrugiPredmet.getValue();
		 System.out.println(selectedValue);
	 }
	 public void NemaPreduslovaPredmetAction()
	 {
		 String selectedValue = NemaPreduslove.getValue();
		 System.out.println(selectedValue);
	 }
	 public void PosaljiZahtjevAction(ActionEvent event) throws IOException {
		 
		 String vrstaZahtjeva = "";
		 if(VrstaZahtjevaLista.getValue().equals("Za zamjenu predmeta")) {vrstaZahtjeva = "zahtjevZaPromjenu";}
		 else if(VrstaZahtjevaLista.getValue().equals("Za prenos bodova")) {vrstaZahtjeva = "zahtjevZaPrenos";}
		 else if(VrstaZahtjevaLista.getValue().equals("Za slusanje predmeta")) {vrstaZahtjeva = "zahtjevZaSlusanje";}
		
        if(vrstaZahtjeva.equals("zahtjevZaPrenos"))
        	insertQuery("INSERT INTO " + vrstaZahtjeva +
        	        "    (idStud,sifNast,brojBodova,sifPred) VALUES (" +
        	        "'" + stud.getBrojIndeksa() + "'," +
        	        "'" + getOdgovorni(getPredmet(PrviPredmet.getValue())) + "'," +
        	        "'" + getBodoviOdprosleGodine(PrviPredmet.getValue()).toString() + "'," +
        	        "'" + getPredmet(PrviPredmet.getValue()).getSifraPred() + "')");
                
	 
        else if(vrstaZahtjeva.equals("zahtjevZaSlusanje"))
     	insertQuery("INSERT INTO " + vrstaZahtjeva +
     	        "    (idStud,sifNast,sifPred,poruka) VALUES (" +
     	        "'" + stud.getBrojIndeksa() + "'," +
     	        "'" + getOdgovorni(getPredmet(NemaPreduslove.getValue())) + "'," +
     	        "'" + getPredmet(NemaPreduslove.getValue()).getSifraPred() + "'," +  
     	        "'" + ZahtjevPoruka.getText() + "')"); 
        else if(vrstaZahtjeva.equals("zahtjevZaPromjenu"))
         	insertQuery("INSERT INTO " + vrstaZahtjeva +
         	        "    (idStud,sifPred1,sifPred2,poruka) VALUES (" +
         	        "'" + stud.getBrojIndeksa() + "'," +
         	        "'" + getPredmet(PrviPredmet.getValue()).getSifraPred() + "'," +
         	        "'" + getPredmet(DrugiPredmet.getValue()).getSifraPred() + "'," +
         	        "'" + ZahtjevPoruka.getText() + "')");   
	   
	 }
	 
	 
	 @Override
	 public void initialize(URL location, ResourceBundle resources) {
		 getStudent();
		 getSubjects();
		 setStatusStudenta();
		 System.out.println(stud.isZavrsioReg());
		// insertQuery("UPDATE Student SET zavrsioReg = false WHERE ime = 'Eldar'");
		 if(stud.isZavrsioReg() || ZakljucanaRegistracija() ||(stud.getOstvareniECTS()== 0 && stud.getGodStudija()== 1))
		 { 
			 ZakljucajRegistraciju();
		 }
		 System.out.println("student: "+stud.getEmail());
		ImePrezimeLabel.setText(stud.getIme() +' '+ stud.getPrezime());
		StatusStudenta.setText("Godina "+stud.getGodStudija()+", "+stud.getStatusStud()+", "+stud.getSifUsmjerenja());
		PredmetiLjetni.getItems().addAll(getLjetniPredmeti(stud));	
		PredmetiZimski.getItems().addAll(getZimskiPredmeti(stud));
		RegistrovaniPredmetiLista.getItems().addAll(getLjetniPredmeti(stud));
		RegistrovaniPredmetiLista.getItems().addAll(getZimskiPredmeti(stud));
		if(VrstaZahtjevaLista.getItems().isEmpty())
	        VrstaZahtjevaLista.getItems().addAll("Za prenos bodova","Za slusanje predmeta","Za zamjenu predmeta");
			RegistrovaniPredmetiLista.getItems().addAll(getLjetniPredmeti(stud));
			RegistrovaniPredmetiLista.getItems().addAll(getZimskiPredmeti(stud));
		
		
		try{Ljetni.addAll(PredmetiLjetni.getItems());
		    Zimski.addAll(PredmetiZimski.getItems());
		    VrstaZahtjevaLista.getItems().addAll("Za prenos Bodova","Za slusanje Predmeta","Za zamjenu Predmeta");
		    }
		catch(Exception e) {System.out.println();};
		System.out.println(Zimski);
		System.out.println(Ljetni);
	    
	     
	     //////////////////////////////////////////////////////////////////////7
		 PredmetiLista.getItems().addAll(getSubjects().stream()
	                .map(Predmet2::getNazivpred)  // Map each Subject to its name
	                .collect(Collectors.toList()));
		 
		 /////////////////////////////////////////////////////////////////////////////
		 if(PrviPredmet.getItems().isEmpty())
			{
		    PrviPredmet.getItems().addAll(Trenutni);
			}
			
			if(DrugiPredmet.getItems().isEmpty()) {
				ArrayList<String> sviPredmeti = new ArrayList<>();
				sviPredmeti.addAll(PredmetiLista.getItems());
				for(int i = 0; i < Trenutni.size(); i++)
					for(int n = 0; n < sviPredmeti.size();n++)
					if(Trenutni.get(i).equals(sviPredmeti.get(n)))
						sviPredmeti.remove(n);
				sviPredmeti.add(PredmetiLista.getItems().get(0));
				for(int i = 0; i<sviPredmeti.size(); i++) {
				if(ImaPreduslove(sviPredmeti.get(i)))
					DrugiPredmet.getItems().add(sviPredmeti.get(i));
				}
			}
	        if(NemaPreduslove.getItems().isEmpty())
	        	for(int i = 0; i < PredmetiLista.getItems().size(); i++)
	        	{
	        		if(!ImaPreduslove(PredmetiLista.getItems().get(i)))
	        		{
	        			NemaPreduslove.getItems().add(PredmetiLista.getItems().get(i));
	        		}
	        	}
			
		 
		 
		 PredmetiMenu.setOnMouseClicked(event -> {
			    if (event.getClickCount() == 1) { // Check for single-click
			        try {
			            root = FXMLLoader.load(getClass().getResource("Predmeti.fxml"));
			            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			            scene = new Scene(root);
			            stage.setScene(scene);
			            stage.show();
			            
			        } catch (IOException e) {
			            e.printStackTrace();
			        }			        
			      
			    }
			});
		
		 RegistracijaMenu.setOnMouseClicked(event -> {
			    if (event.getClickCount() == 1) { // Check for single-click
			        try {
			            root = FXMLLoader.load(getClass().getResource("Student.fxml"));
			            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			            scene = new Scene(root);
			            stage.setScene(scene);
			            stage.show();
			        } catch (IOException e) {
			            e.printStackTrace();
			        }			
			    }
			});
		 ZahtjeviMenu.setOnMouseClicked(event -> {
			    if (event.getClickCount() == 1) { // Check for single-click
			        try {
			            root = FXMLLoader.load(getClass().getResource("Zahtjevi.fxml"));
			            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			            scene = new Scene(root);
			            stage.setScene(scene);
			            stage.show();
			            
			            
			        } catch (IOException e) {
			            e.printStackTrace();
			        }			
			    }
			});
	    
		 
		 PredmetiLista.setOnMouseClicked(event -> {
			    if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
			        // Double-click logic
			        String current = PredmetiLista.getSelectionModel().getSelectedItem();
                    
			        int pos = getSubjects().stream()
			                .map(Predmet2::getNazivpred)
			                .collect(Collectors.toList())
			                .indexOf(current);

			        Boolean isLjetni = getSubjects().get(pos).getSemestar().equals("ljetnji") || getSubjects().get(pos).getSemestar().equals("ljetni");
                   // if(imaPreduslov())
			        if (isLjetni) {
			            if (!Ljetni.contains(current) && Ljetni.size() < 5) {
			             	Ljetni.add(current);
			                PredmetiLjetni.getItems().add(current);
			                RegisterSubject(stud.getBrojIndeksa(),current);
			            }
			        } else {
			            if (!Zimski.contains(current) && Zimski.size() < 5) {
			                Zimski.add(current);
			                PredmetiZimski.getItems().add(current);
			                RegisterSubject(stud.getBrojIndeksa(),current);
			            }
			        }
			    }
			});
		 
		 RegistrovaniPredmetiLista.setOnMouseClicked(event -> {
			    if (event.getClickCount() == 1) { // Check for single-click
			        String Selected = RegistrovaniPredmetiLista.getSelectionModel().getSelectedItem();
			      Predmet2 pred = (getPredmet(Selected));
			     
			      /*@FXML public  Label InfroNaziv;
			 	 @FXML public  Label InfoSifra;
			 	 @FXML public  Label InfoNastavnik;
			 	 @FXML public  Label InfoSatiP;
			 	 @FXML public  Label InfoSatiAV;
			 	 @FXML public  Label InfoSatiLV;
			 	 @FXML public  Label InfoECTS;
			 	 @FXML public  Label InfoPreduslov;
			 	 @FXML public  Label InfoOcjena;
			   */
			      InfoSifra.setText(pred.getSifraPred());
			      InfoNastavnik.setText(getPredavaci(pred).toString());
			      InfoSatiP.setText(pred.getPredavanja_sati().toString());
			      InfoSatiAV.setText(pred.getAv_sati().toString());
			      InfoSatiLV.setText(pred.getLab_sati().toString());
			      InfoECTS.setText(pred.getECTS().toString());		     
			      InfoPreduslov.setText(getPreduslov(pred).toString());
			      String ocj = new String(getOcjena(pred));
			      if(ocj.equals("5")) ocj = "NP";
			      InfoOcjena.setText(ocj);
			      
			    }
			});
		 
		
		 
		 PredmetiZimski.setOnMouseClicked(event -> {
			    if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
			        // Double-click logic
			        String selectedItem = PredmetiZimski.getSelectionModel().getSelectedItem();
			        if (selectedItem != null && !Zimski.isEmpty() && obnavlja(stud.getBrojIndeksa(),selectedItem)==false) {
			            Platform.runLater(() -> {
			                try {
			                    // Remove the selected item from both the list and the ListView
			                    Zimski.remove(selectedItem);
			                    PredmetiZimski.getItems().remove(selectedItem);
			                    UnregisterSubject(stud.getBrojIndeksa(),selectedItem);
			                    
			                } catch (Exception e) {
			                    System.out.println("Error!");
			                }
			            });
			        }
			    }
			});
		 
		 
		 PredmetiLjetni.setOnMouseClicked(event -> {
			    if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
			        // Double-click logic
			        String selectedItem = PredmetiLjetni.getSelectionModel().getSelectedItem();
			        if (selectedItem != null && !Ljetni.isEmpty() && obnavlja(stud.getBrojIndeksa(),selectedItem)==false) {
			            Platform.runLater(() -> {
			                try {
			                    Ljetni.remove(selectedItem);
			                    PredmetiLjetni.getItems().remove(selectedItem);
			                    UnregisterSubject(stud.getBrojIndeksa(),selectedItem);
			               
			                } catch (Exception e) {
			                    System.out.println("Error!");
			                }
			            });
			        }
			    }
			}
		 
				 );
		 
	
	 }

	

	
	
}
