/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.rbalasubramanian1.web;

import edu.iit.sat.itmd4515.rbalasubramanian1.model.Game;
import edu.iit.sat.itmd4515.rbalasubramanian1.model.Team;
import edu.iit.sat.itmd4515.rbalasubramanian1.model.Venue;
import edu.iit.sat.itmd4515.rbalasubramanian1.model.VenueOwner;
import edu.iit.sat.itmd4515.rbalasubramanian1.model.security.User;
import edu.iit.sat.itmd4515.rbalasubramanian1.service.GameService;
import edu.iit.sat.itmd4515.rbalasubramanian1.service.GroupService;
import edu.iit.sat.itmd4515.rbalasubramanian1.service.UserService;
import edu.iit.sat.itmd4515.rbalasubramanian1.service.VenueOwnerService;
import edu.iit.sat.itmd4515.rbalasubramanian1.service.VenueService;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.ManagedProperty;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * used to handle venue info requests
 * @author raghul
 */
@Named
@RequestScoped
public class OwnerVenueController {

    private static final Logger LOG = Logger.getLogger(OwnerVenueController.class.getName());
    
    private Venue venue;
    private VenueOwner owner;
    private User user;
    
    @Inject 
    @ManagedProperty(value = "#{param.id}")
    Long ownerId;

    @EJB VenueService venueServ;
    @EJB VenueOwnerService ownerServ;
    @EJB GroupService groupServ;
    @EJB UserService userServ;
    
    @Inject LoginController loginController;

    /**
     *default constructor
     */
    public OwnerVenueController() {
    }
   
//    initialization methods below

    /**
     * used to get owner and user obj
     */
    @PostConstruct
    public void init(){
        LOG.info("OwnerVenueController.. post Construct");
        if (ownerId == null) {
            owner = new VenueOwner();
        } else{
          owner = ownerServ.find(ownerId);
        }
        owner = ownerServ.findByUsername(loginController.getUserName());
        user = new User();
    }

//    action methods

    /**
     * used to edit venue of owner
     * @return
     */
    public String editVenueInfo(){
        LOG.info("!!!!!!!!edit....." + owner.getVenue());
//        need to implement edit game
        ownerServ.editVenueInfo(owner);
        
        return "/owner/welcome.xhtml?faces-redirect=true";
    }
    
    /**
     * used to change owner password
     * @return
     */
    public String changePwd(){
        user.setEnabled(true);
//        user.setUserName(owner.getUser().getUserName());
        
        ownerServ.editOwnerPwd(user, owner);
          
        return "/owner/welcome.xhtml?faces-redirect=true";
    }
    
//    accessors and mutators

    /**
     * used to get venue
     * @return
     */
    
    public Venue getVenue() {
        return venue;
    }

    /**
     * used to set venue
     * @param venue
     */
    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    /**
     * owner getter
     * @return
     */
    public VenueOwner getOwner() {
        return owner;
    }

    /**
     * owner setter
     * @param owner
     */
    public void setOwner(VenueOwner owner) {
        this.owner = owner;
    }

    /**
     * user getter
     * @return
     */
    public User getUser() {
        return user;
    }

    /**
     * user setter
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }
}
