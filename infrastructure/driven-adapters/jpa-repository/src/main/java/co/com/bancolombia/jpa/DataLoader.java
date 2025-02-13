package co.com.bancolombia.jpa;

import co.com.bancolombia.jpa.modelsBd.ClienteEntity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final JPARepository clienteRepository;

    @Override
    public void run(String... args) {
        if (clienteRepository.count() == 0) {
            List<ClienteEntity> clientes = List.of(
                    new ClienteEntity(1000225586, "Luis", "Miguel", "Gomez", "Perez", 310272820, "Calle 48 p bis c sur #3-45", "Medellin", "luis@example.com"),
                    new ClienteEntity(1546564111, "Ana", "Maria", "Lopez", "Garcia", 312564984, "Carrera 4 #5-6", "Bogota", "ana@example.com"),
                    new ClienteEntity(1331054566, "Carlos", "Eduardo", "Martinez", "Ruiz", 315566845, "Av. Siempre Viva", "Cali", "carlos@example.com")
            );

            clienteRepository.saveAll(clientes);
            System.out.println("Registros iniciales creados exitosamente.");
        }
    }
}
