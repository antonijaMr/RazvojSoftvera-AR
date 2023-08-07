package models;

public class ZahtjevZaSlusanjePredmeta {
	private Student student;
	private Predmet predmet;

	private int sifNast;
	private boolean odobreno;
	private String poruka;

	public ZahtjevZaSlusanjePredmeta() {
		student = new Student();
		predmet = new Predmet();
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Predmet getPredmet() {
		return predmet;
	}

	public void setPredmet(Predmet predmet) {
		this.predmet = predmet;
	}

	public int getSifNast() {
		return sifNast;
	}

	public void setSifNast(int sifNast) {
		this.sifNast = sifNast;
	}

	public boolean isOdobreno() {
		return odobreno;
	}

	public void setOdobreno(boolean odobreno) {
		this.odobreno = odobreno;
	}

	public String getPoruka() {
		return poruka;
	}

	public void setPoruka(String poruka) {
		this.poruka = poruka;
	}

}
