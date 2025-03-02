package co.com.bancolombia.jpa.modelsBd;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteEntity {
    @Id
    private Long cedulaCliente;
    private String direccionCliente;
    private String emailCliente;
    private String nombreCliente;
    private String telefonoCliente;
}
