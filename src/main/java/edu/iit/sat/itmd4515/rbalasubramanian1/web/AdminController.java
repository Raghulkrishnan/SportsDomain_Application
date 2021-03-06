/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.rbalasubramanian1.web;

import edu.iit.sat.itmd4515.rbalasubramanian1.model.Coach;
import edu.iit.sat.itmd4515.rbalasubramanian1.model.Level;
import edu.iit.sat.itmd4515.rbalasubramanian1.model.Team;
import edu.iit.sat.itmd4515.rbalasubramanian1.model.Venue;
import edu.iit.sat.itmd4515.rbalasubramanian1.model.VenueOwner;
import edu.iit.sat.itmd4515.rbalasubramanian1.model.security.Group;
import edu.iit.sat.itmd4515.rbalasubramanian1.model.security.User;
import edu.iit.sat.itmd4515.rbalasubramanian1.service.CoachService;
import edu.iit.sat.itmd4515.rbalasubramanian1.service.GroupService;
import edu.iit.sat.itmd4515.rbalasubramanian1.service.TeamService;
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
 *admin controller handles admin related operations
 * @author raghul
 */
@Named
@RequestScoped
public class AdminController {

    private static final Logger LOG = Logger.getLogger(AdminController.class.getName());
    
    private VenueOwner owner;
    private Coach coach;
    private Team team; 
    private Venue venue;

    private User user;
    
    @Inject 
    @ManagedProperty(value = "#{param.id}")
    Long coachId;
    
    @Inject 
    @ManagedProperty(value = "#{param.id}")
    Long ownerId;

    @EJB CoachService coachServ;
    @EJB VenueOwnerService ownerServ;
    @EJB VenueService venueServ;
    @EJB UserService userServ;
    @EJB GroupService groupServ;
    @EJB TeamService teamServ;

    /**
     *default constructor
     */
    public AdminController() {
    }
   
//    initialization methods below

    /**
     *post construct method that initializes the page
     */
    @PostConstruct
    public void init(){
//        user = userServ.findByUsername(loginController.getUserName());
        LOG.info("admin controller.. post Construct");
        if (coachId == null) {
            coach = new Coach();
        } else{
          coach = coachServ.find(coachId);
        }
        
        if (ownerId == null) {
            owner = new VenueOwner();
        } else{
            LOG.info("========ownerID========" + ownerId);
            owner = ownerServ.find(ownerId);
        }
        
        user = new User();
        team = new Team();
        venue = new Venue();
    }
    
    /**
     *gets the coach object based on id
     */
    public void initCoachById(){
        coach = coachServ.find(this.coach.getId());
        LOG.info("coach...after find!!!" + this.coach.toString());
    }
    
    /**
     *gets the owner object based on id
     */
    public void initOwnerById(){
        owner = ownerServ.find(this.owner.getId());
        LOG.info("owner...after find!!!" + this.owner.toString());
    }
    
    /**
     *gets all coaches for the page
     * @return
     */
    public List<Coach> getAllCoaches(){
        List<Coach> allCoaches= new ArrayList<>();
        
        teamServ.findAll().forEach((t) ->{
           LOG.info("================All coaches of teams...--->> " + t.getCoach());
        });
        
        
        coachServ.findAll().forEach((c) -> {
            allCoaches.add(c);
            LOG.info("All teams of coaches...--->> " + c.getTeam());
        });
        
        return allCoaches;
    }
    
    /**
     *gets all venue owners for the page
     * @return
     */
    public List<VenueOwner> getAllOwners(){
        List<VenueOwner> allOwners= new ArrayList<>();
        
        ownerServ.findAll().forEach((o) -> {
            allOwners.add(o);
        });
        
        LOG.info("All owners...--->> " + allOwners);
        return allOwners;
    }

//    action methods

    /**
     * this function is called to add a new coach in the application
     * @return
     */
    public String addCoach(){
//        LOG.info("!!!!!!!!save coach result....." + this.coach.toString());
        user.setEnabled(true);
        
        groupServ.findAll().forEach((g) -> {
            if(g.getGroupName().equals("COACH_GROUP")){
                user.addGroup(g);
            }
        });
        userServ.create(user);
        coach.setUser(user);
        LOG.info("======================>>>>>>>>>>>>>>>>>>>>save coach result....." + this.coach.toString());
        coachServ.create(coach);
          
        return "/admin/welcome.xhtml?faces-redirect=true";
    }
    
    /**
     * used to add a team to the coach
     * @return
     */
    public String addTeamToCoach(){
////        need to implement 
        team.setLevel(Level.BEG);
        teamServ.create(team);
        
        teamServ.addCoachToTeam(team, coach);
        
        return "/admin/welcome.xhtml?faces-redirect=true";
    }
    
    /**
     * used to change coach object data
     * @return
     */
    public String editCoach(){
//        need to implement edit coach
        coachServ.editCoach(coach);
        
        return "/admin/welcome.xhtml?faces-redirect=true";
    }
    
    /**
     * removes coach data
     * @return
     */
    public String removeCoach(){
        if(coach.getTeam() != null){
            LOG.info("remove this coach......." + this.coach.toString());
//            teamServ.removeCoachFromTeam(coach.getTeam(), coach);
            teamServ.deleteTeam(coach.getTeam());
            coachServ.deleteCoach(coach);
            userServ.remove(this.coach.getUser());
        }
        else{
            LOG.info("removinggggggggggg coach......." + this.coach.toString());
            coachServ.deleteCoach(coach);
            userServ.remove(this.coach.getUser());
        }
        
        return "/admin/welcome.xhtml?faces-redirect=true";
    }
    
    
    /**
     * removes owner data
     * @return
     */
    public String removeOwner(){
        
        if(owner.getVenue()!= null){
            LOG.info("remove this owner......." + this.owner.toString());
            Venue v = owner.getVenue();
            
            venueServ.removeVenueFromGame(owner.getVenue(), v.getGames());
            
            venueServ.removeOwnerFromVenue(owner.getVenue(), owner);
            
            venueServ.deleteVenue(v);
            ownerServ.deleteOwner(owner);
            userServ.remove(this.owner.getUser());
        }
        else{
            LOG.info("removinggggggggggg owner......." + this.owner.toString());
            ownerServ.deleteOwner(owner);
            userServ.remove(this.owner.getUser());
        }
        
        return "/admin/welcome.xhtml?faces-redirect=true";
    }
    
    /**
     * adds owner data in the application
     * @return
     */
    public String addOwner(){
        user.setEnabled(true);
        
        groupServ.findAll().forEach((g) -> {
            if(g.getGroupName().equals("OWNER_GROUP")){
                user.addGroup(g);
            }
        });
        userServ.create(user);
        owner.setUser(user);
        ownerServ.create(owner);
        
        return "/admin/welcome.xhtml?faces-redirect=true";
    }
    
    /**
     *adds venue to owner
     * @return
     */
    public String addVenueToOwner(){
        
        LOG.info("============venue owner is============" + venue.getVenueOwner());
        LOG.info("============venue is============" + owner.getVenue());
        
        venueServ.create(venue);
        venueServ.addOwnerToVenue(venue, owner);
        
        return "/admin/welcome.xhtml?faces-redirect=true";
    }
    
    /**
     * used to edit owner data
     * @return
     */
    public String editOwner(){
//        need to implement edit owner
        ownerServ.editOwner(owner);
        
        return "/admin/welcome.xhtml?faces-redirect=true";
    }
    
//    accessors and mutators

    /**
     * owner getter
     * @return
     */
    public VenueOwner getOwner() {
        return owner;
    }

    /**
     * owner setter method
     * @param owner
     */
    public void setOwner(VenueOwner owner) {
        this.owner = owner;
    }

    /**
     * coach getter method
     * @return
     */
    public Coach getCoach() {
        return coach;
    }

    /**
     *
     * @param coach
     */
    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    /**
     * user getter method
     * @return
     */
    public User getUser() {
        return user;
    }

    /**
     * user setter method
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * team getter
     * @return
     */
    public Team getTeam() {
        return team;
    }

    /**
     * team setter
     * @param team
     */
    public void setTeam(Team team) {
        this.team = team;
    }

    /**
     * venue getter
     * @return
     */
    public Venue getVenue() {
        return venue;
    }

    /**
     * venue setter
     * @param venue
     */
    public void setVenue(Venue venue) {
        this.venue = venue;
    }
}
