package co.com.bancolombia.usecase.clientes;

import co.com.bancolombia.model.response.ApiResponse;
import co.com.bancolombia.model.response.Cliente;
import co.com.bancolombia.model.response.dto.ClienteDTO;
import co.com.bancolombia.model.response.gateways.ResponseRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ClientesUseCase {

    private final ResponseRepository responseRepository;

    /*
    public ApiResponse<Cliente> obtenerCliente(String tipoDocumento, String numeroDocumento, boolean withAddress) {

        try {
            if (tipoDocumento == null || numeroDocumento == null || tipoDocumento.isEmpty() || numeroDocumento.isEmpty()) {
                return ApiResponse.error(400, "Solicitud incorrecta", "El tipo de documento y el numero son obligatorios");
            }

            if (!esTipoDocumentoValido(tipoDocumento)) {
                return ApiResponse.error(400, "Tipo de documento invalido", "El tipo de documento no es reconocido");
            }

            Optional<Cliente> clienteOpt = responseRepository.obtenerClientePorId(numeroDocumento);

            if (clienteOpt.isPresent()) {
                Cliente cliente = clienteOpt.get();

                Cliente.ClienteBuilder clienteBuilder = Cliente.builder()
                        .primerNombre(cliente.getPrimerNombre())
                        .segundoNombre(cliente.getSegundoNombre())
                        .primerApellido(cliente.getPrimerApellido())
                        .segundoApellido(cliente.getSegundoApellido())
                        .telefono(cliente.getTelefono())
                        .ciudad(cliente.getCiudad())
                        .correo(cliente.getCorreo());

                if (withAddress) {
                    clienteBuilder.direccion(cliente.getDireccion());
                }

                Cliente clienteFinal = clienteBuilder.build();

                return ApiResponse.success(clienteFinal);
            } else {
                return ApiResponse.error(404, "Cliente no encontrado", "No se encontro un cliente con esos datos");
            }
        } catch (Exception e) {
            return ApiResponse.error(500, "Error interno del servidor", e.getMessage());
        }
    }

    public ApiResponse<Cliente> actualizarCliente(String numeroDocumento, Cliente cliente) {
        try {
            Optional<Cliente> clienteExistenteOpt = responseRepository.obtenerClientePorId(numeroDocumento);

            if (clienteExistenteOpt.isEmpty()) {
                return ApiResponse.error(404, "Cliente no encontrado", "No se encontro un cliente con ese numero de documento");
            }

            Cliente clienteActualizado = clienteExistenteOpt.get().toBuilder()
                    .primerNombre(cliente.getPrimerNombre())
                    .segundoNombre(cliente.getSegundoNombre())
                    .primerApellido(cliente.getPrimerApellido())
                    .segundoApellido(cliente.getSegundoApellido())
                    .telefono(cliente.getTelefono())
                    .direccion(cliente.getDireccion())
                    .ciudad(cliente.getCiudad())
                    .correo(cliente.getCorreo())
                    .build();

            Cliente clienteGuardado = responseRepository.actualizarCliente(clienteActualizado);

            return ApiResponse.success(clienteGuardado);
        } catch (Exception e) {
            return ApiResponse.error(500, "Error interno del servidor", e.getMessage());
        }
    }


     */

    private boolean esTipoDocumentoValido(String tipoDocumento) {
        return tipoDocumento.equalsIgnoreCase("CC") ||
                tipoDocumento.equalsIgnoreCase("TI") ||
                tipoDocumento.equalsIgnoreCase("CE");
    }

    public ApiResponse<Iterable<Cliente>> obtenerClientes() {
        try {
            Iterable<Cliente> clientes = responseRepository.obtenerClientes();

            if (!clientes.iterator().hasNext()) {
                return ApiResponse.error(404, "No hay clientes", "No se encontraron clientes en la base de datos");
            }

            return ApiResponse.success(clientes);
        } catch (Exception e) {
            return ApiResponse.error(500, "Error interno del servidor", e.getMessage());
        }
    }

    public ApiResponse<Cliente> guardarCliente(ClienteDTO clienteDTO) {
        try {
            // Verificar si el cliente ya existe en la base de datos
            Optional<Cliente> clienteExistente = responseRepository.obtenerClientePorId(clienteDTO.getCedulaCliente().toString());

            if (clienteExistente.isPresent()) {
                return ApiResponse.error(409, "Cliente ya registrado", "El cliente con el documento " + clienteDTO.getCedulaCliente() + " ya existe.");
            }

            // Convertir ClienteDTO a Cliente
            Cliente nuevoCliente = Cliente.builder()
                    .cedulaCliente(clienteDTO.getCedulaCliente())
                    .nombreCliente(clienteDTO.getNombreCliente())
                    .direccionCliente(clienteDTO.getDireccionCliente())
                    .emailCliente(clienteDTO.getEmailCliente())
                    .telefonoCliente(clienteDTO.getTelefonoCliente())
                    .build();

            // Guardar el cliente en la base de datos
            Cliente clienteGuardado = responseRepository.guardarCliente(nuevoCliente);

            return ApiResponse.success(clienteGuardado);
        } catch (Exception e) {
            return ApiResponse.error(500, "Error interno del servidor", e.getMessage());
        }
    }

    public ApiResponse<Cliente> actualizarCliente(String numeroDocumento, ClienteDTO clienteDTO) {
        try {
            Optional<Cliente> clienteExistente =  responseRepository.obtenerClientePorId(numeroDocumento);

            if (clienteExistente.isEmpty()) {
                return ApiResponse.error(409, "Cliente no encontrado", "El cliente con el documento " + clienteDTO.getCedulaCliente() + " no existe.");
            }
            Cliente clienteActualizado = clienteExistente.get().toBuilder()
                    .cedulaCliente(clienteExistente.get().getCedulaCliente())
                    .direccionCliente(clienteDTO.getDireccionCliente())
                    .emailCliente(clienteDTO.getEmailCliente())
                    .telefonoCliente(clienteDTO.getTelefonoCliente())
                    .nombreCliente(clienteDTO.getNombreCliente())
                    .build();

            Cliente clienteGuardado = responseRepository.actualizarCliente(clienteActualizado);

            return ApiResponse.success(clienteGuardado);
        } catch (Exception e) {
            return ApiResponse.error(500, "Error interno del servidor", e.getMessage());
        }
    }

    public boolean eliminarCliente(String numeroDocumento) {
        Optional<Cliente> clienteExistente =  responseRepository.obtenerClientePorId(numeroDocumento);

        if (clienteExistente.isEmpty()) {
            return false;
        }

        return responseRepository.eliminarCliente(numeroDocumento);
    }
}


