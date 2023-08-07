package application;

import models.Nastavnik;
import models.Predmet;

public class DataSingleton{

	private static final DataSingleton instance = new DataSingleton();

	//trenutni nastavnik
	//trenutni predmet
	private Nastavnik nastavnik;
	private Predmet predmet;

	private DataSingleton() {
	}

	public static DataSingleton getInstance() {
		return instance;
	}

	public void setPredmet(Predmet p) {
		predmet = p;
	}

	public Predmet getPredmet() {
		return predmet;
	}

	public void setNastavnik(Nastavnik nastavnik) {
		this.nastavnik = nastavnik;
	}

	public Nastavnik getNastavnik() {
		return nastavnik;
	}
}
