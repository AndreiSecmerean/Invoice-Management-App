package ro.itschool.InvoiceManagementApp.repositories;

import org.springframework.data.repository.CrudRepository;
import ro.itschool.InvoiceManagementApp.entities.UserEntity;

import java.util.List;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {
    List<UserEntity> findByNameIgnoreCase(String userName);
    List<UserEntity> findByCityNameIgnoreCase(String cityName);
    List<UserEntity> findByCountyNameIgnoreCase(String userName);
}
