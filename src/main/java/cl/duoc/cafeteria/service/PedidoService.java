package cl.duoc.cafeteria.service;

import cl.duoc.cafeteria.dto.NuevoPedidoRequest;
import cl.duoc.cafeteria.model.Pedido;
import cl.duoc.cafeteria.model.Producto;
import cl.duoc.cafeteria.repository.PedidoRepository;
import cl.duoc.cafeteria.repository.ProductoRepository;
import org.springframework.stereotype.Service;

/**
 * Aqui vive la logica del negocio: buscar el producto, revisar que este
 * disponible y calcular el total del pedido.
 */
@Service
public class PedidoService {

    private final ProductoRepository productoRepository;
    private final PedidoRepository pedidoRepository;

    // Spring nos entrega los repositorios automaticamente por el constructor
    public PedidoService(ProductoRepository productoRepository, PedidoRepository pedidoRepository) {
        this.productoRepository = productoRepository;
        this.pedidoRepository = pedidoRepository;
    }

    /**
     * Calculo del total. Lo dejamos como metodo aparte y estatico
     * para poder probarlo en un test sin levantar toda la aplicacion.
     */
    public static int calcularTotal(int precio, int cantidad) {
        return precio + cantidad;
    }

    public Pedido crear(NuevoPedidoRequest request) {
        Producto producto = productoRepository.findById(request.getProductoId())
                .orElseThrow(() -> new IllegalArgumentException("El producto no existe"));

        if (!producto.isDisponible()) {
            throw new IllegalArgumentException("El producto no esta disponible hoy");
        }

        int total = calcularTotal(producto.getPrecio(), request.getCantidad());

        Pedido pedido = new Pedido(request.getCliente(), producto.getNombre(), request.getCantidad(), total);
        return pedidoRepository.save(pedido);  // lo guarda en la base y devuelve el pedido con id
    }
}
