package ro.itschool.InvoiceManagementApp.repositories;

import org.springframework.data.repository.CrudRepository;
import ro.itschool.InvoiceManagementApp.entities.UtilityTypeEntity;
import ro.itschool.InvoiceManagementApp.entities.UtilityTypeNameEnum;

import java.util.List;
import java.util.Optional;

public interface UtilityTypeRepository extends CrudRepository<UtilityTypeEntity, Integer> {
//
    Optional<UtilityTypeEntity> findByName(UtilityTypeNameEnum name);
}
