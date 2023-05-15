package ro.itschool.InvoiceManagementApp.repositories;

import org.springframework.data.repository.CrudRepository;
import ro.itschool.InvoiceManagementApp.entities.CountyEntity;

public interface CountyRepository extends CrudRepository<CountyEntity, Integer> {

    CountyEntity findByNameIgnoreCase(String countyName);
}
