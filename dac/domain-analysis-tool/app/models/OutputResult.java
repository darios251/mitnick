package models;

import java.util.List;

import javax.persistence.Entity;

import play.data.validation.Required;
import play.db.jpa.Blob;
import play.db.jpa.Model;
import util.OutputDTO;

public class OutputResult {
	
	public static OutputResult output;
	
	public String fileName;
	
	public List<OutputDTO> results;
	
	public List<OutputDTO> getResult() {
		if (results==null)
			results = OutputDTO.getPruebas();
		return results;
	}
	
	public static OutputResult getInstance(){
		if (output==null){
			output = new OutputResult();
			output.getResult();
		}
		return output;
	}
	
}
