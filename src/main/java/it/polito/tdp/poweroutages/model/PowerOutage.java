package it.polito.tdp.poweroutages.model;

import java.time.*;

public class PowerOutage {

	private int id;
	private Nerc nerc;
	private LocalDateTime dataInizio;
	private LocalDateTime dataFine;
	private int personeAffette;
	private int nOre;
	
	public PowerOutage(int id, Nerc nerc, LocalDateTime dataInizio, LocalDateTime dataFine, int personeAffette) {
		super();
		this.id = id;
		this.nerc = nerc;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.personeAffette = personeAffette;
		if(dataFine.getDayOfMonth() == dataInizio.getDayOfMonth()) {
			nOre = dataFine.getHour() - dataInizio.getHour();
		}else {
			nOre = (24-dataInizio.getHour()) + dataFine.getHour();
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Nerc getNerc() {
		return nerc;
	}

	public void setNerc(Nerc nerc) {
		this.nerc = nerc;
	}

	public LocalDateTime getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(LocalDateTime dataInizio) {
		this.dataInizio = dataInizio;
	}

	public LocalDateTime getDataFine() {
		return dataFine;
	}

	public void setDataFine(LocalDateTime dataFine) {
		this.dataFine = dataFine;
	}

	public int getPersoneAffette() {
		return personeAffette;
	}

	public void setPersoneAffette(int personeAffette) {
		this.personeAffette = personeAffette;
	}

	public int getnOre() {
		return nOre;
	}

	public void setnOre(int nOre) {
		this.nOre = nOre;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PowerOutage other = (PowerOutage) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%4d %18s %20s %2d %8d",dataInizio.getYear(),dataInizio.toString(),dataFine.toString(),nOre,personeAffette));
		return sb.toString();
	}
	
	
}
