/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "Movie_Tickets")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MovieTickets.findAll", query = "SELECT m FROM MovieTickets m"),
    @NamedQuery(name = "MovieTickets.findByTicketid", query = "SELECT m FROM MovieTickets m WHERE m.ticketid = :ticketid"),
    @NamedQuery(name = "MovieTickets.findByTicketplaydate", query = "SELECT m FROM MovieTickets m WHERE m.ticketplaydate = :ticketplaydate"),
    @NamedQuery(name = "MovieTickets.findByMovieid", query = "SELECT m FROM MovieTickets m WHERE m.movieid = :movieid AND m.ticketplaydate >= CURRENT_DATE ORDER BY m.ticketplaydate ASC"),
    @NamedQuery(name = "MovieTickets.findByTicketplayhour", query = "SELECT m FROM MovieTickets m WHERE m.ticketplayhour = :ticketplayhour"),
    @NamedQuery(name = "MovieTickets.findByTicketsavailable", query = "SELECT m FROM MovieTickets m WHERE m.ticketsavailable = :ticketsavailable"),
    @NamedQuery(name = "MovieTickets.findByMovieIdAndHallId", query = "SELECT m FROM MovieTickets m WHERE m.movieid = :movieid AND m.tickethallid = :tickethallid"),
    @NamedQuery(name = "MovieTickets.findByTicketssold", query = "SELECT m FROM MovieTickets m WHERE m.ticketssold = :ticketssold"),
    @NamedQuery(name = "MovieTickets.findByHall", query = "SELECT DISTINCT m.tickethallid FROM MovieTickets m WHERE m.movieid = :movieid"),
    @NamedQuery(name = "MovieTickets.findByTicketprice", query = "SELECT m FROM MovieTickets m WHERE m.ticketprice = :ticketprice")})
public class MovieTickets extends RecursiveTreeObject<MovieTickets> implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bTicketmovieticketid")
    private Collection<BoughtTickets> boughtTicketsCollection;
    private static final long serialVersionUID = 1L;
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Ticket_id")
    @GeneratedValue(generator = "InvSeq")
    @SequenceGenerator(name = "InvSeq", sequenceName="INV_SEQ", allocationSize = 1)
    private Integer ticketid;
    @Column(name = "Ticket_play_date")
    @Temporal(TemporalType.DATE)
    private Date ticketplaydate;
    @Column(name = "Ticket_play_hour")
    @Temporal(TemporalType.TIME)
    private Date ticketplayhour;
    @Column(name = "Tickets_available")
    private Short ticketsavailable;
    @Column(name = "Tickets_sold")
    private Short ticketssold;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Ticket_price")
    private BigDecimal ticketprice;
    @JoinColumn(name = "Ticket_hall_id", referencedColumnName = "Hall_id")
    @ManyToOne(optional = false)
    private Hall tickethallid;
    @JoinColumn(name = "Movie_id", referencedColumnName = "Movie_id")
    @ManyToOne(optional = false)
    private Movie movieid;

    public MovieTickets() {
        
    }

    public MovieTickets(Integer ticketid) {
        this.ticketid = ticketid;
    }

    public Integer getTicketid() {
        return ticketid;
    }

    public void setTicketid(Integer ticketid) {
        this.ticketid = ticketid;
    }

    public Date getTicketplaydate() {
        return ticketplaydate;
    }

    public void setTicketplaydate(Date ticketplaydate) {
        this.ticketplaydate = ticketplaydate;
    }

    public Date getTicketplayhour() {
        return ticketplayhour;
    }

    public void setTicketplayhour(Date ticketplayhour) {
        this.ticketplayhour = ticketplayhour;
    }

    public Short getTicketsavailable() {
        return ticketsavailable;
    }

    public void setTicketsavailable(Short ticketsavailable) {
        this.ticketsavailable = ticketsavailable;
    }

    public Short getTicketssold() {
        return ticketssold;
    }

    public void setTicketssold(Short ticketssold) {
        this.ticketssold = ticketssold;
    }

    public BigDecimal getTicketprice() {
        return ticketprice;
    }

    public void setTicketprice(BigDecimal ticketprice) {
        this.ticketprice = ticketprice;
    }

    public Hall getTickethallid() {
        return tickethallid;
    }

    public void setTickethallid(Hall tickethallid) {
        this.tickethallid = tickethallid;
    }

    public Movie getMovieid() {
        return movieid;
    }

    public void setMovieid(Movie movieid) {
        this.movieid = movieid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ticketid != null ? ticketid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MovieTickets)) {
            return false;
        }
        MovieTickets other = (MovieTickets) object;
        if ((this.ticketid == null && other.ticketid != null) || (this.ticketid != null && !this.ticketid.equals(other.ticketid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BLL.MovieTickets[ ticketid=" + ticketid + " ]";
    }

    @XmlTransient
    public Collection<BoughtTickets> getBoughtTicketsCollection() {
        return boughtTicketsCollection;
    }

    public void setBoughtTicketsCollection(Collection<BoughtTickets> boughtTicketsCollection) {
        this.boughtTicketsCollection = boughtTicketsCollection;
    }
    
}
