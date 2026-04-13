package com.bro.jakartaeesimple.service;

import com.bro.jakartaeesimple.model.Users;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class UserService {

    @PersistenceContext(unitName = "my_persistence_unit") // Update with your actual PU name from persistence.xml
    private EntityManager em;

    public List<Users> findAll() {
        return em.createNamedQuery("Users.findAll", Users.class).getResultList();
    }

    public void save(Users user) {
        if (user.getUserId() == null) {
            // Set createdAt if it's a new record and not auto-handled by DB trigger
            user.setCreatedAt(new java.util.Date()); 
            em.persist(user);
        } else {
            em.merge(user);
        }
    }

    public void delete(Users user) {
        // Ensure the entity is managed before removing
        em.remove(em.contains(user) ? user : em.merge(user));
    }
}