/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BLL.Invoices;

/**
 *
 * @author HP
 */
public class InvoicesRepository extends EntMngClass {
    public void create(Invoices inv) throws CinemaException {
        try{
            em.getTransaction().begin();
            em.persist(inv);
            em.getTransaction().commit();
        }catch(Exception e){
            throw new CinemaException("Msg: "+e.getMessage());
        }
    }
}
