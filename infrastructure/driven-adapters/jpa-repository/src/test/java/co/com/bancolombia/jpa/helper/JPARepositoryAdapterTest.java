package co.com.bancolombia.jpa.helper;

import co.com.bancolombia.jpa.JPARepositoryAdapter;
import co.com.bancolombia.jpa.modelsBd.ClienteEntity;
import co.com.bancolombia.model.response.Cliente;
import co.com.bancolombia.jpa.JPARepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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
class JPARepositoryAdapterTest {

    @Mock
    private JPARepository repository;

    @Mock
    private ObjectMapper mapper;

    @InjectMocks
    private JPARepositoryAdapter jpaRepositoryAdapter;

    private ClienteEntity clienteEntity;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        clienteEntity = new ClienteEntity();
        clienteEntity.setId(1);
        clienteEntity.setPrimerNombre("Luis");
        clienteEntity.setSegundoNombre("Miguel");
        clienteEntity.setPrimerApellido("Gomez");
        clienteEntity.setSegundoApellido("Perez");
        clienteEntity.setTelefono(310272820);
        clienteEntity.setDireccion("Calle 123");
        clienteEntity.setCiudad("Medellin");
        clienteEntity.setCorreo("luis@example.com");

        cliente = Cliente.builder()
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
    void obtenerClientePorId_Exito() {
        when(repository.findById(1)).thenReturn(Optional.of(clienteEntity));
        when(mapper.convertValue(clienteEntity, Cliente.class)).thenReturn(cliente);

        Optional<Cliente> resultado = jpaRepositoryAdapter.obtenerClientePorId("1");

        assertTrue(resultado.isPresent());
        assertEquals("Luis", resultado.get().getPrimerNombre());

        verify(repository, times(1)).findById(1);
        verify(mapper, times(1)).convertValue(clienteEntity, Cliente.class);
    }

    @Test
    void obtenerClientePorId_NoEncontrado() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        Optional<Cliente> resultado = jpaRepositoryAdapter.obtenerClientePorId("1");

        assertFalse(resultado.isPresent());

        verify(repository, times(1)).findById(1);
        verifyNoInteractions(mapper);
    }

    @Test
    void actualizarCliente_Exito() {
        when(mapper.convertValue(cliente, ClienteEntity.class)).thenReturn(clienteEntity);
        when(repository.save(clienteEntity)).thenReturn(clienteEntity);
        when(mapper.convertValue(clienteEntity, Cliente.class)).thenReturn(cliente);

        Cliente resultado = jpaRepositoryAdapter.actualizarCliente(cliente);

        assertNotNull(resultado);
        assertEquals("Luis", resultado.getPrimerNombre());

        verify(repository, times(1)).save(clienteEntity);
        verify(mapper, times(1)).convertValue(cliente, ClienteEntity.class);
        verify(mapper, times(1)).convertValue(clienteEntity, Cliente.class);
    }
}
