package ro.itschool.InvoiceManagementApp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ro.itschool.InvoiceManagementApp.dtos.invoice.InvoiceDTO;
import ro.itschool.InvoiceManagementApp.dtos.invoice.SettingInvoiceDTO;
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

    @Operation(summary = "Getting invoices by: " +
            "email" +
            "city" +
            "county" +
            "housing_type")
    @GetMapping("/getByClient/search={param}")
    public ResponseEntity<List<InvoiceDTO>> searchByClient(@PathVariable("param")String searchTerm){
        log.info("Searching invoices by client(s)");
        List<InvoiceEntity> foundInvoices = this.invoiceService.searchByClient(searchTerm);
        List<InvoiceDTO> foundInvoicesDTO = InvoiceDTO.from(foundInvoices);
        log.info("Found client(s)");
        return new ResponseEntity<>(foundInvoicesDTO, HttpStatus.FOUND);
    }

    @GetMapping("/getByUtilityProvider/search={param}")
    public ResponseEntity<List<InvoiceDTO>> searchByUtilityProvider(@PathVariable("param")String searchTerm){
        log.info("Searching by utility providers");
        List<InvoiceEntity> foundInvoices = this.invoiceService.searchByUtilityProvider(searchTerm);
        List<InvoiceDTO> foundInvoicesInvoicesDTO = InvoiceDTO.from(foundInvoices);
        return new ResponseEntity<>(foundInvoicesInvoicesDTO,HttpStatus.FOUND);
    }

    @PostMapping("/register")
    public ResponseEntity<InvoiceDTO> register(@RequestBody SettingInvoiceDTO settingInvoiceDTO){
        try {
            log.info("Adding new invoice in database");
            InvoiceEntity invoiceEntity = this.invoiceService.add(settingInvoiceDTO);
            InvoiceDTO newInvoiceDTO = InvoiceDTO.from(invoiceEntity);
            log.info("Added new invoice in database");
            return new ResponseEntity<>(newInvoiceDTO, HttpStatus.OK);
        }catch (InexistentResourceException e){
            log.error(e.getMessage());
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/updateData/id={index}")
    public ResponseEntity<InvoiceDTO> updateData(@PathVariable("index")Integer index,@RequestBody SettingInvoiceDTO settingInvoiceDTO){
        try {
            log.info("Updating wrong data in invoice");
            InvoiceEntity invoiceUpdate = this.invoiceService.updateData(index,settingInvoiceDTO);
            InvoiceDTO invoiceUpdateDTO = InvoiceDTO.from(invoiceUpdate);
            log.info("Updated wrong data in invoice successfully");
            return new ResponseEntity<>(invoiceUpdateDTO,HttpStatus.OK);
        } catch (InexistentResourceException e) {
            log.error(e.getMessage()+e.getId());
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/id={index}")
    public ResponseEntity<String> delete(@PathVariable("index")Integer index){
        String response;
        try {
            this.invoiceService.delete(index);
            response = "Deleted invoice successfully, id: "+index;
            log.info(response);
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (InexistentResourceException e) {
            response = e.getMessage()+e.getId();
            log.error(response);
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
    }
}
//TODO: Implement CRUD
