package cl.duoc.cafeteria.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Un item del menu de la cafeteria.
 * @Entity le dice a JPA que esta clase se guarda como una tabla llamada "producto".
 */
@Entity
public class Producto {

    @Id                                                  // clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // el id lo genera la base de datos
    private Long id;

    private String nombre;
    private String categoria;    // CAFE, TE, PASTELERIA, FRIO
    private int precio;          // en pesos chilenos
    private boolean disponible;  // si hoy se puede pedir o no

    // JPA necesita un constructor vacio para poder crear los objetos
    public Producto() {
    }

    // Getters y setters: permiten leer y modificar los datos desde otras clases
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public int getPrecio() { return precio; }
    public void setPrecio(int precio) { this.precio = precio; }

    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
}
