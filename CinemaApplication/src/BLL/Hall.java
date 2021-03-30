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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "Hall")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Hall.findAll", query = "SELECT h FROM Hall h"),
    @NamedQuery(name = "Hall.findByHallid", query = "SELECT h FROM Hall h WHERE h.hallid = :hallid"),
    @NamedQuery(name = "Hall.findByHallname", query = "SELECT h FROM Hall h WHERE h.hallname = :hallname"),
    @NamedQuery(name = "Hall.findByHallcapacity", query = "SELECT h FROM Hall h WHERE h.hallcapacity = :hallcapacity"),
    @NamedQuery(name = "Hall.findByHallrow", query = "SELECT h FROM Hall h WHERE h.hallrow = :hallrow"),
    @NamedQuery(name = "Hall.findByHallcolumns", query = "SELECT h FROM Hall h WHERE h.hallcolumns = :hallcolumns"),
    @NamedQuery(name = "Hall.findByHallCinemaid", query = "SELECT h FROM Hall h WHERE h.hallCinemaid = :hallCinemaid")})
public class Hall extends RecursiveTreeObject<Hall> implements Serializable {
    @JoinColumn(name = "Hall_Cinema_id", referencedColumnName = "Cinema_id")
    @ManyToOne(optional = false)
    private Cinema hallCinemaid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bTickethallid")
    private Collection<BoughtTickets> boughtTicketsCollection;
    private static final long serialVersionUID = 1L;
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Hall_id")
    @GeneratedValue(generator = "InvSeq")
    @SequenceGenerator(name = "InvSeq", sequenceName="INV_SEQ", allocationSize = 1)
    private Integer hallid;
    @Basic(optional = false)
    @Column(name = "Hall_name")
    private String hallname;
    @Basic(optional = false)
    @Column(name = "Hall_capacity")
    private short hallcapacity;
    @Basic(optional = false)
    @Column(name = "Hall_row")
    private short hallrow;
    @Basic(optional = false)
    @Column(name = "Hall_columns")
    private short hallcolumns;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tickethallid")
    private Collection<MovieTickets> movieTicketsCollection;

    public Hall() {
    }

    public Hall(Integer hallid) {
        this.hallid = hallid;
    }

    public Hall(Integer hallid, String hallname, short hallcapacity, short hallrow, short hallcolumns) {
        this.hallid = hallid;
        this.hallname = hallname;
        this.hallcapacity = hallcapacity;
        this.hallrow = hallrow;
        this.hallcolumns = hallcolumns;
    }

    public Integer getHallid() {
        return hallid;
    }

    public void setHallid(Integer hallid) {
        this.hallid = hallid;
    }

    public String getHallname() {
        return hallname;
    }

    public void setHallname(String hallname) {
        this.hallname = hallname;
    }

    public short getHallcapacity() {
        return hallcapacity;
    }

    public void setHallcapacity(short hallcapacity) {
        this.hallcapacity = hallcapacity;
    }

    public short getHallrow() {
        return hallrow;
    }

    public void setHallrow(short hallrow) {
        this.hallrow = hallrow;
    }

    public short getHallcolumns() {
        return hallcolumns;
    }

    public void setHallcolumns(short hallcolumns) {
        this.hallcolumns = hallcolumns;
    }

    @XmlTransient
    public Collection<MovieTickets> getMovieTicketsCollection() {
        return movieTicketsCollection;
    }

    public void setMovieTicketsCollection(Collection<MovieTickets> movieTicketsCollection) {
        this.movieTicketsCollection = movieTicketsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hallid != null ? hallid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Hall)) {
            return false;
        }
        Hall other = (Hall) object;
        if ((this.hallid == null && other.hallid != null) || (this.hallid != null && !this.hallid.equals(other.hallid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return hallname;
    }

    @XmlTransient
    public Collection<BoughtTickets> getBoughtTicketsCollection() {
        return boughtTicketsCollection;
    }

    public void setBoughtTicketsCollection(Collection<BoughtTickets> boughtTicketsCollection) {
        this.boughtTicketsCollection = boughtTicketsCollection;
    }

    public Cinema getHallCinemaid() {
        return hallCinemaid;
    }

    public void setHallCinemaid(Cinema hallCinemaid) {
        this.hallCinemaid = hallCinemaid;
    }
    
}
