package ro.itschool.InvoiceManagementApp.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ro.itschool.InvoiceManagementApp.dtos.utilityProvider.UtilityProviderDTO;
import ro.itschool.InvoiceManagementApp.entities.UtilityProviderEntity;
import ro.itschool.InvoiceManagementApp.entities.UtilityTypeNameEnum;
import ro.itschool.InvoiceManagementApp.exceptions.DuplicateResourceException;
import ro.itschool.InvoiceManagementApp.exceptions.InexistentResourceException;
import ro.itschool.InvoiceManagementApp.services.users.UtilityProviderService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/utp")
@Validated
@Slf4j
public class UtilityProviderResource {

    @Autowired
    private UtilityProviderService utilityProviderService;

    @GetMapping("/get")
    public ResponseEntity<List<UtilityProviderDTO>> getAll() {
        log.info("Getting all utility providers form database");


        try {
            Iterable<UtilityProviderEntity> dbUTP = this.utilityProviderService.findAll();

            List<UtilityProviderDTO> foundUTP = new ArrayList<>();

            log.debug("Saving found utps to a list");
            for (UtilityProviderEntity utilityProvider : dbUTP) {
                foundUTP.add(UtilityProviderDTO.from(utilityProvider));
            }
            if (foundUTP.isEmpty()) {
                throw new InexistentResourceException("No utility providers found in database", null);
            }
            log.info("Retrieved all utility providers from database");
            return new ResponseEntity<>(foundUTP, HttpStatus.FOUND);
        } catch (InexistentResourceException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get/id={index}")
    public ResponseEntity<UtilityProviderDTO> getById(@Min(1) @PathVariable("index") Integer index) {
        try {
            log.info("Retrieving utility provider from database by id");
            UtilityProviderEntity foundUTP = this.utilityProviderService.findById(index);
            log.info("Retriev utility provider with id: "+index+" successfully!");
            return new ResponseEntity<>(UtilityProviderDTO.from(foundUTP),HttpStatus.FOUND);
        }catch (InexistentResourceException e){
            log.error(e.getMessage()+e.getId());
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search={param}")
    public ResponseEntity<List<UtilityProviderDTO>> search(@PathVariable("param")String searchTerm){
        try {
            log.info("Searching the database for utility providers by: "+searchTerm);
            Optional<List<UtilityProviderEntity>> foundUTPs = this.utilityProviderService.search(searchTerm);
            if(foundUTPs.isEmpty()){
                throw new InexistentResourceException("No utility providers were found",null);
            }
            log.debug("Saving found UTP to a DTOlist");
            List<UtilityProviderDTO> foundUtilityProviderDTOS = UtilityProviderDTO.from(new ArrayList<>(foundUTPs.get()));

            log.info("Found utility providers");
            return new ResponseEntity<>(foundUtilityProviderDTOS, HttpStatus.FOUND);
        } catch (InexistentResourceException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



    @PostMapping("/register/utilityTypeName={name}")
    public ResponseEntity<UtilityProviderDTO> register(@PathVariable("name")UtilityTypeNameEnum utilityTypeName, @Valid @RequestBody UtilityProviderDTO utilityProviderDTO){

        UtilityProviderEntity utilityProvider = this.utilityProviderService.add(utilityTypeName, utilityProviderDTO);
        return new ResponseEntity<>(UtilityProviderDTO.from(utilityProvider),HttpStatus.CREATED);
    }

    @PutMapping("/updateCredentials/id={index}/utilityTypeName={name}")
    public ResponseEntity<UtilityProviderDTO> updateCredentials(@PathVariable("index") Integer index,@PathVariable("name") UtilityTypeNameEnum utilityTypeName, @Valid @RequestBody UtilityProviderDTO utilityProviderDTO){
        try {
            UtilityProviderEntity foundUTP = this.utilityProviderService.updateCredentials(index, utilityTypeName,utilityProviderDTO);
            return new ResponseEntity<>(UtilityProviderDTO.from(foundUTP),HttpStatus.OK);
        } catch (InexistentResourceException e) {
            log.error(e.getMessage()+e.getId());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @PatchMapping("/updateCredentialsPartial/id={index}")
    public ResponseEntity<UtilityProviderDTO> updateCredentialsPartial(@PathVariable("index") Integer index, @Valid @RequestBody UtilityProviderDTO utilityProviderDTO){
        try {
            UtilityProviderEntity foundUTP = this.utilityProviderService.updateCredentialsPartial(index, utilityProviderDTO);
            return new ResponseEntity<>(UtilityProviderDTO.from(foundUTP),HttpStatus.OK);
        } catch (InexistentResourceException e) {
            log.error(e.getMessage()+e.getId());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (DuplicateResourceException e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/assignType/idProvider={indexProvider}/idType={indextype}")
    public ResponseEntity<String> assignType(@PathVariable("indexProvider")Integer indexProvider,@PathVariable("indexType")Integer indexType){
        String response;

        try {
            this.utilityProviderService.assignType(indexProvider,indexType);
            response = "Successfully assigned utility type to provider";
            log.info(response);
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (InexistentResourceException e) {
            response = "Failed to assign utility type to provider";
            log.error(response);
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/delete/id={index}")
    public ResponseEntity<String> delete(@PathVariable("index") Integer index){
        String response;

        try {
            this.utilityProviderService.delete(index);
            response="Deleted utility provider with id: "+index+" successfully";
            log.info(response);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (InexistentResourceException e){
            response = e.getMessage()+e.getId();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
