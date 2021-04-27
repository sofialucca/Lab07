package it.polito.tdp.poweroutages.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;

public class PowerOutageDAO {
	
	public List<Nerc> getNercList() {

		String sql = "SELECT id, value FROM nerc";
		List<Nerc> nercList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				nercList.add(n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}

	public List<PowerOutage> getPowerOutagesList(Nerc nerc) {
		String sql = "SELECT id, customers_affected,date_event_began,date_event_finished demand_loss "
				+ "FROM poweroutages "
				+ "WHERE nerc_id = ? "
				+ "ORDER BY date_event_began";
		
		List <PowerOutage> result = new ArrayList<>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, nerc.getId());
			
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				
				PowerOutage po = new PowerOutage(rs.getInt("id"), nerc, rs.getTimestamp("date_event_began").toLocalDateTime(),rs.getTimestamp("date_event_finished").toLocalDateTime(),rs.getInt("customers_affected"));
				result.add(po);
			}
			rs.close();
			st.close();
			conn.close();
			return result;
		}catch(SQLException sqle) {
			throw new RuntimeException("Errore connessione DB",sqle);
		}
		
	}
	

}
