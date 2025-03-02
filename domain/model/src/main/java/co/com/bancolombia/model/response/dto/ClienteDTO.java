package co.com.bancolombia.model.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {
    private Long cedulaCliente;
    private String direccionCliente;
    private String emailCliente;
    private String nombreCliente;
    private String telefonoCliente;
}

