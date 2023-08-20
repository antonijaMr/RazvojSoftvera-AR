package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;

public class Student {
	private SlusaPredmet slusaPred;
	private final StringProperty student_id;
	private final StringProperty ime;
	private final StringProperty prezime;
	private final StringProperty lozinka;
	private final StringProperty email;
	private final StringProperty godStudija;
	private final StringProperty statusStud;
	private final StringProperty sifUsmjerenja;
	private final StringProperty ostvareniECTS;
    private final StringProperty zavrsioReg;
	private final Button actionButton;

	public Student() {
		setSlusaPred(new SlusaPredmet());
		student_id = new SimpleStringProperty(this, "student_id");
		ime = new SimpleStringProperty(this, "ime");
		prezime = new SimpleStringProperty(this, "prezime");
		lozinka = new SimpleStringProperty(this, "lozinka");
		email = new SimpleStringProperty(this, "email");
		godStudija = new SimpleStringProperty(this, "godStudija");
		statusStud = new SimpleStringProperty(this, "statusStud");
		sifUsmjerenja = new SimpleStringProperty(this, "sifUsmjerenja");
		ostvareniECTS = new SimpleStringProperty(this, "ostvareniECTS");
		zavrsioReg=new SimpleStringProperty(this,"zavrsioReg");

		actionButton = new Button("Update");
	}

	
	public Student(SlusaPredmet slusaPred, StringProperty student_id, StringProperty ime, StringProperty prezime,
			StringProperty lozinka, StringProperty email, StringProperty godStudija, StringProperty statusStud,
			StringProperty sifUsmjerenja, StringProperty ostvareniECTS, StringProperty zavrsioReg,
			Button actionButton) {
		super();
		this.slusaPred = slusaPred;
		this.student_id = student_id;
		this.ime = ime;
		this.prezime = prezime;
		this.lozinka = lozinka;
		this.email = email;
		this.godStudija = godStudija;
		this.statusStud = statusStud;
		this.sifUsmjerenja = sifUsmjerenja;
		this.ostvareniECTS = ostvareniECTS;
		this.zavrsioReg = zavrsioReg;
		this.actionButton = actionButton;
	}


	public Button getActionButton() {
		return actionButton;
	}

	public final StringProperty idProperty() {
		return this.student_id;
	}

	public final String getId() {
		return this.idProperty().get();
	}

	public final void setId(final String id) {
		this.idProperty().set(id);
	}

	public StringProperty imeProperty() {
		return ime;
	}

	public String getIme() {
		return this.imeProperty().get();
	}

	public void setIme(final String ime) {
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

	public final StringProperty lozinkaProperty() {
		return this.lozinka;
	}

	public final String getLozinka() {
		return this.lozinkaProperty().get();
	}

	public final void setLozinka(final String lozinka) {
		this.lozinkaProperty().set(lozinka);
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

	public final StringProperty godStudijaProperty() {
		return this.godStudija;
	}

	public final String getGodStudija() {
		return this.godStudijaProperty().get();
	}

	public final void setGodStudija(final String godStudija) {
		this.godStudijaProperty().set(godStudija);
	}

	public final StringProperty statusStudProperty() {
		return this.statusStud;
	}

	public final String getStatusStud() {
		return this.statusStudProperty().get();
	}

	public final void setStatusStud(final String statusStud) {
		this.statusStudProperty().set(statusStud);
	}

	public final StringProperty sifUsmjerenjaProperty() {
		return this.sifUsmjerenja;
	}

	public final String getSifUsmjerenja() {
		return this.sifUsmjerenjaProperty().get();
	}

	public final void setSifUsmjerenja(final String sifUsmjerenja) {
		this.sifUsmjerenjaProperty().set(sifUsmjerenja);
	}

	public final StringProperty ostvareniECTSProperty() {
		return this.ostvareniECTS;
	}

	public final String getOstvareniECTS() {
		return this.ostvareniECTSProperty().get();
	}

	public final void setOstvareniECTS(final String ostvareniECTS) {
		this.ostvareniECTSProperty().set(ostvareniECTS);
	}

	public SlusaPredmet getSlusaPred() {
		return slusaPred;
	}

	public void setSlusaPred(SlusaPredmet slusaPred) {
		this.slusaPred = slusaPred;
	}

	public final StringProperty zavrsioRegProperty() {
		return this.zavrsioReg;
	}
	

	public final String getZavrsioReg() {
		return this.zavrsioRegProperty().get();
	}
	

	public final void setZavrsioReg(final String zavrsioReg) {
		this.zavrsioRegProperty().set(zavrsioReg);
	}
	
	

}
