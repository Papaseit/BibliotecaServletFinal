package org.example.crudbasicoservlets.Servicio;

import jakarta.servlet.http.HttpServletResponse;
import org.example.crudbasicoservlets.modelo.Libro;
import org.example.crudbasicoservlets.modelo.LibroDAO;

import java.util.List;
import java.util.regex.Pattern;

public class LibroService {

    private LibroDAO libroDAO;

    public LibroService() {
        this.libroDAO = new LibroDAO();
    }


    public List<Libro> getAllLibros() {
        return libroDAO.getAllLibros();
    }

    public Libro getLibroByIsbn(String isbn) {
        Pattern.matches("[A-Z]+",isbn);
        return libroDAO.getLibroById(isbn);
    }

    public String addLibro(Libro libro) {
        if (libroDAO.addLibro(libro)) {
            return ("{\"message\": \"Libro creado con éxito\"}");
        } else {
            return ("{\"error\": \"No se pudo crear el libro\"}");
        }
    }

    public String updateLibro(Libro libro) {
        if (libroDAO.updateLibro(libro)) {
            return ("{\"message\": \"Libro actualizado con éxito\"}");
        }else {
            return ("{\"error\": \"No se pudo actualizar el libro\"}");
        }
    }

    public String deleteLibro(Libro libro) {
       if (libroDAO.delete(libro)) {
           return ("{\"message\": \"Libro borrado con éxito\"}");
       }else {
           return ("{\"error\": \"No se pudo borrar el libro\"}");
       }
    }
}
