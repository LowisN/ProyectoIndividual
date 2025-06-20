package mx.ipn.escom.Recomendaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {
    "mx.ipn.escom.Recomendaciones",
    "mx.ipn.escom.Recomendaciones.auth.entity",
    "mx.ipn.escom.Recomendaciones.auth.SistemaAutenticacion"
})
@EnableJpaRepositories(basePackages = "mx.ipn.escom.Recomendaciones.auth.repository")
public class RecomendacionesApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecomendacionesApplication.class, args);
    }
}
