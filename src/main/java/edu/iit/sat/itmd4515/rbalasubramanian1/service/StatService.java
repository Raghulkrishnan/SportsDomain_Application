/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.rbalasubramanian1.service;

import edu.iit.sat.itmd4515.rbalasubramanian1.model.Stat;
import edu.iit.sat.itmd4515.rbalasubramanian1.model.Venue;
import edu.iit.sat.itmd4515.rbalasubramanian1.model.VenueOwner;
import java.util.List;
import javax.ejb.Stateless;

/**
 *stat service class
 * @author raghul
 */
@Stateless
public class StatService extends AbstractService<Stat> {

    /**
     *default constructor
     */
    public StatService() {
        super(Stat.class);
    }

    /**
     *used to get all stats
     * @return
     */
    @Override
    public List<Stat> findAll() {
        return em.createNamedQuery("Stat.findAll", entityClass).getResultList();
    }
    
}
