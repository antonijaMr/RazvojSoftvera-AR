package models;

import javafx.scene.control.Button;

public class ZahtjevZaPrenosBodova {
	private Student student;
	private Predmet predmet;
	
	private double bodovi;
	private boolean odobreno;	
	private String poruka;
	
	private final Button actionButton;
	
	
	public ZahtjevZaPrenosBodova() {
		student = new Student();
		predmet = new Predmet();
		actionButton = new Button("Odgovori");
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

	public double getBodovi() {
		return bodovi;
	}

	public void setBodovi(double bodovi) {
		this.bodovi = bodovi;
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

	public Button getActionButton() {
		return actionButton;
	}

}
