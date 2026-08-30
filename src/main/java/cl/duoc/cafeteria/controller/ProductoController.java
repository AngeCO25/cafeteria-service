package cl.duoc.cafeteria.controller;

import cl.duoc.cafeteria.model.Producto;
import cl.duoc.cafeteria.repository.ProductoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Expone el menu de la cafeteria como JSON.
 * @RestController = todo lo que devuelvan estos metodos se convierte en JSON.
 */
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoRepository productoRepository;

    public ProductoController(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // GET /api/productos            -> todo el menu
    // GET /api/productos?categoria=CAFE -> solo una categoria
    @GetMapping
    public List<Producto> listar(@RequestParam(required = false) String categoria) {
        if (categoria == null) {
            return productoRepository.findAll();
        }
        return productoRepository.findByCategoria(categoria.toUpperCase());
    }
    
        // GET /api/productos/disponibles -> solo lo que se puede pedir hoy
    @GetMapping("/disponibles")
    public List<Producto> listarDisponibles() {
        return productoRepository.findByDisponibleTrue();
    }

    // GET /api/productos/3 -> un producto puntual
    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscarPorId(@PathVariable Long id) {
        return productoRepository.findById(id)
                .map(ResponseEntity::ok)                       // si existe -> 200 con el JSON
                .orElse(ResponseEntity.notFound().build());    // si no existe -> 404
    }
}
