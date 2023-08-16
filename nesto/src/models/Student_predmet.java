package models;

public class Student_predmet {
    private int student_id;
    private String ImePrezimeStud;
	private String sifraPred;
	private String NazivPred;
	public Student_predmet(int student_id, String imePrezimeStud, String sifraPred, String nazivPred) {
		super();
		this.student_id = student_id;
		ImePrezimeStud = imePrezimeStud;
		this.sifraPred = sifraPred;
		NazivPred = nazivPred;
	}
	public Student_predmet() {
		super();
	}
	public int getStudent_id() {
		return student_id;
	}
	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}
	public String getImePrezimeStud() {
		return ImePrezimeStud;
	}
	public void setImePrezimeStud(String imePrezimeStud) {
		ImePrezimeStud = imePrezimeStud;
	}
	public String getSifraPred() {
		return sifraPred;
	}
	public void setSifraPred(String sifraPred) {
		this.sifraPred = sifraPred;
	}
	public String getNazivPred() {
		return NazivPred;
	}
	public void setNazivPred(String nazivPred) {
		NazivPred = nazivPred;
	}
	
	
	
}
