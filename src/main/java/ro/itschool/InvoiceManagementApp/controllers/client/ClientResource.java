package ro.itschool.InvoiceManagementApp.controllers.client;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ro.itschool.InvoiceManagementApp.dtos.client.ClientDTO;
import ro.itschool.InvoiceManagementApp.dtos.client.ClientListDTO;
import ro.itschool.InvoiceManagementApp.entities.ClientEntity;
import ro.itschool.InvoiceManagementApp.entities.HousingTypeEnum;
import ro.itschool.InvoiceManagementApp.exceptions.InexistentResourceException;
import ro.itschool.InvoiceManagementApp.services.ClientService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/clients")
@Validated
@Slf4j
public class ClientResource {


    @Autowired
    private ClientService clientService;

    @Operation(summary = "Getting all clients")
    @GetMapping()
    public ResponseEntity<ClientListDTO> getAll() {
        log.info("getting all clients from database");
        Iterable<ClientEntity> dbClients = this.clientService.findAll();
        List<ClientDTO> foundClientsList = new ArrayList<>();

        for (ClientEntity clientEntity : dbClients) {
            foundClientsList.add(ClientDTO.from(clientEntity));
        }

        ClientListDTO clientListDTO = new ClientListDTO(foundClientsList);
        return new ResponseEntity<>(clientListDTO, HttpStatus.OK);
    }

    @Operation(summary = "Getting clients by their index")
    @GetMapping("/{index}")
    public ResponseEntity<ClientDTO> getById(@Min(1) @PathVariable("index") Integer index) throws InexistentResourceException {
        log.info("getting client by index");

        ClientEntity foundClient = this.clientService.findById(index);

        log.debug("found client");

        ClientDTO clientDTO = ClientDTO.from(foundClient);

        log.debug("saved found client in a ClientDTO");

        return new ResponseEntity<>(clientDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ClientDTO> create(@Valid @RequestBody ClientDTO clientDTO) {
        ClientEntity clientEntity = this.clientService.add(clientDTO);
        return new ResponseEntity<>(ClientDTO.from(clientEntity), HttpStatus.CREATED);
    }


    @GetMapping("/searchClient/{param}")
    public ResponseEntity<List<ClientDTO>> search(@PathVariable("param") String searchTerm) {
        List<ClientDTO> clientDTOS = ClientDTO.from(this.clientService.searchClients(searchTerm));

        return new ResponseEntity<>(clientDTOS, HttpStatus.FOUND);
    }

    @DeleteMapping("/{index}")
    public ResponseEntity<String> delete(@PathVariable("index") Integer index) {
        this.clientService.delete(index);
        String response = "Client with id " + index + " has been deleted!";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
//TODO: implement CRUD operations
