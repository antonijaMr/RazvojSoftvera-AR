package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;

public class Predmet {
	private StringProperty sifraPred;
	private StringProperty kratPred;
	private StringProperty nazivPred;
	private StringProperty usmjerenje;
	private StringProperty predavanja_sati;
	private StringProperty lab_sati;
	private StringProperty av_sati;
	private StringProperty ECTS;
	private StringProperty semestar;
	private StringProperty preduslovi;
	private StringProperty uzaNaucnaOblast;
	private Nastavnik nastavnikN; //nosioc
	private Nastavnik nastavnikP; //predavac

	private final Button actionButton;
	
	public Predmet() {
		super();
		setNastavnikN(new Nastavnik());
		setNastavnikP(new Nastavnik());
		actionButton = new Button("Detalji");
		
		sifraPred = new SimpleStringProperty(this,"sifraPred");
		kratPred= new SimpleStringProperty(this,"kratPred");
		nazivPred= new SimpleStringProperty(this,"nazivPred");
		usmjerenje= new SimpleStringProperty(this,"usmjerenje");
		predavanja_sati= new SimpleStringProperty(this,"predavanja_sati");
		lab_sati= new SimpleStringProperty(this,"lab_sati");
		av_sati= new SimpleStringProperty(this,"av_sati");
		ECTS= new SimpleStringProperty(this,"ECTS");
		semestar = new SimpleStringProperty(this,"semestar");
		preduslovi= new SimpleStringProperty(this,"preduslovi");
		uzaNaucnaOblast= new SimpleStringProperty(this,"uzaNaucanOblast");
	}

	public Predmet(String sifraPred, String nazivPred, String usmjerenje, int predavanja_sati, int lab_sati,
			int av_sati, int eCTS, String semestar, String preduslovi) {
		super();
		this.actionButton = new Button("Detalji");

		this.sifraPredProperty().set(sifraPred);
		this.nazivPredProperty().set(nazivPred);
		this.usmjerenjeProperty().set(usmjerenje);
		this.predavanja_satiProperty().set(Integer.toString(predavanja_sati));
		this.av_satiProperty().set(Integer.toString(av_sati));
		this.lab_satiProperty().set(Integer.toString(lab_sati));
		this.ECTSProperty().set(Integer.toString(eCTS));
		this.semestarProperty().set(semestar);
		this.predusloviProperty().set(preduslovi);
	}

	public final StringProperty sifraPredProperty() {
		return this.sifraPred;
	}
	

	public final String getSifraPred() {
		return this.sifraPredProperty().get();
	}
	

	public final void setSifraPred(final String sifraPred) {
		this.sifraPredProperty().set(sifraPred);
	}
	

	public final StringProperty kratPredProperty() {
		return this.kratPred;
	}
	

	public final String getKratPred() {
		return this.kratPredProperty().get();
	}
	

	public final void setKratPred(final String kratPred) {
		this.kratPredProperty().set(kratPred);
	}
	

	public final StringProperty nazivPredProperty() {
		return this.nazivPred;
	}
	

	public final String getNazivPred() {
		return this.nazivPredProperty().get();
	}
	

	public final void setNazivPred(final String nazivPred) {
		this.nazivPredProperty().set(nazivPred);
	}
	

	public final StringProperty usmjerenjeProperty() {
		return this.usmjerenje;
	}
	

	public final String getUsmjerenje() {
		return this.usmjerenjeProperty().get();
	}
	

	public final void setUsmjerenje(final String usmjerenje) {
		this.usmjerenjeProperty().set(usmjerenje);
	}
	

	public final StringProperty predavanja_satiProperty() {
		return this.predavanja_sati;
	}
	

	public final String getPredavanja_sati() {
		return this.predavanja_satiProperty().get();
	}
	

	public final void setPredavanja_sati(final String predavanja_sati) {
		this.predavanja_satiProperty().set(predavanja_sati);
	}
	

	public final StringProperty lab_satiProperty() {
		return this.lab_sati;
	}
	

	public final String getLab_sati() {
		return this.lab_satiProperty().get();
	}
	

	public final void setLab_sati(final String lab_sati) {
		this.lab_satiProperty().set(lab_sati);
	}
	

	public final StringProperty av_satiProperty() {
		return this.av_sati;
	}
	

	public final String getAv_sati() {
		return this.av_satiProperty().get();
	}
	

	public final void setAv_sati(final String av_sati) {
		this.av_satiProperty().set(av_sati);
	}
	

	public final StringProperty ECTSProperty() {
		return this.ECTS;
	}
	

	public final String getECTS() {
		return this.ECTSProperty().get();
	}
	

	public final void setECTS(final String ECTS) {
		this.ECTSProperty().set(ECTS);
	}
	

	public final StringProperty semestarProperty() {
		return this.semestar;
	}
	

	public final String getSemestar() {
		return this.semestarProperty().get();
	}
	

	public final void setSemestar(final String semestar) {
		this.semestarProperty().set(semestar);
	}
	

	public final StringProperty predusloviProperty() {
		return this.preduslovi;
	}
	

	public final String getPreduslovi() {
		return this.predusloviProperty().get();
	}
	

	public final void setPreduslovi(final String preduslovi) {
		this.predusloviProperty().set(preduslovi);
	}
	

	public final StringProperty uzaNaucnaOblastProperty() {
		return this.uzaNaucnaOblast;
	}
	

	public final String getUzaNaucnaOblast() {
		return this.uzaNaucnaOblastProperty().get();
	}
	

	public final void setUzaNaucnaOblast(final String uzaNaucnaOblast) {
		this.uzaNaucnaOblastProperty().set(uzaNaucnaOblast);
	}

	public Nastavnik getNastavnikN() {
		return nastavnikN;
	}

	public void setNastavnikN(Nastavnik nastavnikN) {
		this.nastavnikN = nastavnikN;
	}

	public Nastavnik getNastavnikP() {
		return nastavnikP;
	}

	public void setNastavnikP(Nastavnik nastavnikP) {
		this.nastavnikP = nastavnikP;
	}

	public Button getActionButton() {
		return actionButton;
	}
	

}
