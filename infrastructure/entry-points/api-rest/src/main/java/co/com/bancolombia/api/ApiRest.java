package co.com.bancolombia.api;
import co.com.bancolombia.model.response.ApiResponse;
import co.com.bancolombia.model.response.Cliente;
import co.com.bancolombia.model.response.dto.ClienteDTO;
import co.com.bancolombia.usecase.clientes.ClientesUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/clientes", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ApiRest {
    private final ClientesUseCase clientesUseCase;

    @GetMapping("/listar")
    public ResponseEntity<ApiResponse<Iterable<Cliente>>> obtenerCliente() {
        ApiResponse<Iterable<Cliente>> response = clientesUseCase.obtenerClientes();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/guardar")
    public ResponseEntity<String> crearClientes(@RequestBody ClienteDTO clienteDTO) {
        clientesUseCase.guardarCliente(clienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Cliente creado exitosamente");
    }

    @PutMapping("/{numeroDocumento}")
    public ResponseEntity<ApiResponse<Cliente>> actualizarCliente(
            @PathVariable String numeroDocumento,
            @Validated @RequestBody ClienteDTO clienteDTO) {
        ApiResponse<Cliente> response = clientesUseCase.actualizarCliente(numeroDocumento, clienteDTO);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("{numeroDocumento}")
    public ResponseEntity<String> eliminarCliente(@PathVariable String numeroDocumento) {
        boolean isEliminado = clientesUseCase.eliminarCliente(numeroDocumento);
        if (isEliminado) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Cliente eliminado exitosamente");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("No se pudo eliminar el cliente por un error");
        }

    }
}
