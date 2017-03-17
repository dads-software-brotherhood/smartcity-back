package mx.infotec.smartcity.backend.model;
import org.springframework.data.annotation.Id;

public class Disease {
	@Id
	public String id;
	
	public String name;
	public String[] symptoms;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String[] getSymptoms() {
		return symptoms;
	}
	public void setSymptoms(String[] symptoms) {
		this.symptoms = symptoms;
	}
}
