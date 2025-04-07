package org.example.crudbasicoservlets.Controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.crudbasicoservlets.Servicio.LibroService;
import org.example.crudbasicoservlets.modelo.Libro;
import org.example.crudbasicoservlets.modelo.LibroDAO;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "libroservlet", value = "/api/libro")
public class LibroServlet extends HttpServlet {

    private LibroService libroService;
    private LibroDAO libroDAO;
    private ObjectMapper objectMapper;

    public LibroServlet() {
        this.libroService = new LibroService();
    }

    @Override
    public void init() {
        libroDAO = new LibroDAO();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String isbn = request.getParameter("isbn");
        PrintWriter out = response.getWriter();
        List<Libro> libros = libroService.getAllLibros();
        if (isbn != null && !isbn.isEmpty()) {
            Libro libro = libroService.getLibroByIsbn(isbn);
            out.println(objectMapper.writeValueAsString(libro));
        } else {
            out.println(objectMapper.writeValueAsString(libros));
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        String isbn = request.getParameter("isbn");
        String titulo = request.getParameter("titulo");
        String autor = request.getParameter("autor");

        Libro libro = new Libro(isbn, titulo, autor);
        out.println(libroService.addLibro(libro));
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String isbn = request.getParameter("isbn");
        String titulo = request.getParameter("titulo");
        String autor = request.getParameter("autor");

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        Libro libroExistente = libroService.getLibroByIsbn(isbn);
        libroExistente.setTitulo(titulo);
        libroExistente.setAutor(autor);
        out.println(libroService.updateLibro(libroExistente));
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String isbn = request.getParameter("isbn");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        Libro libroExistente = libroService.getLibroByIsbn(isbn);
        out.println(libroService.deleteLibro(libroExistente));
    }

    @Override
    public void destroy() {

    }
}
