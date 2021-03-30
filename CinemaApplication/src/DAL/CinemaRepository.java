/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BLL.Cinema;
import java.util.List;

/**
 *
 * @author HP
 */
public class CinemaRepository extends EntMngClass {
    public void create(Cinema  c) throws CinemaException {
        try{
            em.getTransaction().begin();
            em.persist(c);
            em.getTransaction().commit();
        }catch(Exception e){
            throw new CinemaException("Msg: "+e.getMessage());
        }
    }

    public void edit(Cinema c) throws CinemaException {
       try{
            em.getTransaction().begin();
            em.merge(c);
            em.getTransaction().commit();
        }catch(Exception e){
            throw new CinemaException("Msg: "+e.getMessage());
        }
    }

    public void delete(Cinema c) throws CinemaException {
        try{
            em.getTransaction().begin();
            Cinema mStock2 = em.merge(c);
            em.remove(mStock2);
            em.getTransaction().commit();
            em.close();
        }catch(Exception e){
            throw new CinemaException("Msg: "+e.getMessage());
        }
    }
    
    public List<Cinema> findAll() throws CinemaException {
        try{
            return em.createNamedQuery("Cinema.findAll").getResultList();
        }catch(Exception e){
            throw new CinemaException("Msg: "+e.getMessage());
        }
    }

    public Cinema findById(Integer id) throws CinemaException {
        try{
            return (Cinema) em.createNamedQuery("Cinema.findByCinemaid").setParameter("cinemaid", id).getResultList().stream().findFirst().orElse(null);
        }catch(Exception e){
            throw new CinemaException("Msg: "+e.getMessage());
        }
    }
}
