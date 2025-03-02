package co.com.bancolombia.jpa;

import co.com.bancolombia.jpa.modelsBd.ClienteEntity;
import co.com.bancolombia.model.response.Cliente;
import co.com.bancolombia.model.response.gateways.ResponseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
@RequiredArgsConstructor
public class JPARepositoryAdapter implements ResponseRepository {

    private static final Logger logger = LoggerFactory.getLogger(JPARepositoryAdapter.class);
    private final JPARepository repository;
    private final ObjectMapper mapper;

    @Override
    public Iterable<Cliente> obtenerClientes() {
        logger.debug("Consultando clientes en la base de datos");
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(entity -> mapper.convertValue(entity, Cliente.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Cliente> obtenerClientePorId(String numeroDocumento) {
        logger.debug("Consultando cliente en la base de datos con ID: {}", numeroDocumento);
        return repository.findById(Integer.valueOf(numeroDocumento))
                .map(entity -> {
                    logger.info("Cliente encontrado en base de datos con ID: {}", numeroDocumento);
                    return mapper.convertValue(entity, Cliente.class);
                });
    }

    @Override
    public Cliente actualizarCliente(Cliente cliente) {
        logger.debug("Actualizando cliente con ID: {}", cliente.getCedulaCliente());
        ClienteEntity entity = mapper.convertValue(cliente, ClienteEntity.class);
        ClienteEntity updatedEntity = repository.save(entity);
        logger.info("Cliente actualizado correctamente en la base de datos con ID: {}", cliente.getCedulaCliente());
        return mapper.convertValue(updatedEntity, Cliente.class);
    }

    @Override
    public Cliente guardarCliente(Cliente cliente) {
        logger.debug("Guardando un nuevo cliente en la base de datos: {}", cliente);
        ClienteEntity entity = mapper.convertValue(cliente, ClienteEntity.class);
        ClienteEntity entityGuardada = repository.save(entity);
        return mapper.convertValue(entityGuardada, Cliente.class);
    }

    @Override
    public boolean eliminarCliente(String numeroDocumento) {
        boolean clienteDelete = false;
        logger.debug("Eliminando cliente en la base de datos: {}", numeroDocumento);
        repository.deleteById(Integer.valueOf(numeroDocumento));
        clienteDelete = true;
        return clienteDelete;
    }
}
