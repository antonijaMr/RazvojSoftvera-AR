package models;

public class Student2 {


	
		public Student2(int brojIndeksa2,String ime2, String Prezime2,String email2,int godStudija2,String statusStud,String sifUsmjerenja, int ostvareniECTS, boolean zavrsioReg2) {
			this.setBrojIndeksa(brojIndeksa2);
			this.setIme(ime2);
			this.setPrezime(Prezime2);
			this.setEmail(email2);
			this.setGodStudija(godStudija2);
			this.setGodStudija(godStudija2);
			this.setStatusStud(statusStud);
			this.setSifUsmjerenja(sifUsmjerenja);
			this.setOstvareniECTS(ostvareniECTS);
			this.setZavrsioReg(zavrsioReg2);
		};
	  
		public Student2() {
			super();
		}

		private int brojIndeksa;
	    private String ime;
	    private String Prezime;
	    private String email;
	    private int godStudija;
	    private String statusStud;
	    private String sifUsmjerenja;
	    private int ostvareniECTS;
	    private boolean zavrsioReg;
		public int getOstvareniECTS() {
			return ostvareniECTS;
		}
		public void setOstvareniECTS(int ostvareniECTS) {
			this.ostvareniECTS = ostvareniECTS;
		}
		public String getSifUsmjerenja() {
			return sifUsmjerenja;
		}
		public void setSifUsmjerenja(String sifUsmjerenja) {
			this.sifUsmjerenja = sifUsmjerenja;
		}
		public String getStatusStud() {
			return statusStud;
		}
		public void setStatusStud(String statusStud) {
			this.statusStud = statusStud;
		}
		public int getGodStudija() {
			return godStudija;
		}
		public void setGodStudija(int godStudija) {
			this.godStudija = godStudija;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPrezime() {
			return Prezime;
		}
		public void setPrezime(String prezime) {
			Prezime = prezime;
		}
		public String getIme() {
			return ime;
		}
		public void setIme(String ime) {
			this.ime = ime;
		}
		public int getBrojIndeksa() {
			return brojIndeksa;
		}
		public void setBrojIndeksa(int brojIndeksa) {
			this.brojIndeksa = brojIndeksa;
		}
		public boolean isZavrsioReg() {
			return zavrsioReg;
		}
		public void setZavrsioReg(boolean zavrsioReg) {
			this.zavrsioReg = zavrsioReg;
		}    
	}


