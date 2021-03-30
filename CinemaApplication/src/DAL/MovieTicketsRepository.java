/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BLL.Hall;
import BLL.MovieTickets;
import BLL.Movie;
import java.util.List;

/**
 *
 * @author HP
 */
public class MovieTicketsRepository extends EntMngClass {
    public List<MovieTickets> findAll() throws CinemaException {
        try{
            return em.createNamedQuery("MovieTickets.findAll").getResultList();
        }catch(Exception e){
            throw new CinemaException("Msg: "+e.getMessage());
        }
    }
    
    public void edit(MovieTickets m) throws CinemaException {
       try{
            em.getTransaction().begin();
            em.merge(m);
            em.getTransaction().commit();
        }catch(Exception e){
            throw new CinemaException("Msg: "+e.getMessage());
        }
    }

    
    public List<MovieTickets> findByMovieId(Movie movie) throws CinemaException {
        try{
            return em.createNamedQuery("MovieTickets.findByMovieid")
                    .setParameter("movieid", movie)
                    .getResultList();
        }catch(Exception e){
            throw new CinemaException("Msg: "+e.getMessage());
        }
    }

    public List<MovieTickets> findByMovieIdAndHallId(Movie movie, Hall id) throws CinemaException {
        try{
            return em.createNamedQuery("MovieTickets.findByMovieIdAndHallId")
                    .setParameter("movieid", movie)
                    .setParameter("tickethallid", id)
                    .getResultList();
        }catch(Exception e){
            throw new CinemaException("Msg: "+e.getMessage());
        }
    }

    public List<Hall> getMoviePlayHalls(Movie movie) throws CinemaException {
        try{
            return em.createNamedQuery("MovieTickets.findByHall")
                    .setParameter("movieid", movie)
                    .getResultList();
        }catch(Exception e){
            throw new CinemaException("Msg: "+e.getMessage());
        }
    }
    
    public MovieTickets findById(Integer id) throws CinemaException {
        try{
            return (MovieTickets) em.createNamedQuery("MovieTickets.findByTicketid").setParameter("ticketid", id).getSingleResult();
        }catch(Exception e){
            throw new CinemaException("Msg: "+e.getMessage());
        }
    }
    
    public void delete(MovieTickets m) throws CinemaException {
        try{
            em.getTransaction().begin();
            MovieTickets mStock2 = em.merge(m);
            em.remove(mStock2);
            em.getTransaction().commit();
            em.close();
        }catch(Exception e){
            throw new CinemaException("Msg: "+e.getMessage());
        }
    }
}
