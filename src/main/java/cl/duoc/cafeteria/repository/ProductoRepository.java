package cl.duoc.cafeteria.repository;

import cl.duoc.cafeteria.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repositorio: da acceso a la tabla producto.
 * Solo con extender JpaRepository ya tenemos findAll(), findById(), save(), etc.
 */
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Spring genera la consulta solo, a partir del nombre del metodo
    List<Producto> findByCategoria(String categoria);
}
