package mx.infotec.smartcity.backend.service.keystone.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Scope implements Serializable {

    private static final long serialVersionUID = 1L;
    private Domain_ domain;

    public Scope(String idDomain) {
        this.domain = new Domain_(idDomain);
    }

    public Domain_ getDomain() {
        return domain;
    }

    public void setDomain(Domain_ domain) {
        this.domain = domain;
    }
}
