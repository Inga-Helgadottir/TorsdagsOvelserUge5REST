package facades;

import dtos.PersonDTO;
import entities.Person;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class PersonFacade implements IPersonFacade{

    private static PersonFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    public PersonFacade() {}
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
        PersonFacade fe = getFacadeExample(emf);
    }

    @Override
    public PersonDTO addPerson(String fName, String lName, String phone) {
        Person rme = new Person(fName, lName, phone);
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(rme);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(rme);
    }

    @Override
    public Person deletePerson(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            Person p = em.find(Person.class, id);
            em.getTransaction().begin();
            if(p != null){
                em.remove(p);
                em.getTransaction().commit();
            } else {
                return null;
            }
            return p;
        } finally {
            em.close();
        }
    }

    @Override
    public PersonDTO getPerson(int id) {
        EntityManager em = emf.createEntityManager();
        Person rm = em.find(Person.class, id);
        if (rm == null)
            System.out.println("The Person entity with ID: " + id + " Was not found");
        return new PersonDTO(rm);
    }

    @Override
    public List<PersonDTO> getAllPersons() {
        EntityManager em = emf.createEntityManager();
        try{
            TypedQuery query = em.createQuery("select p from Person p", Person.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public PersonDTO editPerson(PersonDTO person) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            PersonDTO p = em.find(PersonDTO.class, person.getId());
            person.setFirstName(p.getFirstName());
            person.setLastName(p.getLastName());
            em.getTransaction().commit();
            return p;
        } finally {
            em.close();
        }
    }
}
