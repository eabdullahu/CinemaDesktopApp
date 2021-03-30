/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "Cinema")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cinema.findAll", query = "SELECT c FROM Cinema c"),
    @NamedQuery(name = "Cinema.findByCinemaid", query = "SELECT c FROM Cinema c WHERE c.cinemaid = :cinemaid"),
    @NamedQuery(name = "Cinema.findByCinemaname", query = "SELECT c FROM Cinema c WHERE c.cinemaname = :cinemaname"),
    @NamedQuery(name = "Cinema.findByCinemalocation", query = "SELECT c FROM Cinema c WHERE c.cinemalocation = :cinemalocation"),
    @NamedQuery(name = "Cinema.findByCinemaphone", query = "SELECT c FROM Cinema c WHERE c.cinemaphone = :cinemaphone")})
public class Cinema extends RecursiveTreeObject<Cinema> implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "movieCinemaid")
    private Collection<Movie> movieCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hallCinemaid")
    private Collection<Hall> hallCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bTicketcinemaid")
    private Collection<BoughtTickets> boughtTicketsCollection;
    private static final long serialVersionUID = 1L;
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Cinema_id")
    @GeneratedValue(generator = "InvSeq")
    @SequenceGenerator(name = "InvSeq", sequenceName="INV_SEQ", allocationSize = 1)
    private Integer cinemaid;
    @Column(name = "Cinema_name")
    private String cinemaname;
    @Column(name = "Cinema_location")
    private String cinemalocation;
    @Column(name = "Cinema_phone")
    private String cinemaphone;

    public Cinema() {
    }

    public Cinema(Integer cinemaid) {
        this.cinemaid = cinemaid;
    }

    public Integer getCinemaid() {
        return cinemaid;
    }

    public void setCinemaid(Integer cinemaid) {
        this.cinemaid = cinemaid;
    }

    public String getCinemaname() {
        return cinemaname;
    }

    public void setCinemaname(String cinemaname) {
        this.cinemaname = cinemaname;
    }

    public String getCinemalocation() {
        return cinemalocation;
    }

    public void setCinemalocation(String cinemalocation) {
        this.cinemalocation = cinemalocation;
    }

    public String getCinemaphone() {
        return cinemaphone;
    }

    public void setCinemaphone(String cinemaphone) {
        this.cinemaphone = cinemaphone;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cinemaid != null ? cinemaid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cinema)) {
            return false;
        }
        Cinema other = (Cinema) object;
        if ((this.cinemaid == null && other.cinemaid != null) || (this.cinemaid != null && !this.cinemaid.equals(other.cinemaid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return cinemaname;
    }

    @XmlTransient
    public Collection<BoughtTickets> getBoughtTicketsCollection() {
        return boughtTicketsCollection;
    }

    public void setBoughtTicketsCollection(Collection<BoughtTickets> boughtTicketsCollection) {
        this.boughtTicketsCollection = boughtTicketsCollection;
    }

    @XmlTransient
    public Collection<Movie> getMovieCollection() {
        return movieCollection;
    }

    public void setMovieCollection(Collection<Movie> movieCollection) {
        this.movieCollection = movieCollection;
    }

    @XmlTransient
    public Collection<Hall> getHallCollection() {
        return hallCollection;
    }

    public void setHallCollection(Collection<Hall> hallCollection) {
        this.hallCollection = hallCollection;
    }
    
}
