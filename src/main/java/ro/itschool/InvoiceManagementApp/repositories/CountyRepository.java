package ro.itschool.InvoiceManagementApp.repositories;

import org.springframework.data.repository.CrudRepository;
import ro.itschool.InvoiceManagementApp.entities.CountyEntity;

import java.util.List;

public interface CountyRepository extends CrudRepository<CountyEntity, Integer> {

    List<CountyEntity> findByNameIgnoreCase(String countyName);
}
