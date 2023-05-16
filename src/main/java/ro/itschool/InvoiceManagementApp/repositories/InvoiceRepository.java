package ro.itschool.InvoiceManagementApp.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ro.itschool.InvoiceManagementApp.entities.InvoiceEntity;

import java.util.List;

public interface InvoiceRepository extends CrudRepository<InvoiceEntity, Integer> {


    //    @Query("SELECT i FROM InvoiceEntity i JOIN i.client c WHERE c.name LIKE %:searchTerm% OR c.email LIKE %:searchTerm% OR c.city LIKE %:searchTerm% OR c.county LIKE %:searchTerm% OR c.housingType LIKE %:searchTerm%")
//    List<InvoiceEntity> searchInvoicesByClient(@Param("searchTerm") String searchTerm);
    @Query("SELECT i FROM InvoiceEntity i WHERE i.client.email LIKE %:searchTerm% OR i.client.city.name LIKE %:searchTerm% OR i.client.county.name LIKE %:searchTerm% OR i.client.housingType LIKE %:searchTerm%")
    List<InvoiceEntity> searchInvoicesByClient(@Param("searchTerm") String searchTerm);
//    i.client.name LIKE %:searchTerm% OR

    @Query("SELECT i FROM InvoiceEntity i JOIN i.utilityProvider u WHERE u.name LIKE %:searchTerm% OR u.email LIKE %:searchTerm% OR u.city LIKE %:searchTerm% OR u.county LIKE %:searchTerm%")
    List<InvoiceEntity> searchInvoicesByUtilityProvider(@Param("searchTerm") String searchTerm);

}
