package models;

public class Predmet {
	  String sifraPred;
	  String nazivPred;
	  String usmjerenje;
	  int predavanja_sati;
	  int lab_sati;
	  int av_sati;
	  int ECTS;
	  String semestar;
	  String preduslovi;
	public Predmet(String sifraPred, String nazivPred, String usmjerenje, int predavanja_sati, int lab_sati, int av_sati,
			int eCTS, String semestar,String preduslovi) {
		super();
		this.sifraPred = sifraPred;
		this.nazivPred = nazivPred;
		this.usmjerenje = usmjerenje;
		this.predavanja_sati = predavanja_sati;
		this.lab_sati = lab_sati;
		this.av_sati = av_sati;
		ECTS = eCTS;
		this.semestar = semestar;
		this.preduslovi=preduslovi;
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


	}

