/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BLL.Consumator;
import java.util.List;

/**
 *
 * @author HP
 */
public class ConsumatorRepository extends EntMngClass {
    public void create(Consumator c) throws CinemaException {
        try{
            em.getTransaction().begin();
            em.persist(c);
            em.getTransaction().commit();
        }catch(Exception e){
            throw new CinemaException("Msg: "+e.getMessage());
        }
    }

    public void edit(Consumator c) throws CinemaException {
        try {
            em.getTransaction().begin();
            em.merge(c);
            em.getTransaction().commit();
        } catch(Exception e){
            throw new CinemaException("Msg: "+e.getMessage());
        }
    }

    public void delete(Consumator c) throws CinemaException {
        try{
            em.getTransaction().begin();
            em.remove(c);
            em.getTransaction().commit();
        }catch(Exception e){
            throw new CinemaException("Msg: "+e.getMessage());
        }
    }

    public Consumator findById(Integer id) throws CinemaException {
        try{
            return (Consumator) em.createNamedQuery("Consumator.findByClientid").setParameter("clientid", id).getResultList().stream().findFirst().orElse(null);
        }catch(Exception e){
            throw new CinemaException("Msg: "+e.getMessage());
        }
    }
    
    public Consumator findByClientEmail(String email) throws CinemaException {
        try{
            return (Consumator) em.createNamedQuery("Consumator.findByClientemail").setParameter("clientemail", email).getResultList().stream().findFirst().orElse(null);
        }catch(Exception e){
            throw new CinemaException("Msg: "+e.getMessage());
        }
    }
    
    public Consumator findByUsernameAndPassword(String username, String password) throws CinemaException {
        try{
            return (Consumator) em.createNamedQuery("Consumator.findByClientemailAndPassword").
                    setParameter("clientemail", username).
                    setParameter("clientpassword", password).
                    getResultList().stream().findFirst().orElse(null);
        }catch(Exception e){
            throw new CinemaException("Msg: "+e.getMessage());
        }
    }
    
    public Consumator findByEmail(String email) throws CinemaException {
        try{
            return (Consumator) em.createNamedQuery("Consumator.findByClientemail").
                    setParameter("clientemail", email).getResultList().stream().findFirst().orElse(null);
        }catch(Exception e){
            throw new CinemaException("Msg: "+e.getMessage());
        }
    }
    
    public List<Consumator> findAll() throws CinemaException {
        try{
            return em.createNamedQuery("Consumator.findAll").getResultList();
        }catch(Exception e){
            throw new CinemaException("Msg: "+e.getMessage());
        }
    }
    
    public List<Consumator> findByRole(char id) throws CinemaException {
        try{
            return em.createNamedQuery("Consumator.findByClientrole").setParameter("clientrole", id).getResultList();
        }catch(Exception e){
            throw new CinemaException("Msg: "+e.getMessage());
        }
    }
}
