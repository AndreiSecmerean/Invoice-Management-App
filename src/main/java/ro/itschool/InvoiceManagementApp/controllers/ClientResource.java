package ro.itschool.InvoiceManagementApp.controllers;

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
import ro.itschool.InvoiceManagementApp.exceptions.InexistentResourceException;
import ro.itschool.InvoiceManagementApp.services.users.ClientService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/*
Description:
This is the controller for the client. The client needs to do a request
regarding only his opperations(see bellow) and will not have access to
operations of other user types

The client has access only to the following operations:

-create a new client
-see his invoices
-update his log-in credentials
-delete his/hers account
 */

@RestController
@RequestMapping("/api/clients")
@Validated
@Slf4j
public class ClientResource {


    @Autowired
    private ClientService clientService;

    @Operation(summary = "Getting all clients")
    @GetMapping("/get")
    public ResponseEntity<ClientListDTO> getAll() {
        log.info("getting all clients from database");
        Iterable<ClientEntity> dbClients = this.clientService.findAll();
        List<ClientDTO> foundClientsList = new ArrayList<>();

        for (ClientEntity clientEntity : dbClients) {
            foundClientsList.add(ClientDTO.from(clientEntity));
        }

        ClientListDTO clientListDTO = new ClientListDTO(foundClientsList);
        return new ResponseEntity<>(clientListDTO, HttpStatus.FOUND);
    }

    @Operation(summary = "Getting clients by their index")
    @GetMapping("/get/id={index}")
    public ResponseEntity<ClientDTO> getById(@Min(1) @PathVariable("index") Integer index) {
        log.info("getting client by index");

        ClientEntity foundClient = null;
        try {
            foundClient = this.clientService.findById(index);
            log.debug("found client");

            ClientDTO clientDTO = ClientDTO.from(foundClient);

            log.debug("saved found client in a ClientDTO");

            return new ResponseEntity<>(clientDTO, HttpStatus.FOUND);
        } catch (InexistentResourceException e) {
            log.error(e.getMessage() + e.getId());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }


    }


    @PostMapping("/register")
    public ResponseEntity<ClientDTO> register(@Valid @RequestBody ClientDTO clientDTO) {
        try {
            ClientEntity clientEntity = this.clientService.add(clientDTO);
            return new ResponseEntity<>(ClientDTO.from(clientEntity), HttpStatus.CREATED);
        } catch (InexistentResourceException e) {
            log.error(e.getMessage() + e.getId());
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }

    }


    @GetMapping("/searchClient/{param}")
    public ResponseEntity<List<ClientDTO>> search(@PathVariable("param") String searchTerm) {
        try {

            Optional<List<ClientEntity>> searchClient = this.clientService.searchClients(searchTerm);
            if (searchClient.isEmpty()) {
                throw new InexistentResourceException("Client does not exist", null);
            }
            List<ClientDTO> clientDTOS = ClientDTO.from(new ArrayList<>(searchClient.get()));
            return new ResponseEntity<>(clientDTOS, HttpStatus.FOUND);
        } catch (InexistentResourceException e) {
            throw new RuntimeException(e);
        }
    }

    @PatchMapping("/updateCredentials/{index}")
    public ResponseEntity<ClientDTO> updateCredentials(@PathVariable("index") Integer index, @Valid @RequestBody ClientDTO clientDTO) {
        try {
            ClientEntity updateClient = this.clientService.updateCredentials(index, clientDTO);
            log.info("Updated credentials for user with id: " + index + " successfully");
            return new ResponseEntity<>(ClientDTO.from(updateClient), HttpStatus.OK);
        } catch (InexistentResourceException e) {
            String response = e.getMessage() + e.getId();
            log.error(response);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/id={index}")
    public ResponseEntity<String> delete(@PathVariable("index") Integer index) {
        String response;

        try {
            this.clientService.delete(index);
            response = "Client with the id " + index + " has been deleted";
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (InexistentResourceException e) {
            response = e.getMessage() + e.getId();
            log.error(response);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
    }
}
