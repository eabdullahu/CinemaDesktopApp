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
@Table(name = "Consumator")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Consumator.findAll", query = "SELECT c FROM Consumator c"),
    @NamedQuery(name = "Consumator.findByClientid", query = "SELECT c FROM Consumator c WHERE c.clientid = :clientid"),
    @NamedQuery(name = "Consumator.findByClientrole", query = "SELECT c FROM Consumator c WHERE c.clientrole = :clientrole"),
    @NamedQuery(name = "Consumator.findByClientname", query = "SELECT c FROM Consumator c WHERE c.clientname = :clientname"),
    @NamedQuery(name = "Consumator.findByClientsurname", query = "SELECT c FROM Consumator c WHERE c.clientsurname = :clientsurname"),
    @NamedQuery(name = "Consumator.findByClientaddres", query = "SELECT c FROM Consumator c WHERE c.clientaddres = :clientaddres"),
    @NamedQuery(name = "Consumator.findByClientemail", query = "SELECT c FROM Consumator c WHERE c.clientemail = :clientemail"),
    @NamedQuery(name = "Consumator.findByClientpassword", query = "SELECT c FROM Consumator c WHERE c.clientpassword = :clientpassword"),
    @NamedQuery(name = "Consumator.findByClientemailAndPassword", query = "SELECT c FROM Consumator c WHERE c.clientemail = :clientemail AND c.clientpassword = :clientpassword"),
    @NamedQuery(name = "Consumator.findByClientphone", query = "SELECT c FROM Consumator c WHERE c.clientphone = :clientphone")})
public class Consumator extends RecursiveTreeObject<Consumator> implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Client_id")
    @GeneratedValue(generator = "InvSeq")
    @SequenceGenerator(name = "InvSeq", sequenceName="INV_SEQ", allocationSize = 1)
    private Integer clientid;
    @Column(name = "Client_role")
    private Character clientrole;
    @Basic(optional = false)
    @Column(name = "Client_name")
    private String clientname;
    @Basic(optional = false)
    @Column(name = "Client_surname")
    private String clientsurname;
    @Column(name = "Client_addres")
    private String clientaddres;
    @Basic(optional = false)
    @Column(name = "Client_email")
    private String clientemail;
    @Basic(optional = false)
    @Column(name = "Client_password")
    private String clientpassword;
    @Column(name = "Client_phone")
    private String clientphone;
    public Consumator() {
    }

    public Consumator(Integer clientid) {
        this.clientid = clientid;
    }

    public Consumator(Integer clientid, String clientname, String clientsurname, String clientemail, String clientpassword) {
        this.clientid = clientid;
        this.clientname = clientname;
        this.clientsurname = clientsurname;
        this.clientemail = clientemail;
        this.clientpassword = clientpassword;
    }

    public Integer getClientid() {
        return clientid;
    }

    public void setClientid(Integer clientid) {
        this.clientid = clientid;
    }

    public Character getClientrole() {
        return clientrole;
    }

    public void setClientrole(Character clientrole) {
        this.clientrole = clientrole;
    }

    public String getClientname() {
        return clientname;
    }

    public void setClientname(String clientname) {
        this.clientname = clientname;
    }

    public String getClientsurname() {
        return clientsurname;
    }

    public void setClientsurname(String clientsurname) {
        this.clientsurname = clientsurname;
    }

    public String getClientaddres() {
        return clientaddres;
    }

    public void setClientaddres(String clientaddres) {
        this.clientaddres = clientaddres;
    }

    public String getClientemail() {
        return clientemail;
    }

    public void setClientemail(String clientemail) {
        this.clientemail = clientemail;
    }

    public String getClientpassword() {
        return clientpassword;
    }

    public void setClientpassword(String clientpassword) {
        this.clientpassword = clientpassword;
    }

    public String getClientphone() {
        return clientphone;
    }

    public void setClientphone(String clientphone) {
        this.clientphone = clientphone;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clientid != null ? clientid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Consumator)) {
            return false;
        }
        Consumator other = (Consumator) object;
        if ((this.clientid == null && other.clientid != null) || (this.clientid != null && !this.clientid.equals(other.clientid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BLL.Consumator[ clientid=" + clientid + " ]";
    }
    
}
