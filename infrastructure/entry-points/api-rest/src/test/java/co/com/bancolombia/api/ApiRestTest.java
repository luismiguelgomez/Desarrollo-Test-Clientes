package co.com.bancolombia.api;

import co.com.bancolombia.model.response.ApiResponse;
import co.com.bancolombia.model.response.Cliente;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ApiRestTest {

    private MockMvc mockMvc;

    @Mock
    private co.com.bancolombia.usecase.clientes.ClientesUseCase clientesUseCase;

    @InjectMocks
    private ApiRest apiRest;

    private Cliente clienteMock;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(apiRest).build();

        clienteMock = Cliente.builder()
                .id(1)
                .primerNombre("Luis")
                .segundoNombre("Miguel")
                .primerApellido("Gomez")
                .segundoApellido("Perez")
                .telefono(310272820)
                .direccion("Calle 48 p bis c sur #3-45")
                .ciudad("Medellin")
                .correo("luis@example.com")
                .build();
    }

    @Test
    void obtenerCliente_ConDireccion() throws Exception {
        ApiResponse<Cliente> apiResponse = ApiResponse.success(clienteMock);

        when(clientesUseCase.obtenerCliente("CC", "12345678", true)).thenReturn(apiResponse);

        mockMvc.perform(get("/api/clientes/CC/12345678")
                        .param("withAddress", "true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.data.direccion").value("Calle 48 p bis c sur #3-45"));

        verify(clientesUseCase, times(1)).obtenerCliente("CC", "12345678", true);
    }

    @Test
    void obtenerCliente_NoEncontrado() throws Exception {
        ApiResponse<Cliente> apiResponse = ApiResponse.error(404, "Cliente no encontrado", "No se encontro un cliente con esos datos");

        when(clientesUseCase.obtenerCliente("CC", "999999", false)).thenReturn(apiResponse);

        mockMvc.perform(get("/api/clientes/CC/999999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Cliente no encontrado"));

        verify(clientesUseCase, times(1)).obtenerCliente("CC", "999999", false);
    }

    @Test
    void actualizarCliente_Exito() throws Exception {
        ApiResponse<Cliente> apiResponse = ApiResponse.success(clienteMock);

        when(clientesUseCase.actualizarCliente(eq("12345678"), any(Cliente.class))).thenReturn(apiResponse);

        mockMvc.perform(put("/api/clientes/12345678")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "primerNombre": "Luis",
                                "segundoNombre": "Miguel",
                                "primerApellido": "Gomez",
                                "segundoApellido": "Perez",
                                "telefono": 310272820,
                                "direccion": "Calle 123",
                                "ciudad": "Medellin",
                                "correo": "luis@example.com"
                            }
                            """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.data.primerNombre").value("Luis"));

        verify(clientesUseCase, times(1)).actualizarCliente(eq("12345678"), any(Cliente.class));
    }

    @Test
    void actualizarCliente_NoEncontrado() throws Exception {
        ApiResponse<Cliente> apiResponse = ApiResponse.error(404, "Cliente no encontrado", "No se encontró un cliente con ese numero de documento");

        when(clientesUseCase.actualizarCliente(eq("999999"), any(Cliente.class))).thenReturn(apiResponse);

        mockMvc.perform(put("/api/clientes/999999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "primerNombre": "Luis",
                                "segundoNombre": "Miguel",
                                "primerApellido": "Gomez",
                                "segundoApellido": "Perez",
                                "telefono": 310272820,
                                "direccion": "Calle 123",
                                "ciudad": "Medellin",
                                "correo": "luis@example.com"
                            }
                            """))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Cliente no encontrado"));

        verify(clientesUseCase, times(1)).actualizarCliente(eq("999999"), any(Cliente.class));
    }
}

