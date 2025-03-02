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
                    new ClienteEntity(1000225586L, "Luis@gmail.com", "Carrera 4 #5-65", "Luis Miguel", "31220235"),
                    new ClienteEntity(1546564111L, "ana@example.com", "Carrera 4 #5-6", "Lopez Garcia luis caros", "312564984")
            );

            clienteRepository.saveAll(clientes);
            System.out.println("Registros iniciales creados exitosamente.");
        }
    }
}
