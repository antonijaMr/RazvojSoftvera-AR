package models;

public class Predmet2 {
		

		public Predmet2(String nazivpred2, String sifraPred2, String usmjerenje2, Integer predavanja_sati2, Integer lab_sati2,
				Integer av_sati2, Integer eCTS2, String semestar2, String kratPred2) {
			this.setNazivpred(nazivpred2);
			this.setSifraPred(sifraPred2);
			this.setUsmjerenje(usmjerenje2);
			this.setPredavanja_sati(predavanja_sati2);
			this.setLab_sati(lab_sati2);
			this.setAv_sati(av_sati2);
			this.setECTS(eCTS2);
	        this.setSemestar(semestar2); 
	        this.setKratpred(kratPred2);
		}
		public Predmet2() {
			// TODO Auto-generated constructor stub
		}
		public String getNazivpred() {
			return nazivpred;
		}
		public void setNazivpred(String nazivpred) {
			this.nazivpred = nazivpred;
		}
		public String getSifraPred() {
			return sifraPred;
		}
		public void setSifraPred(String sifraPred) {
			this.sifraPred = sifraPred;
		}
		public String getUsmjerenje() {
			return usmjerenje;
		}
		public void setUsmjerenje(String usmjerenje) {
			this.usmjerenje = usmjerenje;
		}
		public Integer getPredavanja_sati() {
			return predavanja_sati;
		}
		public void setPredavanja_sati(int predavanja_sati) {
			this.predavanja_sati = predavanja_sati;
		}
		public Integer getLab_sati() {
			return lab_sati;
		}
		public void setLab_sati(int lab_sati) {
			this.lab_sati = lab_sati;
		}
		public Integer getAv_sati() {
			return av_sati;
		}
		public void setAv_sati(int av_sati) {
			this.av_sati = av_sati;
		}
		public Integer getECTS() {
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
		public String getKratpred() {
			return kratpred;
		}
		public void setKratpred(String kratpred) {
			this.kratpred = kratpred;
		}

		private String nazivpred;
		private String sifraPred;
		private String usmjerenje;
		private Integer predavanja_sati;
		private Integer lab_sati;
		private Integer av_sati;
		private Integer ECTS;
		private String semestar;
		private String kratpred;
		
	}




