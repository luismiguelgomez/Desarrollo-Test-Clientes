package co.com.bancolombia.model.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Cliente {
    private Long cedulaCliente;
    private String direccionCliente;
    private String emailCliente;
    private String nombreCliente;
    private String telefonoCliente;
}
