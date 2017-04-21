/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.infotec.smartcity.backend.model;

import java.io.Serializable;
import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
/**
 *
 * @author jose.gomez
 */
@Document
public class VehicleType implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    private Integer id;
    @Indexed(unique = true)
    private String name;
    private Boolean includeBrandModel;
    private Date dateCreated;
    private Date dateModified;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Boolean getIncludeBrandModel() {
        return includeBrandModel;
    }

    public void setIncludeBrandModel(Boolean includeBrandModel) {
        this.includeBrandModel = includeBrandModel;
    }
    
    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
    
    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }
    
}
