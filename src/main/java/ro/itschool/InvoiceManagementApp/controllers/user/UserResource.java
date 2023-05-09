package ro.itschool.InvoiceManagementApp.controllers.user;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.itschool.InvoiceManagementApp.dtos.UserDTO;
import ro.itschool.InvoiceManagementApp.dtos.UserListDTO;
import ro.itschool.InvoiceManagementApp.dtos.client.ClientDTO;
import ro.itschool.InvoiceManagementApp.dtos.client.ClientListDTO;
import ro.itschool.InvoiceManagementApp.entities.ClientEntity;
import ro.itschool.InvoiceManagementApp.entities.UserEntity;
import ro.itschool.InvoiceManagementApp.services.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@Validated
@Slf4j
public class UserResource {

    @Autowired
    private UserService userService;

    @Operation(summary = "Getting all clients")
    @GetMapping()
    public ResponseEntity<UserListDTO> getAll() {
        log.info("getting all clients from database");
        Iterable<UserEntity> dbUser = this.userService.findAll();
        List<UserDTO> foundUsersList = new ArrayList<>();

        for (UserEntity userEntity : dbUser) {
            foundUsersList.add(UserDTO.from(userEntity));
        }

        UserListDTO userListDTO = new UserListDTO(foundUsersList);
        return new ResponseEntity<>(userListDTO, HttpStatus.OK);
    }
}
