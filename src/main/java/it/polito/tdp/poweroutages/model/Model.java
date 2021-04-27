package it.polito.tdp.poweroutages.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	PowerOutageDAO podao;
	private int personeOttime;
	private int oreOttime;
	private List<PowerOutage> listaGuasti;
	private List<PowerOutage> partenza;
	
	public Model() {
		podao = new PowerOutageDAO();
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}
	
	public List<PowerOutage> getWorstCase(Nerc nercScelto, int anniMax, int oreMax){
		listaGuasti = new ArrayList<>();
		List<PowerOutage> parziale = new ArrayList<>();
		personeOttime = 0;
		oreOttime = 0;
		partenza = this.podao.getPowerOutagesList(nercScelto);
		
		ricorsiva(parziale,0, anniMax, oreMax);
		return listaGuasti;
	}

	private void ricorsiva(List<PowerOutage> parziale,int livello, int anniMax, int oreMax) {
		int oreTotali = calcoloOre(parziale);

		if(oreTotali>oreMax) {
			return;
		}
		
		int personeTotali = calcoloPersone(parziale);
		if(personeOttime<personeTotali) {
			personeOttime = personeTotali;
			listaGuasti = new ArrayList<>(parziale);
			oreOttime = oreTotali;
		}
		if(livello == partenza.size()) {
			return;
		}else {
			PowerOutage nuovoPo = partenza.get(livello);
			if(parziale.size()==0||nuovoPo.getDataFine().compareTo(parziale.get(0).getDataInizio().plusYears(anniMax))<=0) {
				parziale.add(nuovoPo);
				ricorsiva(parziale,livello+1,anniMax, oreMax);
				parziale.remove(nuovoPo);
				ricorsiva(parziale,livello+1,anniMax,oreMax);
			}else {
				return;
			}
		}
	}

	public int calcoloOre(List<PowerOutage> parziale) {
		int cnt = 0;
		for(PowerOutage po: parziale) {
			cnt += po.getnOre();
		}
		return cnt;
	}

	public int calcoloPersone(List<PowerOutage> parziale) {
		int cnt = 0;
		for(PowerOutage po: parziale) {
			cnt+=po.getPersoneAffette();
		}
		return cnt;
	}

}
