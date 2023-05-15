package ro.itschool.InvoiceManagementApp.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ro.itschool.InvoiceManagementApp.entities.AdminEntity;

import java.util.List;

public interface AdminRepository extends CrudRepository<AdminEntity, Integer> {
    @Query("SELECT a FROM AdminEntity a WHERE a.name LIKE %:searchTerm% OR a.email LIKE %:searchTerm% OR a.adminType LIKE %:searchTerm%")
    List<AdminEntity> searchAdmins(@Param("searchTerm") String searchTerm);

}
