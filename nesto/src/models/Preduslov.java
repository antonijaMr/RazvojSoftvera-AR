package models;

public class Preduslov {
	private Predmet predmet;
	private int ocjena;
	private boolean obnova;
	
	public Predmet getPredmet() {
		return predmet;
	}

	public void setPredmet(Predmet predmet) {
		this.predmet = predmet;
	}

	public int getOcjena() {
		return ocjena;
	}

	public void setOcjena(int ocjena) {
		this.ocjena = ocjena;
	}

	public boolean isObnova() {
		return obnova;
	}

	public void setObnova(boolean obnova) {
		this.obnova = obnova;
	}

	public Preduslov() {
		predmet = new Predmet();
	}
}
