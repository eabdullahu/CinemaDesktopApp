/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BLL.Hall;
import java.util.List;

/**
 *
 * @author HP
 */
public class HallRepository extends EntMngClass {
    public Hall findById(Hall id) throws CinemaException {
        try{
            return (Hall) em.createNamedQuery("Hall.findByHallid").setParameter("hallid", id).getResultList().stream().findFirst().orElse(null);
        }catch(Exception e){
            throw new CinemaException("Msg: "+e.getMessage());
        }
    }
    public void edit(Hall m) throws CinemaException {
        try{
            em.getTransaction().begin();
            em.merge(m);
            em.getTransaction().commit();
        }catch(Exception e){
            throw new CinemaException("Msg: "+e.getMessage());
        }
    }
    public List<Hall> findAll() throws CinemaException {

        try{
            return (List<Hall>) em.createNamedQuery("Hall.findAll").getResultList();
        }catch(Exception e){
            throw new CinemaException("Msg: "+e.getMessage());
        }
    }
    public void delete(Hall m) throws CinemaException {

        try{
            em.getTransaction().begin();
            Hall mStock2 = em.merge(m);
            em.remove(mStock2);
            em.getTransaction().commit();
            em.close();
        }catch(Exception e){
            throw new CinemaException("Msg: "+e.getMessage());
        }

    }
}