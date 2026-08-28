package cl.duoc.cafeteria.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * Molde de los datos que llegan en el JSON cuando alguien crea un pedido.
 * Ejemplo: { "cliente": "Angela", "productoId": 2, "cantidad": 3 }
 */
public class NuevoPedidoRequest {

    @NotBlank(message = "El nombre del cliente es obligatorio")
    private String cliente;

    private Long productoId;

    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private int cantidad;

    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }

    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
}
