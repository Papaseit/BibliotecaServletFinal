package org.example.crudbasicoservlets.Servicio;

import org.example.crudbasicoservlets.modelo.Ejemplar;
import org.example.crudbasicoservlets.modelo.EjemplarDAO;
import org.example.crudbasicoservlets.modelo.Libro;

import java.util.List;

public class EjemplarService {
    private EjemplarDAO ejemplarDAO;

    public EjemplarService(){
        this.ejemplarDAO = new EjemplarDAO();
    }

    public List<Ejemplar> getAllEjemplares(){
        return ejemplarDAO.getAll();
    }

    public Ejemplar getEjemplaresById(int id){
        return ejemplarDAO.getById(id);
    }

    public String addEjemplar(Ejemplar ejemplar){
        if (ejemplarDAO.add(ejemplar)){
            return ("{\"message\": \"Ejemplar creado con éxito\"}");
        } else {
            return ("{\"error\": \"No se pudo crear el ejemplar\"}");
        }
    }

    public String updateEjemplar(Ejemplar ejemplar){
        if (ejemplarDAO.update(ejemplar)){
            return ("{\"message\": \"Ejemplar actualizado con éxito\"}");
        } else {
            return ("{\"error\": \"No se pudo actualizar el ejemplar\"}");
        }
    }
    public String deleteEjemplar(Ejemplar ejemplar){
        if (ejemplarDAO.delete(ejemplar)){
            return ("{\"message\": \"Ejemplar borrado con éxito\"}");
        } else {
            return ("{\"error\": \"No se pudo borrar el ejemplar\"}");
        }
    }
}
