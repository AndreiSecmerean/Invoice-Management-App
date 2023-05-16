package ro.itschool.InvoiceManagementApp.repositories;

import org.springframework.data.repository.CrudRepository;
import ro.itschool.InvoiceManagementApp.entities.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {
    List<UserEntity> findByNameIgnoreCase(String name);
    UserEntity findByEmailIgnoreCase(String email);

    List<UserEntity> findByCityNameIgnoreCase(String cityName);
    List<UserEntity> findByCountyNameIgnoreCase(String userName);
    Optional<UserEntity> findByEmail(String email);
}
