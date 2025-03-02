package co.com.bancolombia.model.response.gateways;

import co.com.bancolombia.model.response.Cliente;

import java.util.Optional;

public interface ResponseRepository {
    Iterable<Cliente> obtenerClientes();
    Optional<Cliente> obtenerClientePorId(String numeroDocumento);
    Cliente actualizarCliente(Cliente cliente);
    Cliente guardarCliente(Cliente nuevoCliente);
    boolean eliminarCliente(String numeroDocumento);
}

