package models;

public class Nastavnik {

	private int sifNast;
	private String ime;
	private String prezime;
	private String email;
	private String zvanje;
	private String odsjek;
	private boolean prodekan;

	public Nastavnik() {}

	public Nastavnik(String ime, String prezime, String email, String zvanje, String odsjek) {
		super();

		this.ime = ime;
		this.prezime = prezime;
		this.email = email;
		this.zvanje = zvanje;
		this.odsjek = odsjek;

	}

	public int getSifNast() {
		return sifNast;
	}

	public void setSifNast(int sifNast) {
		this.sifNast = sifNast;
	}

	public boolean isProdekan() {
		return prodekan;
	}

	public void setProdekan(boolean prodekan) {
		this.prodekan = prodekan;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getZvanje() {
		return zvanje;
	}

	public void setZvanje(String zvanje) {
		this.zvanje = zvanje;
	}

	public String getOdsjek() {
		return odsjek;
	}

	public void setOdsjek(String odsjek) {
		this.odsjek = odsjek;
	}

}
