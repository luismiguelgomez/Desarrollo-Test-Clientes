package co.com.bancolombia.api;
import co.com.bancolombia.model.response.ApiResponse;
import co.com.bancolombia.model.response.Cliente;
import co.com.bancolombia.usecase.clientes.ClientesUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/clientes", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ApiRest {
    private final ClientesUseCase clientesUseCase;

    @GetMapping("/{tipoDocumento}/{numeroDocumento}")
    public ResponseEntity<ApiResponse<Cliente>> obtenerCliente(
            @PathVariable String tipoDocumento,
            @PathVariable String numeroDocumento,
            @RequestParam(value = "withAddress", required = false, defaultValue = "false") boolean withAddress) {

        ApiResponse<Cliente> response = clientesUseCase.obtenerCliente(tipoDocumento, numeroDocumento, withAddress);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/{numeroDocumento}")
    public ResponseEntity<ApiResponse<Cliente>> actualizarCliente(
            @PathVariable String numeroDocumento,
            @Validated @RequestBody Cliente cliente) {

        ApiResponse<Cliente> response = clientesUseCase.actualizarCliente(numeroDocumento, cliente);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
