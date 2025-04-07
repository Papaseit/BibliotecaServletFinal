package org.example.crudbasicoservlets.Controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.crudbasicoservlets.Servicio.PrestamoService;
import org.example.crudbasicoservlets.modelo.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet(name = "prestamoservlet", value = "/api/prestamo")
public class PrestamoServlet extends HttpServlet {
    private PrestamoService prestamoService;
    private PrestamoDAO prestamoDAO;
    private ObjectMapper objectMapper;

    public PrestamoServlet() {
        prestamoService = new PrestamoService();
        prestamoDAO = new PrestamoDAO();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        String idParam = request.getParameter("id");
        PrintWriter out = response.getWriter();
        List<Prestamo> prestamos = prestamoService.getAllPrestamos();
        if (idParam !=null && !idParam.isEmpty()){
            int id = Integer.parseInt(idParam);
            Prestamo prestamo = prestamoService.getPrestamoById(id);
            out.println(objectMapper.writeValueAsString(prestamo));
        }else {
            out.println(objectMapper.writeValueAsString(prestamos));
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        String usuarioId = request.getParameter("usuario_id");
        String ejemplarId = request.getParameter("ejemplar_id");
        String fechaInicioParam = request.getParameter("fechaInicio");
        String fechaDevolucionParam = request.getParameter("fechaDevolucion");

        PrintWriter out = response.getWriter();

        Usuario usuario = new UsuarioDAO().getById(Integer.parseInt(usuarioId));
        Ejemplar ejemplar = new EjemplarDAO().getById(Integer.parseInt(ejemplarId));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaInicio = LocalDate.parse(fechaInicioParam, formatter);
        LocalDate fechaDevolucion = null;

        if (usuario != null && ejemplar != null) {
        if (fechaDevolucionParam != null && !fechaDevolucionParam.isEmpty()) {
            fechaDevolucion = LocalDate.parse(fechaDevolucionParam, formatter);
            }
        }

        Prestamo prestamo = new Prestamo(usuario, ejemplar, fechaInicio);
        prestamo.setFechaDevolucion(fechaDevolucion);
        out.println(prestamoService.addPrestamo(prestamo));
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        int id = Integer.parseInt(request.getParameter("id"));
        String fechaDevolucionParam = request.getParameter("fechaDevolucion");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Prestamo prestamo = prestamoService.getPrestamoById(id);
        prestamo.setFechaDevolucion(LocalDate.parse(fechaDevolucionParam));
        out.println(prestamoService.updatePrestamo(prestamo));
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        int id = Integer.parseInt(request.getParameter("id"));
        PrintWriter out = response.getWriter();

        Prestamo prestamo = prestamoService.getPrestamoById(id);
        out.println(prestamoService.deletePrestamo(prestamo));
    }
}
