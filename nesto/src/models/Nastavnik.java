package models;

public class Nastavnik {
	
	String ime;
	String prezime;
	String email;
	String zvanje;
	String odsjek;
	
	
	
	
	
	public Nastavnik(String ime, String prezime, String email, String zvanje,
			String odsjek) {
		super();
		
		this.ime = ime;
		this.prezime = prezime;
		this.email = email;
		this.zvanje = zvanje;
		this.odsjek = odsjek;
	
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
