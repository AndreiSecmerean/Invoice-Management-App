package ro.itschool.InvoiceManagementApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import ro.itschool.InvoiceManagementApp.repositories.ClientRepository;
import ro.itschool.InvoiceManagementApp.services.ClientService;

//TODO: implement CRUD operations
public class ClientResource {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientService clientService;
}
