package com.bro.jakartaeesimple.service;

import com.bro.jakartaeesimple.model.Users;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.security.enterprise.identitystore.PasswordHash;
import java.util.List;

@Stateless
public class UserService {

    @PersistenceContext(unitName = "my_persistence_unit") // Update with your actual PU name from persistence.xml
    private EntityManager em;
    @Inject
    private PasswordHash passwordHash; // Injects the PBKDF2 hasher

    public List<Users> findAll() {
        return em.createNamedQuery("Users.findAll", Users.class).getResultList();
    }

    public Users findByEmail(String email) {
        List<Users> results = em.createNamedQuery("Users.findByEmail", Users.class)
                .setParameter("email", email) // Don't forget to bind the parameter!
                .getResultList();

        return results.isEmpty() ? null : results.get(0);
    }

    public void save(Users user) {
        if (user.getUserId() == null) {
            String encodedPassword = passwordHash.generate(user.getPasswordHash().toCharArray());
            user.setPasswordHash(encodedPassword);
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
