/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.rbalasubramanian1.web;

import edu.iit.sat.itmd4515.rbalasubramanian1.model.Game;
import edu.iit.sat.itmd4515.rbalasubramanian1.model.Team;
import edu.iit.sat.itmd4515.rbalasubramanian1.service.GameService;
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
 * handles the game result from the owner side
 * @author raghul
 */
@Named
@RequestScoped
public class OwnerGameResultController {

    private static final Logger LOG = Logger.getLogger(OwnerGameResultController.class.getName());
    
    private Game game;
    private List<Team> teamsInGame;
    
    @Inject 
    @ManagedProperty(value = "#{param.id}")
    Long gameId;

    @EJB GameService gameServ;

    /**
     *default constructor
     */
    public OwnerGameResultController() {
    }
   
//    initialization methods below

    /**
     * post construct method getting the game object
     */
    @PostConstruct
    public void init(){
        LOG.info("OwnerGameResultController.. post Construct");
        if (gameId == null) {
            game = new Game();
        } else{
          game = gameServ.find(gameId);
        }
//        game = new Game();
    }
    
    /**
     * get the teams in the game based on game id
     */
    public void initGameById(){
        teamsInGame = new ArrayList<>();
        
        LOG.info("init game by id..." + this.game.getId());
        game = gameServ.find(this.game.getId());
        
        game.getTeams().forEach((t) -> {
            teamsInGame.add(t);
        });
        
        LOG.info("init game by id...after find!!!" + this.game.toString());
    }

//    action methods

    /**
     * to save result of the game
     * @return
     */
    public String saveGameResult(){
        LOG.info("!!!!!!!!save game result....." + this.game.toString());
//        need to implement edit game
        gameServ.addResultToGame(game);
        
        return "/owner/welcome.xhtml?faces-redirect=true";
    }
    
    /**
     * to remove the game
     * @return
     */
    public String removeGame(){
        LOG.info("remove this game......." + this.game.toString());
//        need to implement delete game
        
        return "/owner/welcome.xhtml";
    }
    
    
//    accessors and mutators

    /**
     * to get the game
     * @return
     */
    public Game getGame() {
        return game;
    }

    /**
     * to set the game
     * @param game
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * gets the teams list of the game
     * @return
     */
    public List<Team> getTeamsInGame() {
        return teamsInGame;
    }

    /**
     * set teams to game
     * @param teamsInGame
     */
    public void setTeamsInGame(List<Team> teamsInGame) {
        this.teamsInGame = teamsInGame;
    }
}
