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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.cell.PropertyValueFactory;
import models.*;
import javafx.util.Pair;
import javafx.scene.control.ListView;

public class ProdekanPregledPlanaController implements Initializable{
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
	private Button PregledPlanaButton;
	@FXML 
	private Button dodajUnosButton;
	@FXML
	private Button btn_prijavljeniPredmeti;
	@FXML
	private Button btn_bitniPredmeti;
	

    @FXML
    private TableView<Nastavnik_predmet> Plan_table;

    @FXML
    private TableColumn<Nastavnik_predmet, String> NastavniPredmetCol;
    @FXML
    private TableColumn<Nastavnik_predmet, String> PZimskiCol;
    @FXML
    private TableColumn<Nastavnik_predmet, String> AZimskiCol;
    @FXML
    private TableColumn<Nastavnik_predmet, String> LZimskiCol;
    @FXML
    private TableColumn<Nastavnik_predmet, String> PLjetnjiCol;
    @FXML
    private TableColumn<Nastavnik_predmet, String> ALjetnjiCol;
    @FXML
    private TableColumn<Nastavnik_predmet, String> LLjetnjiCol;
 
//    @FXML
//    private TableColumn<Nastavnik_predmet, List<Pair<String, String>>> ImeCol;
    
    @FXML
    private TableColumn<Nastavnik_predmet,List<Pair<String,String>>> NastavnikCol;
    @FXML
    private TableColumn<Nastavnik_predmet,List<Pair<String,String>>> NosiocCol;
   
    
    private Connection connection;
	
	Connection con;
	PreparedStatement pst;
	ResultSet res=null;
	
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
	
	load();
	}
	

	private void load() {
		List<Nastavnik_predmet> subjectTeacherList = new ArrayList<>();

        try {
        	String query = "SELECT predmeti.nazivPred AS subjectName, predmeti.satiPredavanja, predmeti.satiLV, predmeti.satiAV,semestar," +
                    "GROUP_CONCAT(CONCAT(nastavnik.ime,' ', nastavnik.prezime, '(', nastavnik.zvanje, ')', '/',nosioc)) AS teachers " +
                    "FROM predmeti " +
                    "INNER JOIN predaje ON predmeti.sifPred = predaje.sifPred " +
                    "INNER JOIN nastavnik ON predaje.sifNastavnik = nastavnik.sifNast WHERE godina=YEAR(NOW())" +
                    "GROUP BY predmeti.nazivPred";

            PreparedStatement preparedStatement=con.prepareStatement(query);
           // preparedStatement.setYear(1,Year.now());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
            	Nastavnik_predmet nast_pred=new Nastavnik_predmet();
                nast_pred.setNastavniPredmet(resultSet.getString("subjectName"));
                if(resultSet.getString("semestar").equals("zimski")) {
                nast_pred.setPZ(resultSet.getInt("predmeti.satiPredavanja"));
                nast_pred.setAZ(resultSet.getInt("satiAV"));
                nast_pred.setLZ(resultSet.getInt("satiLV"));
                }else {
                	nast_pred.setPLJ(resultSet.getInt("predmeti.satiPredavanja"));
                    nast_pred.setALJ(resultSet.getInt("satiAV"));
                    nast_pred.setLLJ(resultSet.getInt("satiLV"));	
                }
                String teachers = resultSet.getString("teachers");
                String[] teacherArray = teachers.split(",");

                List<Pair<String,String>> teacherList = new ArrayList<>();
                for (String teacher : teacherArray) {
                	String[] nast_nosioc=teacher.split("/");
                	
                    teacherList.add(new Pair(nast_nosioc[0],nast_nosioc[1]));
                }
                
                nast_pred.setNastavnik_nosioc(teacherList);
               subjectTeacherList.add(nast_pred);
               
              
            }
            
            ObservableList<Nastavnik_predmet> observableList = FXCollections.observableArrayList(subjectTeacherList);
            Plan_table.setItems(observableList);
            
            Plan_table.setItems(FXCollections.observableArrayList(subjectTeacherList));
            NastavniPredmetCol.setCellValueFactory(new PropertyValueFactory<>("nastavniPredmet"));
            
            PZimskiCol.setCellValueFactory(new PropertyValueFactory<>("PZ"));
            AZimskiCol.setCellValueFactory(new PropertyValueFactory<>("AZ"));
            LZimskiCol.setCellValueFactory(new PropertyValueFactory<>("LZ"));
          
            PLjetnjiCol.setCellValueFactory(new PropertyValueFactory<>("PLJ"));
            ALjetnjiCol.setCellValueFactory(new PropertyValueFactory<>("ALJ"));
            LLjetnjiCol.setCellValueFactory(new PropertyValueFactory<>("LLJ"));
            NastavnikCol.setCellValueFactory(new PropertyValueFactory<>("nastavnik_nosioc"));
            NastavnikCol.setCellFactory(column -> {
                return new TableCell<Nastavnik_predmet, List<Pair<String, String>>>() {
                    @Override
                    protected void updateItem(List<Pair<String, String>> teachers, boolean empty) {
                        super.updateItem(teachers, empty);

                        if (empty || teachers == null || teachers.isEmpty()) {
                            setGraphic(null);
                        } else {
                            ListView<String> teacherListView = new ListView<>();
                            ObservableList<String> teacherNames = FXCollections.observableArrayList();

                            for (Pair<String, String> teacher : teachers) {
                                teacherNames.add(teacher.getKey() +(teacher.getValue().equals("Da")?"(nosioc)":""));
                            }

                            teacherListView.setItems(teacherNames);
                            
                            // Set the preferred height based on the number of teachers
                            int preferredHeight = teachers.size() * 24; // Adjust the row height as needed
                            teacherListView.setPrefHeight(preferredHeight);

                            setGraphic(teacherListView);
                        }
                    }
                };
            });






      
 
         


           
         



          
        } catch (Exception e) {
            e.printStackTrace();
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
