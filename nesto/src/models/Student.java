package models;

import javafx.scene.control.Button;

public class Student {
	private SlusaPredmet slusaPredmet;
	private int id;
	private String ime;
	private String prezime;
	private String email;
	private String godinaStudija;
	private String status;
	private String sifUsmjerena; //ili ime/skracenica
	private String ostvareniECTS;	
	
	private final Button actionButton;
	
	public Student() {
		slusaPredmet = new SlusaPredmet();
		actionButton = new Button("Update");
	}
	
	
	public SlusaPredmet getSlusaPredmet() {
		return slusaPredmet;
	}
	public void setSlusaPredmet(SlusaPredmet slusaPredmet) {
		this.slusaPredmet = slusaPredmet;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGodinaStudija() {
		return godinaStudija;
	}
	public void setGodinaStudija(String godinaStudija) {
		this.godinaStudija = godinaStudija;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSifUsmjerena() {
		return sifUsmjerena;
	}
	public void setSifUsmjerena(String sifUsmjerena) {
		this.sifUsmjerena = sifUsmjerena;
	}
	public String getOstvareniECTS() {
		return ostvareniECTS;
	}
	public void setOstvareniECTS(String ostvareniECTS) {
		this.ostvareniECTS = ostvareniECTS;
	}


	public Button getActionButton() {
		return actionButton;
	}

}
