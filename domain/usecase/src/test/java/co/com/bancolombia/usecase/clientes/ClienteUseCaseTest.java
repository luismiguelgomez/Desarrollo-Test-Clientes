package co.com.bancolombia.usecase.clientes;

import co.com.bancolombia.model.response.ApiResponse;
import co.com.bancolombia.model.response.Cliente;
import co.com.bancolombia.model.response.gateways.ResponseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteUseCaseTest {

    @Mock
    private co.com.bancolombia.model.response.gateways.ResponseRepository responseRepository;

    @InjectMocks
    private ClientesUseCase clienteUseCase;

    private Cliente clienteMock;

    @BeforeEach
    void setUp() {
        clienteMock = Cliente.builder()
                .id(1)
                .primerNombre("Luis")
                .segundoNombre("Miguel")
                .primerApellido("Gomez")
                .segundoApellido("Perez")
                .telefono(310272820)
                .direccion("Calle 123")
                .ciudad("Medellin")
                .correo("luis@example.com")
                .build();
    }

    @Test
    void obtenerCliente_Exito_SinDireccion() {
        when(responseRepository.obtenerClientePorId("12345678")).thenReturn(Optional.of(clienteMock));

        ApiResponse<Cliente> response = clienteUseCase.obtenerCliente("CC", "12345678", false);

        assertEquals(200, response.getStatus());
        assertNotNull(response.getData());
        assertNull(response.getData().getDireccion());
        verify(responseRepository, times(1)).obtenerClientePorId("12345678");
    }

    @Test
    void obtenerCliente_Exito_ConDireccion() {
        when(responseRepository.obtenerClientePorId("12345678")).thenReturn(Optional.of(clienteMock));

        ApiResponse<Cliente> response = clienteUseCase.obtenerCliente("CC", "12345678", true);

        assertEquals(200, response.getStatus());
        assertNotNull(response.getData());
        assertEquals("Calle 123", response.getData().getDireccion());
        verify(responseRepository, times(1)).obtenerClientePorId("12345678");
    }

    @Test
    void obtenerCliente_ClienteNoEncontrado() {
        when(responseRepository.obtenerClientePorId("999999")).thenReturn(Optional.empty());

        ApiResponse<Cliente> response = clienteUseCase.obtenerCliente("CC", "999999", true);

        assertEquals(404, response.getStatus());
        assertEquals("Cliente no encontrado", response.getMessage());
        assertNull(response.getData());
        verify(responseRepository, times(1)).obtenerClientePorId("999999");
    }

    @Test
    void obtenerCliente_ErrorInterno() {
        when(responseRepository.obtenerClientePorId(any())).thenThrow(new RuntimeException("Error en BD"));

        ApiResponse<Cliente> response = clienteUseCase.obtenerCliente("CC", "12345678", true);

        assertEquals(500, response.getStatus());
        assertEquals("Error interno del servidor", response.getMessage());
        verify(responseRepository, times(1)).obtenerClientePorId("12345678");
    }

    @Test
    void obtenerCliente_TipoDocumentoInvalido() {
        ApiResponse<Cliente> response = clienteUseCase.obtenerCliente("XYZ", "12345678", true);

        assertEquals(400, response.getStatus());
        assertEquals("Tipo de documento invalido", response.getMessage());
    }

    @Test
    void actualizarCliente_Exito() {
        when(responseRepository.obtenerClientePorId("12345678")).thenReturn(Optional.of(clienteMock));
        when(responseRepository.actualizarCliente(any(Cliente.class))).thenReturn(clienteMock);

        ApiResponse<Cliente> response = clienteUseCase.actualizarCliente("12345678", clienteMock);

        assertEquals(200, response.getStatus());
        assertNotNull(response.getData());
        assertEquals("Luis", response.getData().getPrimerNombre());
        verify(responseRepository, times(1)).obtenerClientePorId("12345678");
        verify(responseRepository, times(1)).actualizarCliente(any(Cliente.class));
    }

    @Test
    void actualizarCliente_NoEncontrado() {
        when(responseRepository.obtenerClientePorId("999999")).thenReturn(Optional.empty());

        ApiResponse<Cliente> response = clienteUseCase.actualizarCliente("999999", clienteMock);

        assertEquals(404, response.getStatus());
        assertEquals("Cliente no encontrado", response.getMessage());
        verify(responseRepository, times(1)).obtenerClientePorId("999999");
        verify(responseRepository, never()).actualizarCliente(any(Cliente.class));
    }

    @Test
    void actualizarCliente_ErrorInterno() {
        when(responseRepository.obtenerClientePorId("12345678")).thenReturn(Optional.of(clienteMock));
        when(responseRepository.actualizarCliente(any(Cliente.class))).thenThrow(new RuntimeException("Error en BD"));

        ApiResponse<Cliente> response = clienteUseCase.actualizarCliente("12345678", clienteMock);

        assertEquals(500, response.getStatus());
        assertEquals("Error interno del servidor", response.getMessage());
        verify(responseRepository, times(1)).obtenerClientePorId("12345678");
        verify(responseRepository, times(1)).actualizarCliente(any(Cliente.class));
    }
}
