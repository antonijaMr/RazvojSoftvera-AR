package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Nastavnik {

	private StringProperty sifNast;
	private StringProperty ime;
	private StringProperty prezime;
	private StringProperty email;
	private StringProperty zvanje;
	private StringProperty odsjek;
	private StringProperty prodekan;

	public Nastavnik() {
		sifNast = new SimpleStringProperty(this,"sifNast");
		ime = new SimpleStringProperty(this,"ime");
		prezime = new SimpleStringProperty(this,"prezime");
		email = new SimpleStringProperty(this,"email");
		zvanje = new SimpleStringProperty(this,"zvanje");
		odsjek = new SimpleStringProperty(this,"odsjek");
		prodekan = new SimpleStringProperty(this,"prodekan");
	}

	public Nastavnik(String ime, String prezime, String email, String zvanje, String odsjek) {
		super();
		
		this.imeProperty().set(ime);
		this.prezimeProperty().set(prezime);
		this.emailProperty().set(email);
		this.zvanjeProperty().set(zvanje);
		this.odsjekProperty().set(odsjek);
	}

	public final StringProperty sifNastProperty() {
		return this.sifNast;
	}
	

	public final String getSifNast() {
		return this.sifNastProperty().get();
	}
	

	public final void setSifNast(final String sifNast) {
		this.sifNastProperty().set(sifNast);
	}
	

	public final StringProperty imeProperty() {
		return this.ime;
	}
	

	public final String getIme() {
		return this.imeProperty().get();
	}
	

	public final void setIme(final String ime) {
		this.imeProperty().set(ime);
	}
	

	public final StringProperty prezimeProperty() {
		return this.prezime;
	}
	

	public final String getPrezime() {
		return this.prezimeProperty().get();
	}
	

	public final void setPrezime(final String prezime) {
		this.prezimeProperty().set(prezime);
	}
	

	public final StringProperty emailProperty() {
		return this.email;
	}
	

	public final String getEmail() {
		return this.emailProperty().get();
	}
	

	public final void setEmail(final String email) {
		this.emailProperty().set(email);
	}
	

	public final StringProperty zvanjeProperty() {
		return this.zvanje;
	}
	

	public final String getZvanje() {
		return this.zvanjeProperty().get();
	}
	

	public final void setZvanje(final String zvanje) {
		this.zvanjeProperty().set(zvanje);
	}
	

	public final StringProperty odsjekProperty() {
		return this.odsjek;
	}
	

	public final String getOdsjek() {
		return this.odsjekProperty().get();
	}
	

	public final void setOdsjek(final String odsjek) {
		this.odsjekProperty().set(odsjek);
	}
	

	public final StringProperty prodekanProperty() {
		return this.prodekan;
	}
	

	public final String getProdekan() {
		return this.prodekanProperty().get();
	}
	

	public final void setProdekan(final String prodekan) {
		this.prodekanProperty().set(prodekan);
	}
	


}
