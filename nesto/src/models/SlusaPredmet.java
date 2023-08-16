package models;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SlusaPredmet {
	private StringProperty bodovi;
	private StringProperty ocjena;

	public SlusaPredmet() {
		bodovi = new SimpleStringProperty(this, "bodovi");
		ocjena = new SimpleStringProperty(this, "ocjena");
	}

	public String getBodovi() {
		return this.bodoviProperty().get();
	}

	public void setBodovi(String bodovi) {
		this.bodoviProperty().set(bodovi);;
	}

	public String getOcjena() {
		return this.ocjenaProperty().get();
	}

	public void setOcjena(String ocjena) {
		this.ocjenaProperty().set(ocjena);;
	}

	public StringProperty bodoviProperty() {
		return this.bodovi;
	}

	public StringProperty ocjenaProperty() {
		return this.ocjena;
	}
}

