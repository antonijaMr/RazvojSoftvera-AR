package models;

import javafx.scene.control.Button;

public class Predmet {
	private String sifraPred;
	private String kratPred;
	private String nazivPred;
	private String usmjerenje;
	private int predavanja_sati;
	private int lab_sati;
	private int av_sati;
	private int ECTS;
	private String semestar;
	private String preduslovi;
	private String uzaNaucnaOblast;
	private Nastavnik nastavnikN; //nosioc
	private Nastavnik nastavnikP; //predavac
	private final Button actionButton;
	
	public Predmet() {
		super();
		nastavnikN = new Nastavnik();
		nastavnikP = new Nastavnik();
		actionButton = new Button("Detalji");
	}

	public Predmet(String sifraPred, String nazivPred, String usmjerenje, int predavanja_sati, int lab_sati,
			int av_sati, int eCTS, String semestar, String preduslovi) {
		super();
		this.sifraPred = sifraPred;
		this.nazivPred = nazivPred;
		this.usmjerenje = usmjerenje;
		this.predavanja_sati = predavanja_sati;
		this.lab_sati = lab_sati;
		this.av_sati = av_sati;
		ECTS = eCTS;
		this.semestar = semestar;
		this.preduslovi = preduslovi;
		kratPred = "";
		uzaNaucnaOblast ="";
		nastavnikN = new Nastavnik();
		nastavnikP = new Nastavnik();
		actionButton = new Button("Detalji");
	}

	public String getKratPred() {
		return kratPred;
	}

	public void setKratPred(String kratPred) {
		this.kratPred = kratPred;
	}

	public String getUzaNaucnaOblast() {
		return uzaNaucnaOblast;
	}

	public void setUzaNaucnaOblast(String uzaNaucnaOblast) {
		this.uzaNaucnaOblast = uzaNaucnaOblast;
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

	public String getSifraPred() {
		return sifraPred;
	}

	public void setSifraPred(String sifraPred) {
		this.sifraPred = sifraPred;
	}

	public String getNazivPred() {
		return nazivPred;
	}

	public void setNazivPred(String nazivPred) {
		this.nazivPred = nazivPred;
	}

	public String getUsmjerenje() {
		return usmjerenje;
	}

	public void setUsmjerenje(String usmjerenje) {
		this.usmjerenje = usmjerenje;
	}

	public int getPredavanja_sati() {
		return predavanja_sati;
	}

	public void setPredavanja_sati(int predavanja_sati) {
		this.predavanja_sati = predavanja_sati;
	}

	public int getLab_sati() {
		return lab_sati;
	}

	public void setLab_sati(int lab_sati) {
		this.lab_sati = lab_sati;
	}

	public int getAv_sati() {
		return av_sati;
	}

	public void setAv_sati(int av_sati) {
		this.av_sati = av_sati;
	}

	public int getECTS() {
		return ECTS;
	}

	public void setECTS(int eCTS) {
		ECTS = eCTS;
	}

	public String getSemestar() {
		return semestar;
	}

	public void setSemestar(String semestar) {
		this.semestar = semestar;
	}

	public String getPreduslovi() {
		return preduslovi;
	}

	public void setPreduslovi(String preduslovi) {
		this.preduslovi = preduslovi;
	}

	public Button getActionButton() {
		return actionButton;
	}

}
