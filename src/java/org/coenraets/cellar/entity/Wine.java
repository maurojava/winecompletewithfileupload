/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.coenraets.cellar.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mauro
 */
@Entity
@Table(name = "wine")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Wine.findAll", query = "SELECT w FROM Wine w"),
    @NamedQuery(name = "Wine.findById", query = "SELECT w FROM Wine w WHERE w.id = :id"),
    @NamedQuery(name = "Wine.findByName", query = "SELECT w FROM Wine w WHERE w.name = :name"),

    @NamedQuery(name = "Wine.findByYear", query = "SELECT w FROM Wine w WHERE w.year = :year"),
    @NamedQuery(name = "Wine.findByGrapes", query = "SELECT w FROM Wine w WHERE w.grapes = :grapes"),
    @NamedQuery(name = "Wine.findByCountry", query = "SELECT w FROM Wine w WHERE w.country = :country"),
    @NamedQuery(name = "Wine.findByRegion", query = "SELECT w FROM Wine w WHERE w.region = :region"),
    @NamedQuery(name = "Wine.findByPicture", query = "SELECT w FROM Wine w WHERE w.picture = :picture")})
   
public class Wine implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "name")
    private String name;
    @Size(max = 45)
    @Column(name = "year")
    private String year;
    @Size(max = 45)
    @Column(name = "grapes")
    private String grapes;
    @Size(max = 45)
    @Column(name = "country")
    private String country;
    @Size(max = 45)
    @Column(name = "region")
    private String region;
    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;
    @Size(max = 256)
    @Column(name = "picture")
    private String picture;

    public Wine() {
    }

    public Wine(Integer id) {
        this.id = id;
    }

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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGrapes() {
        return grapes;
    }

    public void setGrapes(String grapes) {
        this.grapes = grapes;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Wine)) {
            return false;
        }
        Wine other = (Wine) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.coenraets.cellar.entity.Wine[ id=" + id + " ]";
    }
    
}
