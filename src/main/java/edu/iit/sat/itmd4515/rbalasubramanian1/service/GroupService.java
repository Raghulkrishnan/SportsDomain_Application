/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.rbalasubramanian1.service;

import edu.iit.sat.itmd4515.rbalasubramanian1.model.security.Group;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Named;

/**
 * Group service class
 * @author raghul
 */
@Named
@Stateless
public class GroupService extends AbstractService<Group> {

    /**
     *
     */
    public GroupService() {
        super(Group.class);
    }

    /**
     *findAll returns all groups available in the application for the user.
     * @return
     */
    @Override
    public List<Group> findAll() {
        return em.createNamedQuery("Group.findAll", entityClass).getResultList();
    }
    
}
