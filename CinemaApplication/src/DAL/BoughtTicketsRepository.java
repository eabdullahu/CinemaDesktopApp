/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BLL.BoughtTickets;
import BLL.Consumator;
import BLL.Movie;
import BLL.MovieTickets;
import java.util.List;
import java.util.*;

/**
 *
 * @author HP
 */
public class BoughtTicketsRepository extends EntMngClass {
    public void create(BoughtTickets b) throws CinemaException {
        try{
            em.getTransaction().begin();
            em.persist(b);
            em.getTransaction().commit();
        }catch(Exception e){
            throw new CinemaException("Msg: "+e.getMessage());
        }
    }
    
    public List<BoughtTickets> findByMovieIdAndTicketid(Movie movie, MovieTickets ticket) throws CinemaException {
        try{
            return em.createNamedQuery("BoughtTickets.findByMovieIdAndTicketId")
                    .setParameter("bTicketmovieid", movie)
                    .setParameter("bTicketmovieticketid", ticket)
                    .getResultList();
        }catch(Exception e){
            throw new CinemaException("Msg: "+e.getMessage());
        }
    }
    
    public List<BoughtTickets> findByClient(Consumator c) throws CinemaException {
        try{
            return em.createNamedQuery("BoughtTickets.findByClient")
                    .setParameter("bTicketclientid", c)
                    .getResultList();
        }catch(Exception e){
            throw new CinemaException("Msg: "+e.getMessage());
        }
    }

    public List sold() throws CinemaException {
        try{
            return em.createNativeQuery("SELECT COUNT(*) as count, m.Movie_name FROM defaultdb.BoughtTickets bt " +
                    "LEFT JOIN defaultdb.Movie m ON (bt.BTicket_movie_id = m.Movie_id) GROUP BY BTicket_movie_id").getResultList();

        }catch(Exception e){
            throw new CinemaException("Msg: "+e.getMessage());
        }
    }
    public Long countSold() throws CinemaException {
        try{
            return (Long) em.createNativeQuery("SELECT COUNT(*)  FROM defaultdb.BoughtTickets bt " +
                    "LEFT JOIN defaultdb.Movie m ON (bt.BTicket_movie_id = m.Movie_id)").getSingleResult();

        }catch(Exception e){
            throw new CinemaException("Msg: "+e.getMessage());
        }
    }

    public List soldPrice() throws CinemaException {
        try{
            return em.createNativeQuery("SELECT SUM(BTicket_Price), date(BTicket_date) FROM BoughtTickets " +
                    "WHERE BTicket_date >= DATE(NOW()) - INTERVAL 10 DAY GROUP BY date(BTicket_date);").getResultList();

        }catch(Exception e){
            throw new CinemaException("Msg: "+e.getMessage());
        }
    }


}
