/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
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
@Table(name = "Movie")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Movie.findAll", query = "SELECT m FROM Movie m"),
    @NamedQuery(name = "Movie.findByMovieid", query = "SELECT m FROM Movie m WHERE m.movieid = :movieid"),
    @NamedQuery(name = "Movie.findByMovieshowToSlide", query = "SELECT m FROM Movie m WHERE m.movieshowToSlide = :movieshowToSlide"),
    @NamedQuery(name = "Movie.findByMovieCinemaid", query = "SELECT m FROM Movie m WHERE m.movieCinemaid = :movieCinemaid"),
    @NamedQuery(name = "Movie.findByMoviename", query = "SELECT m FROM Movie m WHERE m.moviename = :moviename"),
    @NamedQuery(name = "Movie.findByMovieStatus", query = "SELECT m FROM Movie m WHERE m.movieStatus = :movieStatus"),
    @NamedQuery(name = "Movie.findByMovieduration", query = "SELECT m FROM Movie m WHERE m.movieduration = :movieduration"),
    @NamedQuery(name = "Movie.findByMoviecategory", query = "SELECT m FROM Movie m WHERE m.moviecategory LIKE :moviecategory"),
    @NamedQuery(name = "Movie.findByMovierating", query = "SELECT m FROM Movie m WHERE m.movierating = :movierating"),
    @NamedQuery(name = "Movie.findByMovieawards", query = "SELECT m FROM Movie m WHERE m.movieawards = :movieawards"),
    @NamedQuery(name = "Movie.findByMovieshowingFromDate", query = "SELECT m FROM Movie m WHERE m.movieshowingFromDate = :movieshowingFromDate"),
    @NamedQuery(name = "Movie.findByMovieshowingToDate", query = "SELECT m FROM Movie m WHERE m.movieshowingToDate = :movieshowingToDate"),
    @NamedQuery(name = "Movie.findByMovieicon", query = "SELECT m FROM Movie m WHERE m.movieicon = :movieicon"),
    @NamedQuery(name = "Movie.findByMovieimage", query = "SELECT m FROM Movie m WHERE m.movieimage = :movieimage"),
    @NamedQuery(name = "Movie.findAllByLocAndCategory", query = "SELECT m FROM Movie m WHERE m.movieCinemaid = :movieCinemaid AND m.moviecategory LIKE :moviecategory"),
    @NamedQuery(name = "Movie.findByMovietrailer", query = "SELECT m FROM Movie m WHERE m.movietrailer = :movietrailer")})
public class Movie extends RecursiveTreeObject<Movie> implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Movie_id")
    @GeneratedValue(generator = "InvSeq")
    @SequenceGenerator(name = "InvSeq", sequenceName="INV_SEQ", allocationSize = 1)
    private Integer movieid;
    @Column(name = "Movie_showToSlide")
    private Character movieshowToSlide;
    @Basic(optional = false)
    @Column(name = "Movie_name")
    private String moviename;
    @Column(name = "Movie_Status")
    private String movieStatus;
    @Basic(optional = false)
    @Column(name = "Movie_duration")
    private String movieduration;
    @Column(name = "Movie_category")
    private String moviecategory;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Movie_rating")
    private BigDecimal movierating;
    @Column(name = "Movie_awards")
    private String movieawards;
    @Basic(optional = false)
    @Column(name = "Movie_showingFromDate")
    @Temporal(TemporalType.DATE)
    private Date movieshowingFromDate;
    @Basic(optional = false)
    @Column(name = "Movie_showingToDate")
    @Temporal(TemporalType.DATE)
    private Date movieshowingToDate;
    @Column(name = "Movie_icon")
    private String movieicon;
    @Column(name = "Movie_image")
    private String movieimage;
    @Column(name = "Movie_trailer")
    private String movietrailer;
    @JoinColumn(name = "Movie_Cinema_id", referencedColumnName = "Cinema_id")
    @ManyToOne(optional = false)
    private Cinema movieCinemaid;

    public Movie() {
    }

    public Movie(Integer movieid) {
        this.movieid = movieid;
    }

    public Movie(Integer movieid, String moviename, String movieduration, Date movieshowingFromDate, Date movieshowingToDate) {
        this.movieid = movieid;
        this.moviename = moviename;
        this.movieduration = movieduration;
        this.movieshowingFromDate = movieshowingFromDate;
        this.movieshowingToDate = movieshowingToDate;
    }

    public Integer getMovieid() {
        return movieid;
    }

    public void setMovieid(Integer movieid) {
        this.movieid = movieid;
    }

    public Character getMovieshowToSlide() {
        return movieshowToSlide;
    }

    public void setMovieshowToSlide(Character movieshowToSlide) {
        this.movieshowToSlide = movieshowToSlide;
    }

    public String getMoviename() {
        return moviename;
    }

    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }

    public String getMovieStatus() {
        return movieStatus;
    }

    public void setMovieStatus(String movieStatus) {
        this.movieStatus = movieStatus;
    }

    public String getMovieduration() {
        return movieduration;
    }

    public void setMovieduration(String movieduration) {
        this.movieduration = movieduration;
    }

    public String getMoviecategory() {
        return moviecategory;
    }

    public void setMoviecategory(String moviecategory) {
        this.moviecategory = moviecategory;
    }

    public BigDecimal getMovierating() {
        return movierating;
    }

    public void setMovierating(BigDecimal movierating) {
        this.movierating = movierating;
    }

    public String getMovieawards() {
        return movieawards;
    }

    public void setMovieawards(String movieawards) {
        this.movieawards = movieawards;
    }

    public Date getMovieshowingFromDate() {
        return movieshowingFromDate;
    }

    public void setMovieshowingFromDate(Date movieshowingFromDate) {
        this.movieshowingFromDate = movieshowingFromDate;
    }

    public Date getMovieshowingToDate() {
        return movieshowingToDate;
    }

    public void setMovieshowingToDate(Date movieshowingToDate) {
        this.movieshowingToDate = movieshowingToDate;
    }

    public String getMovieicon() {
        return movieicon;
    }

    public void setMovieicon(String movieicon) {
        this.movieicon = movieicon;
    }

    public String getMovieimage() {
        return movieimage;
    }

    public void setMovieimage(String movieimage) {
        this.movieimage = movieimage;
    }

    public String getMovietrailer() {
        return movietrailer;
    }

    public void setMovietrailer(String movietrailer) {
        this.movietrailer = movietrailer;
    }

    public Cinema getMovieCinemaid() {
        return movieCinemaid;
    }

    public void setMovieCinemaid(Cinema movieCinemaid) {
        this.movieCinemaid = movieCinemaid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (movieid != null ? movieid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Movie)) {
            return false;
        }
        Movie other = (Movie) object;
        if ((this.movieid == null && other.movieid != null) || (this.movieid != null && !this.movieid.equals(other.movieid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return moviename;
    }
    
}
