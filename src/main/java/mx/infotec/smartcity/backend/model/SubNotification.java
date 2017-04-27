/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.infotec.smartcity.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 *
 * @author Manu
 */
public class SubNotification {
    private static final long serialVersionUID = 1L;
    
    @Id
    private Integer id;
    private String name;
    @Indexed
    @DBRef
    private Notification notification;
    
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
    
    public Notification getCountry() {
        return notification;
    }

    public void setCountry(Notification notification) {
        this.notification = notification;
    }
}
