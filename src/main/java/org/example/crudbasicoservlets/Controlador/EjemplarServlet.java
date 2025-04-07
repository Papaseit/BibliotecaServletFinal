package org.example.crudbasicoservlets.Controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.crudbasicoservlets.Servicio.EjemplarService;
import org.example.crudbasicoservlets.modelo.Ejemplar;
import org.example.crudbasicoservlets.modelo.EjemplarDAO;
import org.example.crudbasicoservlets.modelo.Libro;
import org.example.crudbasicoservlets.modelo.LibroDAO;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "ejemplarservlet", value = "/api/ejemplar")
public class EjemplarServlet extends HttpServlet {
    private EjemplarService ejemplarService;
    private EjemplarDAO ejemplarDAO;
    private ObjectMapper objectMapper;

    public EjemplarServlet() {
        ejemplarService = new EjemplarService();
    }

    @Override
    public void init(){
        ejemplarDAO = new EjemplarDAO();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("application/json");
        String idParam = request.getParameter("id");
        PrintWriter out = response.getWriter();
        List<Ejemplar> ejemplars = ejemplarService.getAllEjemplares();
        if (idParam != null && !idParam.isEmpty()){
            int id = Integer.parseInt(idParam);
            Ejemplar ejemplar = ejemplarService.getEjemplaresById(id);
            out.println(objectMapper.writeValueAsString(ejemplar));
        }else {
            out.println(objectMapper.writeValueAsString(ejemplars));
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("application/json");
        String libroIsbn = request.getParameter("isbn");
        String estado = request.getParameter("estado");
        PrintWriter out = response.getWriter();
        Libro libro = new LibroDAO().getLibroById(libroIsbn);

        Ejemplar ejemplar = new Ejemplar(libro, estado);
        out.println(ejemplarService.addEjemplar(ejemplar));
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("application/json");
        int id = Integer.parseInt(request.getParameter("id"));
        String libroIsbn = request.getParameter("isbn");
        String estado = request.getParameter("estado");
        PrintWriter out = response.getWriter();
        Ejemplar ejemplar = ejemplarService.getEjemplaresById(id);
        ejemplar.setEstado(estado);
        out.println(ejemplarService.updateEjemplar(ejemplar));
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("application/json");
        int id = Integer.parseInt(request.getParameter("id"));
        PrintWriter out = response.getWriter();

        Ejemplar ejemplar = ejemplarService.getEjemplaresById(id);
        out.println(ejemplarService.deleteEjemplar(ejemplar));
    }

    @Override
    public void destroy(){

    }
}
