package mx.infotec.smartcity.backend.service.keystone.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


/**
 *
 * @author Erik Valdivieso
 */
public class Group implements Serializable {

	private static final long serialVersionUID = 1L;

	private String description;
	private String domain_id;
	private String id;
	private String name;
	private HashMap<String,String> links;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDomain_id() {
		return domain_id;
	}

	public void setDomain_id(String domain_id) {
		this.domain_id = domain_id;
	}

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

	public HashMap<String, String> getLinks() {
		return links;
	}

	public void setLinks(HashMap<String, String> links) {
		this.links = links;
	}

	public void addLink(String nombre,String url){
		if(this.links == null){
			this.links = new HashMap<String,String>();
		}
		this.links.put(nombre, url);
	}
}
