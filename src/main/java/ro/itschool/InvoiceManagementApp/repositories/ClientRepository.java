package ro.itschool.InvoiceManagementApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.itschool.InvoiceManagementApp.entities.ClientEntity;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientEntity, Integer> {


    @Query("SELECT c FROM ClientEntity c WHERE c.name LIKE %:searchTerm% OR c.email LIKE %:searchTerm% OR c.city LIKE %:searchTerm% OR c.county LIKE %:searchTerm% OR c.housingType LIKE %:searchTerm%")
    Optional<List<ClientEntity>> searchClients(@Param("searchTerm") String searchTerm);

    ClientEntity findByName(String name);
}
