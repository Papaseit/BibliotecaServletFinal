package org.example.crudbasicoservlets.modelo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

public class PrestamoDAO {
    private EntityManagerFactory emf;
    private EntityManager em;

    public PrestamoDAO() {
        emf = Persistence.createEntityManagerFactory("biblioteca");
        em = emf.createEntityManager();
    }

    public boolean add(Prestamo prestamo) {
        try {
            em.getTransaction().begin();
            em.persist(prestamo);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    public Prestamo getById(int id) {
        return em.find(Prestamo.class, id);
    }

    public List<Prestamo> getAll() {
        TypedQuery<Prestamo> query = em.createQuery("SELECT p FROM Prestamo p", Prestamo.class);
        return query.getResultList();
    }

    public boolean update(Prestamo prestamo) {
        try {
            em.getTransaction().begin();
            em.merge(prestamo);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Prestamo prestamo) {
        try {
            em.getTransaction().begin();
            em.remove(em.contains(prestamo) ? prestamo : em.merge(prestamo));
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    public boolean estaPrestadoEnFecha(int ejemplarId, LocalDate fechaInicio, LocalDate fechaDevolucion) {
        String jpql = "SELECT COUNT(p) FROM Prestamo p WHERE p.ejemplar.id = :ejemplarId " +
                "AND p.fechaInicio < :fechaDevolucion " +
                "AND p.fechaDevolucion > :fechaInicio";
        TypedQuery<Long> query = em.createQuery(jpql, Long.class);
        query.setParameter("ejemplarId", ejemplarId);
        query.setParameter("fechaInicio", fechaInicio);
        query.setParameter("fechaDevolucion", fechaDevolucion);
        return query.getSingleResult() > 0;
    }

    // Contar los préstamos activos de un usuario (solo préstamos cuya fecha de devolución sea posterior a la fecha actual)
    public int contarPrestamosActivos(int usuarioId) {
        String jpql = "SELECT COUNT(p) FROM Prestamo p WHERE p.usuario.id = :usuarioId " +
                "AND p.fechaDevolucion > :fechaActual";
        TypedQuery<Long> query = em.createQuery(jpql, Long.class);
        query.setParameter("usuarioId", usuarioId);
        query.setParameter("fechaActual", LocalDate.now());
        return query.getSingleResult().intValue();
    }

    public void close() {
        if (em.isOpen()) {
            em.close();
        }
        if (emf.isOpen()) {
            emf.close();
        }
    }
}
