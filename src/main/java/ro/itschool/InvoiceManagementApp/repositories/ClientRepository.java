package ro.itschool.InvoiceManagementApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ro.itschool.InvoiceManagementApp.entities.ClientEntity;
import ro.itschool.InvoiceManagementApp.entities.HousingTypeEnum;

import java.util.List;

public interface ClientRepository extends JpaRepository<ClientEntity, Integer> {

    ClientEntity findByHousingType(HousingTypeEnum housingType);

    List<ClientEntity> findByName(String name);
    List<ClientEntity> findByEmail(String email);
    List<ClientEntity> findByCity(String city);
    List<ClientEntity> findByCounty(String county);

    @Query("SELECT c FROM ClientEntity c WHERE c.name LIKE %:searchTerm% OR c.email LIKE %:searchTerm% OR c.city LIKE %:searchTerm% OR c.county LIKE %:searchTerm% OR c.housingType LIKE %:searchTerm%")
    List<ClientEntity> searchClients(@Param("searchTerm") String searchTerm);
}
