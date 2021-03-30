/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "Invoices")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Invoices.findAll", query = "SELECT i FROM Invoices i"),
    @NamedQuery(name = "Invoices.findByInvoiceid", query = "SELECT i FROM Invoices i WHERE i.invoiceid = :invoiceid"),
    @NamedQuery(name = "Invoices.findByInvoicenumber", query = "SELECT i FROM Invoices i WHERE i.invoicenumber = :invoicenumber"),
    @NamedQuery(name = "Invoices.findByInvoicecreated", query = "SELECT i FROM Invoices i WHERE i.invoicecreated = :invoicecreated")})
public class Invoices implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Invoice_id")
    @GeneratedValue(generator = "InvSeq")
    @SequenceGenerator(name = "InvSeq", sequenceName="INV_SEQ", allocationSize = 1)
    private Integer invoiceid;
    @Basic(optional = false)
    @Column(name = "Invoice_number")
    private String invoicenumber;
    @Lob
    @Column(name = "Invoice_generate")
    private String invoicegenerate;
    @Column(name = "Invoice_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date invoicecreated;
    @JoinColumn(name = "Invoice_bTicket_id", referencedColumnName = "BTicket_id")
    @ManyToOne(optional = false)
    private BoughtTickets invoicebTicketid;

    public Invoices() {
    }

    public Invoices(Integer invoiceid) {
        this.invoiceid = invoiceid;
    }

    public Invoices(Integer invoiceid, String invoicenumber) {
        this.invoiceid = invoiceid;
        this.invoicenumber = invoicenumber;
    }

    public Integer getInvoiceid() {
        return invoiceid;
    }

    public void setInvoiceid(Integer invoiceid) {
        this.invoiceid = invoiceid;
    }

    public String getInvoicenumber() {
        return invoicenumber;
    }

    public void setInvoicenumber(String invoicenumber) {
        this.invoicenumber = invoicenumber;
    }

    public String getInvoicegenerate() {
        return invoicegenerate;
    }

    public void setInvoicegenerate(String invoicegenerate) {
        this.invoicegenerate = invoicegenerate;
    }

    public Date getInvoicecreated() {
        return invoicecreated;
    }

    public void setInvoicecreated(Date invoicecreated) {
        this.invoicecreated = invoicecreated;
    }

    public BoughtTickets getInvoicebTicketid() {
        return invoicebTicketid;
    }

    public void setInvoicebTicketid(BoughtTickets invoicebTicketid) {
        this.invoicebTicketid = invoicebTicketid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (invoiceid != null ? invoiceid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Invoices)) {
            return false;
        }
        Invoices other = (Invoices) object;
        if ((this.invoiceid == null && other.invoiceid != null) || (this.invoiceid != null && !this.invoiceid.equals(other.invoiceid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BLL.Invoices[ invoiceid=" + invoiceid + " ]";
    }
    
}
