/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author armin
 */
@Entity
@Table(name = "BoughtTickets")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "BoughtTickets.findAll", query = "SELECT b FROM BoughtTickets b"),
    @NamedQuery(name = "BoughtTickets.findByBTicketid", query = "SELECT b FROM BoughtTickets b WHERE b.bTicketid = :bTicketid"),
    @NamedQuery(name = "BoughtTickets.findByBTicktestatus", query = "SELECT b FROM BoughtTickets b WHERE b.bTicktestatus = :bTicktestatus"),
    @NamedQuery(name = "BoughtTickets.findByBTicketgeneratedtoken", query = "SELECT b FROM BoughtTickets b WHERE b.bTicketgeneratedtoken = :bTicketgeneratedtoken"),
    @NamedQuery(name = "BoughtTickets.findByMovieIdAndTicketId", query = "SELECT b FROM BoughtTickets b WHERE b.bTicketmovieid = :bTicketmovieid AND b.bTicketmovieticketid = :bTicketmovieticketid"),
    @NamedQuery(name = "BoughtTickets.findByBTicketPrice", query = "SELECT b FROM BoughtTickets b WHERE b.bTicketPrice = :bTicketPrice"),
    @NamedQuery(name = "BoughtTickets.findByBTicketseatnumber", query = "SELECT b FROM BoughtTickets b WHERE b.bTicketseatnumber = :bTicketseatnumber"),
    @NamedQuery(name = "BoughtTickets.findByClient", query = "SELECT b FROM BoughtTickets b WHERE b.bTicketclientid = :bTicketclientid"),
    @NamedQuery(name = "BoughtTickets.findByBTicketrow", query = "SELECT b FROM BoughtTickets b WHERE b.bTicketrow = :bTicketrow"),
    @NamedQuery(name = "BoughtTickets.findByBTicketdate", query = "SELECT b FROM BoughtTickets b WHERE b.bTicketdate = :bTicketdate")})
public class BoughtTickets implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "BTicket_id")
    private Integer bTicketid;
    @Column(name = "BTickte_status")
    private String bTicktestatus;
    @Column(name = "BTicket_generated_token")
    private String bTicketgeneratedtoken;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "BTicket_Price")
    private BigDecimal bTicketPrice;
    @Basic(optional = false)
    @Column(name = "BTicket_seat_number")
    private short bTicketseatnumber;
    @Basic(optional = false)
    @Column(name = "BTicket_row")
    private short bTicketrow;
    @Column(name = "BTicket_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date bTicketdate;
    @JoinColumn(name = "BTicket_cinema_id", referencedColumnName = "Cinema_id")
    @ManyToOne(optional = false)
    private Cinema bTicketcinemaid;
    @JoinColumn(name = "BTicket_client_id", referencedColumnName = "Client_id")
    @ManyToOne(optional = false)
    private Consumator bTicketclientid;
    @JoinColumn(name = "BTicket_hall_id", referencedColumnName = "Hall_id")
    @ManyToOne(optional = false)
    private Hall bTickethallid;
    @JoinColumn(name = "BTicket_movie_id", referencedColumnName = "Movie_id")
    @ManyToOne(optional = false)
    private Movie bTicketmovieid;
    @JoinColumn(name = "BTicket_movie_ticket_id", referencedColumnName = "Ticket_id")
    @ManyToOne(optional = false)
    private MovieTickets bTicketmovieticketid;

    public BoughtTickets() {
    }

    public BoughtTickets(Integer bTicketid) {
        this.bTicketid = bTicketid;
    }

    public BoughtTickets(Integer bTicketid, BigDecimal bTicketPrice, short bTicketseatnumber, short bTicketrow) {
        this.bTicketid = bTicketid;
        this.bTicketPrice = bTicketPrice;
        this.bTicketseatnumber = bTicketseatnumber;
        this.bTicketrow = bTicketrow;
    }

    public Integer getBTicketid() {
        return bTicketid;
    }

    public void setBTicketid(Integer bTicketid) {
        this.bTicketid = bTicketid;
    }

    public String getBTicktestatus() {
        return bTicktestatus;
    }

    public void setBTicktestatus(String bTicktestatus) {
        this.bTicktestatus = bTicktestatus;
    }

    public String getBTicketgeneratedtoken() {
        return bTicketgeneratedtoken;
    }

    public void setBTicketgeneratedtoken(String bTicketgeneratedtoken) {
        this.bTicketgeneratedtoken = bTicketgeneratedtoken;
    }

    public BigDecimal getBTicketPrice() {
        return bTicketPrice;
    }

    public void setBTicketPrice(BigDecimal bTicketPrice) {
        this.bTicketPrice = bTicketPrice;
    }

    public short getBTicketseatnumber() {
        return bTicketseatnumber;
    }

    public void setBTicketseatnumber(short bTicketseatnumber) {
        this.bTicketseatnumber = bTicketseatnumber;
    }

    public short getBTicketrow() {
        return bTicketrow;
    }

    public void setBTicketrow(short bTicketrow) {
        this.bTicketrow = bTicketrow;
    }

    public Date getBTicketdate() {
        return bTicketdate;
    }

    public void setBTicketdate(Date bTicketdate) {
        this.bTicketdate = bTicketdate;
    }

    public Cinema getBTicketcinemaid() {
        return bTicketcinemaid;
    }

    public void setBTicketcinemaid(Cinema bTicketcinemaid) {
        this.bTicketcinemaid = bTicketcinemaid;
    }

    public Consumator getBTicketclientid() {
        return bTicketclientid;
    }

    public void setBTicketclientid(Consumator bTicketclientid) {
        this.bTicketclientid = bTicketclientid;
    }

    public Hall getBTickethallid() {
        return bTickethallid;
    }

    public void setBTickethallid(Hall bTickethallid) {
        this.bTickethallid = bTickethallid;
    }

    public Movie getBTicketmovieid() {
        return bTicketmovieid;
    }

    public void setBTicketmovieid(Movie bTicketmovieid) {
        this.bTicketmovieid = bTicketmovieid;
    }

    public MovieTickets getBTicketmovieticketid() {
        return bTicketmovieticketid;
    }

    public void setBTicketmovieticketid(MovieTickets bTicketmovieticketid) {
        this.bTicketmovieticketid = bTicketmovieticketid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bTicketid != null ? bTicketid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BoughtTickets)) {
            return false;
        }
        BoughtTickets other = (BoughtTickets) object;
        if ((this.bTicketid == null && other.bTicketid != null) || (this.bTicketid != null && !this.bTicketid.equals(other.bTicketid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BLL.BoughtTickets[ bTicketid=" + bTicketid + " ]";
    }
    
}
