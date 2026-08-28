package cl.duoc.cafeteria.controller;

import cl.duoc.cafeteria.dto.NuevoPedidoRequest;
import cl.duoc.cafeteria.model.Pedido;
import cl.duoc.cafeteria.repository.PedidoRepository;
import cl.duoc.cafeteria.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoRepository pedidoRepository;

    public PedidoController(PedidoService pedidoService, PedidoRepository pedidoRepository) {
        this.pedidoService = pedidoService;
        this.pedidoRepository = pedidoRepository;
    }

    // GET /api/pedidos -> lista de pedidos hechos
    @GetMapping
    public List<Pedido> listar() {
        return pedidoRepository.findAll();
    }

    // POST /api/pedidos -> crea un pedido nuevo
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody NuevoPedidoRequest request) {
        try {
            Pedido pedido = pedidoService.crear(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
        } catch (IllegalArgumentException e) {
            // Devolvemos un JSON con el mensaje del error en vez de que reviente
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
