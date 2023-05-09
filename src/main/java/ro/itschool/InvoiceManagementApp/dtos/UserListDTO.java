package ro.itschool.InvoiceManagementApp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ro.itschool.InvoiceManagementApp.dtos.client.ClientDTO;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserListDTO {
    private List<UserDTO> userDTOS;
}
