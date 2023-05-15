package ro.itschool.InvoiceManagementApp.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.itschool.InvoiceManagementApp.dtos.invoice.InvoiceDTO;
import ro.itschool.InvoiceManagementApp.entities.InvoiceEntity;
import ro.itschool.InvoiceManagementApp.exceptions.InexistentResourceException;
import ro.itschool.InvoiceManagementApp.services.InvoiceService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/invoice")
@Validated
@Slf4j
public class InvoiceResource {
    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/get")
    public ResponseEntity<List<InvoiceDTO>> getAllInvoices() {
        log.info("Getting all invoices from the database");

        try {
            Iterable<InvoiceEntity> dbInvoices = this.invoiceService.findAll();

            List<InvoiceDTO> foundInvoices = new ArrayList<>();

            log.debug("Saving found invoices to a list");
            for (InvoiceEntity invoice : dbInvoices) {
                foundInvoices.add(InvoiceDTO.from(invoice));
            }

            if (foundInvoices.isEmpty()) {
                throw new InexistentResourceException("No invoices found in the database", null);
            }

            log.info("Retrieved all invoices from the database");
            return new ResponseEntity<>(foundInvoices, HttpStatus.OK);
        } catch (InexistentResourceException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get/id={index}")
    public ResponseEntity<InvoiceDTO> getById(@PathVariable("index")Integer index){
        log.info("Retrieving invoice by id from database");
        try {
            InvoiceEntity foundInvoice = this.invoiceService.findById(index);
            System.out.println(foundInvoice);
            log.info("Retrieved invoice successfully!");
            return new ResponseEntity<>(InvoiceDTO.from(foundInvoice),HttpStatus.FOUND);
        } catch (InexistentResourceException e) {
            log.error(e.getMessage()+e.getId());
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

}
//TODO: Implement CRUD
