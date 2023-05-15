package ro.itschool.InvoiceManagementApp.repositories;

import org.springframework.data.repository.CrudRepository;
import ro.itschool.InvoiceManagementApp.entities.CityEntity;

public interface CityRepository extends CrudRepository<CityEntity, Integer> {
    CityEntity findByNameIgnoreCase(String cityName);
}
