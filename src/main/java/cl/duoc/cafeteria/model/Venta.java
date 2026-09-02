package cl.duoc.cafeteria.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long pedidoId;       // ID del pedido asociado
    private String metodoPago;   // Ej: "EFECTIVO", "TARJETA", "TRANSFERENCIA"
    private int totalPagado;
    private String numeroBoleta; // Un identificador único para la boleta
    private LocalDateTime fechaPago;

    public Venta() {
    }

    public Venta(Long pedidoId, String metodoPago, int totalPagado) {
        this.pedidoId = pedidoId;
        this.metodoPago = metodoPago;
        this.totalPagado = totalPagado;
        this.numeroBoleta = UUID.randomUUID().toString().substring(0, 8).toUpperCase(); // Genera un número de boleta aleatorio
        this.fechaPago = LocalDateTime.now();
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public int getTotalPagado() { return totalPagado; }
    public void setTotalPagado(int totalPagado) { this.totalPagado = totalPagado; }

    public String getNumeroBoleta() { return numeroBoleta; }
    public void setNumeroBoleta(String numeroBoleta) { this.numeroBoleta = numeroBoleta; }

    public LocalDateTime getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDateTime fechaPago) { this.fechaPago = fechaPago; }
}
