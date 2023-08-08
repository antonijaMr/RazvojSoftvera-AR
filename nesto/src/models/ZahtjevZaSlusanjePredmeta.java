package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;

public class ZahtjevZaSlusanjePredmeta {
	private Student stud;
	private Predmet pred;
	private StringProperty sifNast;
	private StringProperty odobreno;
	private StringProperty poruka;
	private StringProperty odgovor;
	
	private final Button actionButton;

	public ZahtjevZaSlusanjePredmeta() {
		stud = new Student();
		pred = new Predmet();
		sifNast = new SimpleStringProperty(this, "sifNast");
		odobreno = new SimpleStringProperty(this, "odobreno");
		poruka = new SimpleStringProperty(this, "poruka");
		odgovor = new SimpleStringProperty(this,"odgovor");
		actionButton = new Button("Odgovori");
	}

	public final StringProperty sifNastProperty() {
		return this.sifNast;
	}

	public final String getSifNast() {
		return this.sifNastProperty().get();
	}

	public Student getStud() {
		return stud;
	}

	public void setStud(Student stud) {
		this.stud = stud;
	}

	public Predmet getPred() {
		return pred;
	}

	public void setPred(Predmet pred) {
		this.pred = pred;
	}

	public final void setSifNast(final String sifNast) {
		this.sifNastProperty().set(sifNast);
	}

	public final StringProperty odobrenoProperty() {
		return this.odobreno;
	}

	public final String getOdobreno() {
		return this.odobrenoProperty().get();
	}

	public final void setOdobreno(final String odobreno) {
		this.odobrenoProperty().set(odobreno);
	}

	public final StringProperty porukaProperty() {
		return this.poruka;
	}

	public final String getPoruka() {
		return this.porukaProperty().get();
	}

	public final void setPoruka(final String poruka) {
		this.porukaProperty().set(poruka);
	}

	public Button getActionButton() {
		return actionButton;
	}

	public final StringProperty odgovorProperty() {
		return this.odgovor;
	}
	

	public final String getOdgovor() {
		return this.odgovorProperty().get();
	}
	

	public final void setOdgovor(final String odgovor) {
		this.odgovorProperty().set(odgovor);
	}
	

}

