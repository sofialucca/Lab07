/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="cmbNerc"
    private ComboBox<Nerc> cmbNerc; // Value injected by FXMLLoader

    @FXML // fx:id="txtYears"
    private TextField txtYears; // Value injected by FXMLLoader

    @FXML // fx:id="txtHours"
    private TextField txtHours; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    private Model model;
    
    @FXML
    void doRun(ActionEvent event) {
    	txtResult.clear();
    	String oreStringa = txtHours.getText();
    	String anniStringa = txtYears.getText();
    	Nerc nercScelto = cmbNerc.getValue();
    	if(nercScelto.equals(null)) {
    		txtResult.appendText("ERRORE:scelgliere Nerc");
    		return;
    	}
    	if(isValid(oreStringa,anniStringa)) {
    		int ore = Integer.parseInt(oreStringa);
    		int anni = Integer.parseInt(anniStringa);
    		List <PowerOutage> listaGuasti = model.getWorstCase(nercScelto, anni, ore);
    		txtResult.appendText("Tot people affected: "+model.calcoloPersone(listaGuasti)
    			+"\nTot hours of outage: "+model.calcoloOre(listaGuasti));
    		for(PowerOutage po: listaGuasti) {
    			txtResult.appendText("\n"+po.toString());
    		}
    		
    	}
    }

    private boolean isValid(String oreStringa,String anniStringa) {
		try {
			int numero = Integer.parseInt(oreStringa);
		}catch(NumberFormatException nfe) {
			txtResult.appendText("ERRORE: inserire ore in formato corretto");
		}
		
		try {
			Integer.parseInt(anniStringa);
			return true;
		}catch(NumberFormatException nfe) {
			txtResult.appendText("ERRORE: inserire anno nel formato corretto");
		}
		return false;
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert cmbNerc != null : "fx:id=\"cmbNerc\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtYears != null : "fx:id=\"txtYears\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtHours != null : "fx:id=\"txtHours\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        

        // Utilizzare questo font per incolonnare correttamente i dati;
        txtResult.setStyle("-fx-font-family: monospace");
    }
    
    public void setModel(Model model) {
    	this.model = model;
        ObservableList<Nerc> listaNerc = FXCollections.observableArrayList(this.model.getNercList());
        this.cmbNerc.setItems(listaNerc);
    }
}
