package ro.itschool.InvoiceManagementApp.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.itschool.InvoiceManagementApp.entities.UserEntity;
import ro.itschool.InvoiceManagementApp.exceptions.InexistentResourceException;
import ro.itschool.InvoiceManagementApp.repositories.UserRepository;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity findById(int id) throws InexistentResourceException {
        return this.userRepository.findById(id).orElseThrow(()->new InexistentResourceException("User does not exist!",id));
    }

    public Iterable<UserEntity> findAll(){
        return userRepository.findAll();
    }

}
