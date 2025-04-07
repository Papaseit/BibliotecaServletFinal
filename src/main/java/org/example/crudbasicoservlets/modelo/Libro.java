package org.example.crudbasicoservlets.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Libro {
    @Id
    @Column(name = "isbn", nullable = false, length = 20)
    private String isbn;

    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;

    @Column(name = "autor", nullable = false, length = 100)
    private String autor;

//    @OneToMany(mappedBy = "isbn")
//    private Set<Ejemplar> ejemplars = new LinkedHashSet<>();

    public Libro(){
    }

    public Libro(String isbn, String titulo, String autor) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

//    public Set<Ejemplar> getEjemplars() {
//        return ejemplars;
//    }
//
//    public void setEjemplars(Set<Ejemplar> ejemplars) {
//        this.ejemplars = ejemplars;
//    }

}