/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BLL.Cinema;
import BLL.Movie;
import java.util.List;

public class MovieRepository extends EntMngClass{
    public void create(Movie  m) throws CinemaException {
        try{
            em.getTransaction().begin();
            em.persist(m);
            em.getTransaction().commit();
        }catch(Exception e){
            throw new CinemaException("Msg: "+e.getMessage());
        }
    }

    public void edit(Movie m) throws CinemaException {
       try{
            em.getTransaction().begin();
            em.merge(m);
            em.getTransaction().commit();
        }catch(Exception e){
            throw new CinemaException("Msg: "+e.getMessage());
        }
    }

    public void delete(Movie m) throws CinemaException {
        try{
            em.getTransaction().begin();
            Movie mStock2 = em.merge(m);
            em.remove(mStock2);
            em.getTransaction().commit();
            em.close();
        }catch(Exception e){
            throw new CinemaException("Msg: "+e.getMessage());
        }
    }
    
    public Integer countAll() throws CinemaException {
        try{
            return em.createNamedQuery("Movie.findAll").getResultList().size();
        }catch(Exception e){
            throw new CinemaException("Msg: "+e.getMessage());
        }
    }
    
    public List<Movie> findAll(Integer start, Integer finish) throws CinemaException {
        try{
            return em.createNamedQuery("Movie.findAll")
                    .setFirstResult(start)
                    .setMaxResults(finish)
                    .getResultList();
        }catch(Exception e){
            throw new CinemaException("Msg: "+e.getMessage());
        }
    }
    
    public List<Movie> findAll() throws CinemaException {

        try{
            return em.createNamedQuery("Movie.findAll").getResultList();
        }catch(Exception e){
            throw new CinemaException("Msg: "+e.getMessage());
        }
    }
    
    public List<Movie> findAllByLocAndCategory(Cinema cinemaId, String category, Integer start, Integer finish) throws CinemaException {
        try{
            if(cinemaId != null && !category.equals("All categories")){
                return em.createNamedQuery("Movie.findAllByLocAndCategory")
                        .setParameter("movieCinemaid", cinemaId)
                        .setParameter("moviecategory", "%"+category+"%")
                        .setFirstResult(start)
                        .setMaxResults(finish)
                        .getResultList();
            }else if(cinemaId != null && category.equals("All categories")){
                return em.createNamedQuery("Movie.findByMovieCinemaid")
                        .setParameter("movieCinemaid", cinemaId)
                        .setFirstResult(start)
                        .setMaxResults(finish)
                        .getResultList();
            }else if(cinemaId == null && !category.equals("All categories")){
                return em.createNamedQuery("Movie.findByMoviecategory")
                        .setParameter("moviecategory", "%"+category+"%")
                        .setFirstResult(start)
                        .setMaxResults(finish)
                        .getResultList();
            }else{
                return em.createNamedQuery("Movie.findAll")
                        .setFirstResult(start)
                        .setMaxResults(finish)
                        .getResultList();
            }
        }catch(Exception e){
            throw new CinemaException("Msg: "+e.getMessage());
        }
    }
    
    public Integer countAllByLocAndCategory(Cinema cinemaId, String category) throws CinemaException {
        try{
            if(cinemaId != null && !category.equals("All categories")){
                return em.createNamedQuery("Movie.findAllByLocAndCategory")
                        .setParameter("movieCinemaid", cinemaId)
                        .setParameter("moviecategory", "%"+category+"%")
                        .getResultList().size();
            }else if(cinemaId != null && category.equals("All categories")){
                return em.createNamedQuery("Movie.findByMovieCinemaid")
                        .setParameter("movieCinemaid", cinemaId)
                        .getResultList().size();
            }else if(cinemaId == null && !category.equals("All categories")){
                return em.createNamedQuery("Movie.findByMoviecategory")
                        .setParameter("moviecategory", "%"+category+"%")
                        .getResultList().size();
            }else{
                return em.createNamedQuery("Movie.findAll").getResultList().size();
            }
        }catch(Exception e){
            throw new CinemaException("Msg: "+e.getMessage());
        }
    }

    public List<Movie> showToSlideShow(char val) throws CinemaException {
        try{
            return em.createNamedQuery("Movie.findByMovieshowToSlide").setParameter("movieshowToSlide", val).getResultList();
        }catch(Exception e){
            throw new CinemaException("Msg: "+e.getMessage());
        }
    }
    
    public Movie findById(Integer id) throws CinemaException {
        try{
            return (Movie) em.createNamedQuery("Movie.findByMovieid").setParameter("movieid", id).getResultList().stream().findFirst().orElse(null);
        }catch(Exception e){
            throw new CinemaException("Msg: "+e.getMessage());
        }
    }
}