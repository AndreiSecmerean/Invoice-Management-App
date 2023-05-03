package ro.itschool.InvoiceManagementApp.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.itschool.InvoiceManagementApp.dtos.client.ClientDTO;
import ro.itschool.InvoiceManagementApp.dtos.client.ClientListDTO;
import ro.itschool.InvoiceManagementApp.entities.ClientEntity;
import ro.itschool.InvoiceManagementApp.repositories.ClientRepository;
import ro.itschool.InvoiceManagementApp.services.ClientService;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/clients")
@Validated
@Slf4j
public class ClientResource {


    @Autowired
    private ClientService clientService;


    @GetMapping
    public ResponseEntity<ClientListDTO> getAll(){
        log.info("getting all clients from database");
        Iterable<ClientEntity> dbClients = this.clientService.findAll();
        List<ClientDTO> foundClientsList = new ArrayList<>();

        for(ClientEntity clientEntity : dbClients){
            foundClientsList.add(ClientDTO.from(clientEntity));
        }

        ClientListDTO clientListDTO = new ClientListDTO(foundClientsList);
        return new ResponseEntity<>(clientListDTO, HttpStatus.OK);
    }
}
//TODO: implement CRUD operations
