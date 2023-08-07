package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;

public class ZahtjevZaPrenosBodova {
	public Student stud;
	public Predmet pred;

	private StringProperty brojBodova;
	private StringProperty odobreno;
	private StringProperty poruka;
	private final Button actionButton;

	public ZahtjevZaPrenosBodova() {
		stud = new Student();
		pred = new Predmet();
		brojBodova = new SimpleStringProperty(this, "brojBodova");
		actionButton = new Button("Odgovori");
	}

	public Button getActionButton() {
		return actionButton;
	}

	public final StringProperty brojBodovaProperty() {
		return this.brojBodova;
	}

	public final String getBrojBodova() {
		return this.brojBodovaProperty().get();
	}

	public final void setBrojBodova(final String brojBodova) {
		this.brojBodovaProperty().set(brojBodova);
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
	
}
