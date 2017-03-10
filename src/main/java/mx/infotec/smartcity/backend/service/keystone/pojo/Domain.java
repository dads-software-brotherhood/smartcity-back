package mx.infotec.smartcity.backend.service.keystone.pojo;

import java.io.Serializable;

/**
 *
 * @author Erik Valdivieso
 */
public class Domain implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;

    public Domain() {
    }

    public Domain(String id) {
        this.id = id;
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
}
