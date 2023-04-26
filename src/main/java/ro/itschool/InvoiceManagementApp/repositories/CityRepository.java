package ro.itschool.InvoiceManagementApp.repositories;

import org.springframework.data.repository.CrudRepository;
import ro.itschool.InvoiceManagementApp.entities.CityEntity;
import ro.itschool.InvoiceManagementApp.entities.CountyEntity;

import java.util.List;

public interface CityRepository extends CrudRepository<CityEntity, Integer> {
    List<CityEntity> findByNameIgnoreCase(String countyName);
}
