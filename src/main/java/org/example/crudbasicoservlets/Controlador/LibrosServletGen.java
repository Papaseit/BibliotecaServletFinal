package org.example.crudbasicoservlets.Controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.crudbasicoservlets.modelo.DAOGenerico;
import org.example.crudbasicoservlets.modelo.Libro;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "librosServlet", value = "/libros-servlet")
public class LibrosServletGen extends HttpServlet {

    DAOGenerico<Libro, String> daolibro;

    public void init(){
        daolibro = new DAOGenerico<>(Libro.class,String.class);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response){

    }


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        PrintWriter impresora = response.getWriter();
        ObjectMapper conversorJson = new ObjectMapper();
        conversorJson.registerModule(new JavaTimeModule());

        List<Libro> listaLibros  = daolibro.getAll();
        System.out.println("En java" + listaLibros);

        String json_response = conversorJson.writeValueAsString(listaLibros);
        System.out.println("En java json" + json_response);
        impresora.println(json_response);

    }

    public void destroy(){

    }
}
