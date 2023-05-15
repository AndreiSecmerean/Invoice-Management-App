package ro.itschool.InvoiceManagementApp.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ro.itschool.InvoiceManagementApp.entities.UtilityProviderEntity;

import java.util.List;
import java.util.Optional;

public interface UtilityProviderRepository extends CrudRepository<UtilityProviderEntity, Integer> {
    @Query("SELECT utp FROM UtilityProviderEntity utp WHERE utp.name LIKE %:searchTerm% OR utp.email LIKE %:searchTerm% OR  utp.city.name LIKE %:searchTerm% OR utp.county.name LIKE %:searchTerm%")
    Optional<List<UtilityProviderEntity>> searchUtilityProvider(@Param("searchTerm") String searchTerm);
}
