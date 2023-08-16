package models;

public class Zahtjevi {

	private String ime;
	private String prezime;
	private int brIndeks;
	private String predmet1;
	private String predmet2;
	private String obrazlozenje;
	public Zahtjevi(String ime, String prezime, int brIndeks, String predmet1, String predmet2, String obrazlozenje) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.brIndeks = brIndeks;
		this.predmet1 = predmet1;
		this.predmet2 = predmet2;
		this.obrazlozenje = obrazlozenje;
	}
	public Zahtjevi() {
		super();
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	public int getBrIndeks() {
		return brIndeks;
	}
	public void setBrIndeks(int brIndeks) {
		this.brIndeks = brIndeks;
	}
	public String getPredmet1() {
		return predmet1;
	}
	public void setPredmet1(String predmet1) {
		this.predmet1 = predmet1;
	}
	public String getPredmet2() {
		return predmet2;
	}
	public void setPredmet2(String predmet2) {
		this.predmet2 = predmet2;
	}
	public String getObrazlozenje() {
		return obrazlozenje;
	}
	public void setObrazlozenje(String obrazlozenje) {
		this.obrazlozenje = obrazlozenje;
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) {
	        return true;
	    }
	    if (obj == null || getClass() != obj.getClass()) {
	        return false;
	    }
	    Zahtjevi other = (Zahtjevi) obj;
	    return (brIndeks == other.brIndeks) && (predmet1.equals(other.predmet1)) && (predmet2.equals(other.predmet2));
	}
	
}
