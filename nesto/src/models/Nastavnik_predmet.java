package models;

import java.util.List;

import javafx.util.Pair;

public class Nastavnik_predmet {

public Nastavnik_predmet() {
		super();
	}
private String nastavniPredmet;
private int PZ;
private int AZ;
private int LZ;
private int PLJ;
private int ALJ;
private int LLJ;
private List<Pair<String,String>> nastavnik_nosioc;
public Nastavnik_predmet(String nastavniPredmet, int pZ, int aZ, int lZ, int pLJ, int aLJ, int lLJ,
		List<Pair<String, String>> nastavnik_nosioc) {
	super();
	this.nastavniPredmet = nastavniPredmet;
	PZ = pZ;
	AZ = aZ;
	LZ = lZ;
	PLJ = pLJ;
	ALJ = aLJ;
	LLJ = lLJ;
	this.nastavnik_nosioc = nastavnik_nosioc;
}

public String getNastavniPredmet() {
	return nastavniPredmet;
}
public void setNastavniPredmet(String nastavniPredmet) {
	this.nastavniPredmet = nastavniPredmet;
}
public int getPZ() {
	return PZ;
}
public void setPZ(int pZ) {
	PZ = pZ;
}
public int getAZ() {
	return AZ;
}
public void setAZ(int aZ) {
	AZ = aZ;
}
public int getLZ() {
	return LZ;
}
public void setLZ(int lZ) {
	LZ = lZ;
}
public int getPLJ() {
	return PLJ;
}
public void setPLJ(int pLJ) {
	PLJ = pLJ;
}
public int getALJ() {
	return ALJ;
}
public void setALJ(int aLJ) {
	ALJ = aLJ;
}
public int getLLJ() {
	return LLJ;
}
public void setLLJ(int lLJ) {
	LLJ = lLJ;
}
public List<Pair<String, String>> getNastavnik_nosioc() {
	return nastavnik_nosioc;
}
public void setNastavnik_nosioc(List<Pair<String, String>> nastavnik_nosioc) {
	this.nastavnik_nosioc = nastavnik_nosioc;
}




	
	
	
}
