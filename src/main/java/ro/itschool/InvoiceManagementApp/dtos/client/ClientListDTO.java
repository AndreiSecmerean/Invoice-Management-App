package ro.itschool.InvoiceManagementApp.dtos.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ClientListDTO {
    private List<ClientDTO> clientDTOS;
}
