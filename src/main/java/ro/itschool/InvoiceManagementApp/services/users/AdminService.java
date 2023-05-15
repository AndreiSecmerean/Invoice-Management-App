package ro.itschool.InvoiceManagementApp.services.users;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.itschool.InvoiceManagementApp.dtos.admin.AdminDTO;
import ro.itschool.InvoiceManagementApp.entities.AdminEntity;
import ro.itschool.InvoiceManagementApp.exceptions.InexistentResourceException;
import ro.itschool.InvoiceManagementApp.repositories.AdminRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    public AdminEntity findById(Integer index) throws InexistentResourceException {
        return this.adminRepository.findById(index).orElseThrow(() -> new InexistentResourceException("Admin does not exist", index));
    }

    public Iterable<AdminEntity> findAll() {
        return this.adminRepository.findAll();
    }

    public List<AdminEntity> search(String searchTerm){
        return this.adminRepository.searchAdmins(searchTerm);
    }

    @Transactional
    public AdminEntity add(AdminDTO adminDTO) {
        log.info("Adding new admin");

        AdminEntity adminEntity = new AdminEntity();

        adminEntity.setName(adminDTO.getName());
        adminEntity.setEmail(adminDTO.getEmail());
        adminEntity.setPassword(adminDTO.getPassword());
        adminEntity.setAdminType(adminDTO.getAdminType());

        log.info("Saving new admin to database with the following details: "+adminEntity.toString());

        AdminEntity savedAdmin = this.adminRepository.save(adminEntity);

        log.info("Saved new admin to database with the following details: "+adminEntity.toString());

        return savedAdmin;
    }

    public AdminEntity updateCredentials(Integer id, AdminDTO adminDTO) throws InexistentResourceException {
        log.info("Searching database for admin with id: "+id);
        Optional<AdminEntity> foundAdmin = this.adminRepository.findById(id);

        if(foundAdmin.isEmpty()){
            throw new InexistentResourceException("Admin does not exist with id: ",id);
        }

        AdminEntity adminUpdate = foundAdmin.get();
        log.info("Found: "+adminUpdate.toString());

        if(adminDTO.getName() != null){
            adminUpdate.setName(adminDTO.getName());
            log.info("Changed name to: "+adminUpdate.getName());
        }
        if(adminDTO.getEmail() != null){
            adminUpdate.setEmail(adminDTO.getEmail());
            log.info("Changed email to: "+adminUpdate.getEmail());
        }
        if(adminDTO.getPassword() != null){
            adminUpdate.setPassword(adminDTO.getPassword());
            log.info("Changed password to: "+adminUpdate.getPassword());
        }

        AdminEntity savedAdmin = this.adminRepository.save(adminUpdate);
        log.info("Saved admin to database!");
        return savedAdmin;
    }

    public void delete(Integer id) throws InexistentResourceException {
        Optional<AdminEntity> foundAdmin = this.adminRepository.findById(id);

        if(foundAdmin.isEmpty()){
            throw new InexistentResourceException("Admin does not exist with id: ",id);
        }
        else {
            this.adminRepository.deleteById(id);
        }
    }
}
