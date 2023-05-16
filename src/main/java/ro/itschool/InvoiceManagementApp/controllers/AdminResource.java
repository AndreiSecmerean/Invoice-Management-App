package ro.itschool.InvoiceManagementApp.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ro.itschool.InvoiceManagementApp.dtos.admin.AdminDTO;
import ro.itschool.InvoiceManagementApp.dtos.admin.AdminListDTO;
import ro.itschool.InvoiceManagementApp.entities.AdminEntity;
import ro.itschool.InvoiceManagementApp.exceptions.InexistentResourceException;
import ro.itschool.InvoiceManagementApp.services.users.AdminService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
Description:
The admin has control over all the data of the database

 */
@RestController
@RequestMapping("/api/admin")
@Validated
@Slf4j
public class AdminResource {
    @Autowired
    private AdminService adminService;

    @GetMapping("/get")
    public ResponseEntity<AdminListDTO> getAll() {
        log.info("Getting all admins from database");
        try {
            Iterable<AdminEntity> dbAdmin = this.adminService.findAll();
            List<AdminDTO> foundAdminList = new ArrayList<>();

            for (AdminEntity adminEntity : dbAdmin) {
                foundAdminList.add(AdminDTO.from(adminEntity));
            }
            if (foundAdminList.isEmpty()) {
                throw new InexistentResourceException("No admins found in database!!!", null);
            }

            AdminListDTO adminListDTO = new AdminListDTO(foundAdminList);
            log.info("Retrieved admins from database");
            return new ResponseEntity<>(adminListDTO, HttpStatus.FOUND);
        } catch (InexistentResourceException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/get/id={index}")
    public ResponseEntity<AdminDTO> getById(@Min(1) @PathVariable("index") Integer index) {
        log.info("Retrieving admin from database with id: " + index);
        try {
            AdminEntity foundAdmin = this.adminService.findById(index);
            log.info("Retrieved admin with id: " + index + " successfully!");
            return new ResponseEntity<>(AdminDTO.from(foundAdmin),HttpStatus.FOUND);
        } catch (InexistentResourceException e) {
            log.error("Failed to retrieve Client with id: "+index);
            log.error("Cause: Client does not exist");
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search={param}")
    public ResponseEntity<List<AdminDTO>> search(@PathVariable("param") String searchTerm){
        List<AdminEntity> foundAdmins = this.adminService.search(searchTerm);

        try {
            if(foundAdmins.isEmpty()){
                throw new InexistentResourceException("Admin does not exist",null);
            }
            List<AdminDTO> foundAdminDTOS = AdminDTO.from(foundAdmins);
            return new ResponseEntity<>(foundAdminDTOS, HttpStatus.FOUND);
        }catch (InexistentResourceException e){
            log.error(e.getMessage()+searchTerm);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<AdminDTO> register(@Valid @RequestBody AdminDTO adminDTO){
        AdminEntity admin = this.adminService.add(adminDTO);
        return new ResponseEntity<>(AdminDTO.from(admin),HttpStatus.CREATED);
    }

    @PatchMapping("/updateCredentials/id={index}")
    public ResponseEntity<AdminDTO> updateCredentials(@PathVariable("index")Integer index, @Valid @RequestBody AdminDTO adminDTO){
        try {
            AdminEntity admin = this.adminService.updateCredentials(index,adminDTO);
            log.info("Updated credentials for admin with id: "+index+" successfully");
            return new ResponseEntity<>(AdminDTO.from(admin),HttpStatus.OK);
        }catch (InexistentResourceException e){
            log.error(e.getMessage()+" "+e.getId());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/id={index}")
    public ResponseEntity<String> delete(@PathVariable("index") Integer index){
        String response;
        try {
            this.adminService.delete(index);
            response = "Admin with id: "+index+" has been successfully deleted!";
            log.info(response);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (InexistentResourceException e){
            response = e.getMessage()+e.getId();
            log.error(response);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
