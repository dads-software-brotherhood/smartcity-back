package mx.infotec.smartcity.backend.service.keystone.pojo;

import java.io.Serializable;

/**
 *
 * @author Erik Valdivieso
 */
public class Project implements Serializable {

	private static final long serialVersionUID = 1L;

	private String description;
	private String domain_id;
	private boolean enabled;
	private boolean is_domain;
	private String name;

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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isIs_domain() {
		return is_domain;
	}

	public void setIs_domain(boolean is_domain) {
		this.is_domain = is_domain;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
