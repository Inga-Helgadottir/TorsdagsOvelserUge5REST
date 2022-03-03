/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Person;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author tha
 */
public class Populator {
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        PersonFacade fe = PersonFacade.getFacadeExample(emf);
        PersonFacade facade = new PersonFacade();

        facade.addPerson("Lilly", "Carter", "2134564");
        facade.addPerson("Missy", "Marks", "2152545");
        facade.addPerson("Mary", "Cutler", "6548544");
        facade.addPerson("Hannah", "Parker", "8457954");
    }
    
    public static void main(String[] args) {
       populate();
    }
}
