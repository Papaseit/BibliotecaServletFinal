package org.example.crudbasicoservlets.modelo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class LibroDAO {

    private EntityManager em;
    private EntityManagerFactory emf;

    public LibroDAO() {
        emf = Persistence.createEntityManagerFactory("biblioteca");
        em = emf.createEntityManager();
    }

    public boolean addLibro(Libro libro) {
        try {
            em.getTransaction().begin();
            em.persist(libro);
            em.getTransaction().commit();
            return true;
        }catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    public Libro getLibroById(String isbn) {
        return em.find(Libro.class, isbn);
    }

    public List<Libro> getAllLibros() {
        TypedQuery<Libro> query = em.createQuery("SELECT l FROM Libro l", Libro.class);
        return query.getResultList();
    }

    public boolean updateLibro(Libro libro) {
        try {
            em.getTransaction().begin();
            em.merge(libro);
            em.getTransaction().commit();
            return true;
        }catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Libro libro) {
        try {
            em.getTransaction().begin();
            em.remove(em.contains(libro) ? libro : em.merge(libro));
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

    public void close(){
        em.close();
        emf.close();
    }
}
