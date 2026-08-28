package cl.duoc.cafeteria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada del microservicio.
 * Al ejecutar esta clase se levanta el servidor web en el puerto 8080.
 */
@SpringBootApplication
public class CafeteriaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CafeteriaApplication.class, args);
    }
}
