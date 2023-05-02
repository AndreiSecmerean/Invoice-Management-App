package ro.itschool.InvoiceManagementApp.repositories;

import org.springframework.data.repository.CrudRepository;
import ro.itschool.InvoiceManagementApp.entities.ClientEntity;
import ro.itschool.InvoiceManagementApp.entities.HousingTypeEnum;

import java.util.List;

public interface ClientRepository extends CrudRepository<ClientEntity, Integer> {
    List<ClientEntity> findByHousingType(HousingTypeEnum housingType);
}
