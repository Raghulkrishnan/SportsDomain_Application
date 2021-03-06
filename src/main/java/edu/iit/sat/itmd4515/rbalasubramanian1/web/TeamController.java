/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.rbalasubramanian1.web;

import edu.iit.sat.itmd4515.rbalasubramanian1.model.Level;
import edu.iit.sat.itmd4515.rbalasubramanian1.model.Team;
import edu.iit.sat.itmd4515.rbalasubramanian1.service.TeamService;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * used to handle creation of team obj
 * @author raghul
 */
@Named
@RequestScoped
public class TeamController {

    private static final Logger LOG = Logger.getLogger(TeamController.class.getName());
    
    private Team team;
    
    @EJB TeamService teamService;

    /**
     * default constructor
     */
    public TeamController() {
    }
    
    @PostConstruct
    private void postContruct(){
        LOG.info("Inside the TeamController.postConstruct method");
        team = new Team();
    }
    
    // action methods

    /**
     * used to save team to the coach
     * @return
     */
    public String saveTeam(){
        team.setLevel(Level.BEG); //because for a new team - level is  always BEG (BEGINNER)
        LOG.info("Inside saveTeam with the model: " + team.toString());
        
        teamService.create(team);
        
        return "/coach/welcome.xhtml";
    }
    
//    getters,setters

    /**
     * team obj getter
     * @return
     */
    public Team getTeam() {
        return team;
    }

    /**
     * team obj setter
     * @param team
     */
    public void setTeam(Team team) {
        this.team = team;
    }
}
