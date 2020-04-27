/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.rbalasubramanian1.service;

import edu.iit.sat.itmd4515.rbalasubramanian1.model.Coach;
import edu.iit.sat.itmd4515.rbalasubramanian1.model.Venue;
import edu.iit.sat.itmd4515.rbalasubramanian1.model.VenueOwner;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author raghul
 */
@Stateless
public class VenueOwnerService extends AbstractService<VenueOwner> {

    private static final Logger LOG = Logger.getLogger(VenueOwnerService.class.getName());

    /**
     *
     */
    public VenueOwnerService() {
        super(VenueOwner.class);
    }

    /**
     *
     * @return
     */
    @Override
    public List<VenueOwner> findAll() {
        return em.createNamedQuery("VenueOwner.findAll", entityClass).getResultList();
    }
    
    /**
     *
     * @param username
     * @return
     */
    public VenueOwner findByUsername(String username){
        return em.createNamedQuery("VenueOwner.findByUserName", VenueOwner.class)
                .setParameter("userName", username)
                .getSingleResult();
    }
    
    public void editVenueInfo(VenueOwner vo) {
        VenueOwner currentRowFromDatabase = em.find(VenueOwner.class, vo.getId());
        Venue rowFromDB = em.find(Venue.class, vo.getVenue().getId());
        LOG.info("venuowner service is..." + vo.getVenue());
        currentRowFromDatabase.setFirstName(vo.getFirstName());
        currentRowFromDatabase.setLastName(vo.getLastName());
        
        rowFromDB.setVenueName(vo.getVenue().getVenueName());
        
        
        em.merge(currentRowFromDatabase);
        em.merge(rowFromDB);
    }
    
    
}
