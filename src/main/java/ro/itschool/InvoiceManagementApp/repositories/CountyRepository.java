package ro.itschool.InvoiceManagementApp.repositories;

import org.springframework.data.repository.CrudRepository;
import ro.itschool.InvoiceManagementApp.entities.CountyEntity;

import java.util.List;
import java.util.Optional;

public interface CountyRepository extends CrudRepository<CountyEntity, Integer> {

    Optional<CountyEntity> findByNameIgnoreCase(String countyName);
}
