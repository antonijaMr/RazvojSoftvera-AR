package models;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Preduslov {
	private Predmet predmet;
	private StringProperty ocjena;
	private StringProperty obnova;
	private Nastavnik nastavnikN;
	
	public Preduslov() {
		predmet = new Predmet();
		nastavnikN = new Nastavnik();
		ocjena= new SimpleStringProperty(this,"ocjena");
		obnova= new SimpleStringProperty(this,"obnova");
	}

	public Predmet getPredmet() {
		return predmet;
	}

	public void setPredmet(Predmet predmet) {
		this.predmet = predmet;
	}

	public Nastavnik getNastavnikN() {
		return nastavnikN;
	}

	public void setNastavnikN(Nastavnik nastavnikN) {
		this.nastavnikN = nastavnikN;
	}

	public final StringProperty ocjenaProperty() {
		return this.ocjena;
	}
	

	public final String getOcjena() {
		return this.ocjenaProperty().get();
	}
	

	public final void setOcjena(final String ocjena) {
		this.ocjenaProperty().set(ocjena);
	}
	

	public final StringProperty obnovaProperty() {
		return this.obnova;
	}
	

	public final String getObnova() {
		return this.obnovaProperty().get();
	}
	

	public final void setObnova(final String obnova) {
		this.obnovaProperty().set(obnova);
	}
	
	

}
