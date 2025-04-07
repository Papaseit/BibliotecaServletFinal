package org.example.crudbasicoservlets.Servicio;

import org.example.crudbasicoservlets.modelo.Prestamo;
import org.example.crudbasicoservlets.modelo.PrestamoDAO;
import org.example.crudbasicoservlets.modelo.UsuarioDAO;

import java.time.LocalDate;
import java.util.List;

public class PrestamoService {
    private PrestamoDAO prestamoDAO;
    private UsuarioDAO usuarioDAO;

    public PrestamoService(){
        prestamoDAO = new PrestamoDAO();
        usuarioDAO = new UsuarioDAO();
    }

    public List<Prestamo> getAllPrestamos(){
        return prestamoDAO.getAll();
    }

    public Prestamo getPrestamoById(int id){
        return prestamoDAO.getById(id);
    }

    public String addPrestamo(Prestamo prestamo) {
        LocalDate fechaInicio = prestamo.getFechaInicio();
        LocalDate fechaDevolucion = prestamo.getFechaDevolucion();

        boolean estaPrestado = prestamoDAO.estaPrestadoEnFecha(prestamo.getEjemplar().getId(), fechaInicio, fechaDevolucion);
        if (estaPrestado) {
            return "{\"error\": \"El ejemplar ya está prestado en estas fechas.\"}";
        }

        int prestamosActivos = prestamoDAO.contarPrestamosActivos(prestamo.getUsuario().getId());
        if (prestamosActivos >= 3) {
            return "{\"error\": \"El usuario ya tiene el número máximo de préstamos activos.\"}";
        }

        if (usuarioDAO.estaPenalizado(prestamo.getUsuario().getId())) {
            return "{\"error\": \"El usuario está penalizado hasta " +
                    prestamo.getUsuario().getPenalizacionHasta() + "\"}";
        }


        if (prestamoDAO.add(prestamo)) {
            return "{\"message\": \"Préstamo creado con éxito\"}";
        } else {
            return "{\"error\": \"No se pudo crear el préstamo\"}";
        }
    }

    public String updatePrestamo(Prestamo prestamo){
        if (prestamoDAO.update(prestamo)){
            return "{\"message\": \"Préstamo actualizado con éxito\"}";
        } else {
            return "{\"error\": \"No se pudo actualizar el préstamo\"}";
        }
    }

    public String deletePrestamo(Prestamo prestamo){
        if (prestamoDAO.delete(prestamo)){
            return ("{\"message\": \"Prestamo borrado con éxito\"}");
        } else {
            return ("{\"error\": \"No se pudo borrar el prestamo\"}");
        }
    }
}
