package ro.itschool.InvoiceManagementApp.repositories;

import org.springframework.data.repository.CrudRepository;
import ro.itschool.InvoiceManagementApp.entities.CityEntity;

import java.util.Optional;

public interface CityRepository extends CrudRepository<CityEntity, Integer> {
    Optional<CityEntity> findByNameIgnoreCase(String cityName);
}
