package cl.duoc.cafeteria;

import cl.duoc.cafeteria.service.PedidoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test unitario: comprueba que el total del pedido se calcule bien.
 * Este es el test que va a correr GitHub Actions en cada push.
 */
class PedidoServiceTest {

    @Test
    @DisplayName("El total es el precio multiplicado por la cantidad")
    void calculaElTotalCorrectamente() {
        assertEquals(9600, PedidoService.calcularTotal(3200, 3));
    }

    @Test
    @DisplayName("Un pedido de una unidad cuesta lo mismo que el producto")
    void calculaElTotalDeUnaUnidad() {
        assertEquals(1800, PedidoService.calcularTotal(1800, 1));
    }
}
