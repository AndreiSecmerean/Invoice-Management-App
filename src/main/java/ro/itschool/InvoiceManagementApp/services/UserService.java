package ro.itschool.InvoiceManagementApp.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.itschool.InvoiceManagementApp.dtos.UserDTO;
import ro.itschool.InvoiceManagementApp.entities.UserEntity;
import ro.itschool.InvoiceManagementApp.exceptions.InexistentResourceException;
import ro.itschool.InvoiceManagementApp.repositories.UserRepository;

import java.util.Optional;

/*
        !!!OUTDATED PLEASE REFER TO THE FOLLOWING SERVICES
        -CLIENT
        -ADMIN
        -UTILITY PROVIDER
 */

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;



    public UserEntity findById(int id) throws InexistentResourceException {
        return this.userRepository.findById(id).orElseThrow(() -> new InexistentResourceException("User does not exist!", id));
    }

    public Iterable<UserEntity> findAll() {
        return userRepository.findAll();
    }


    @Transactional
    public UserEntity add(UserDTO userDTO) {
        log.info("Adding new user");


        UserEntity newUser = new UserEntity();

        newUser.setName(userDTO.getName());
        newUser.setEmail(userDTO.getEmail());
        newUser.setPassword(userDTO.getPassword());
        newUser.setPassword(userDTO.getPassword());
        newUser.setCity(userDTO.getCityName());
        newUser.setCounty(userDTO.getCountyName());
        newUser.setAddress(userDTO.getAddress());


        UserEntity savedUser = userRepository.save(newUser);


        log.info("Added new user");
        return newUser;
    }

    @Transactional
    public UserEntity updateCredentials(int id, UserDTO userDTO) throws InexistentResourceException {
        Optional<UserEntity> optionalUser = userRepository.findById(id);

        if (!optionalUser.isPresent()) {
            throw new InexistentResourceException("User does not exist", id);
        }

        UserEntity userUpdate = optionalUser.get();

        userUpdate.setName(userDTO.getName());
        userUpdate.setEmail(userDTO.getEmail());
        userUpdate.setPassword(userDTO.getPassword());
        this.userRepository.save(userUpdate);
        return userUpdate;
    }

    @Transactional
    public UserEntity updateUsername(int id, UserDTO userDTO) throws InexistentResourceException {
        Optional<UserEntity> optionalUser = userRepository.findById(id);

        if (!optionalUser.isPresent()) {
            throw new InexistentResourceException("User does not exist", id);
        }

        UserEntity userUpdate = optionalUser.get();
        userUpdate.setName(userDTO.getName());

        this.userRepository.save(userUpdate);
        return userUpdate;
    }

    @Transactional
    public UserEntity updatePassword(int id, UserDTO userDTO) throws InexistentResourceException {
        Optional<UserEntity> optionalUser = userRepository.findById(id);

        if (!optionalUser.isPresent()) {
            throw new InexistentResourceException("User does not exist", id);
        }

        UserEntity userUpdate = optionalUser.get();
        userUpdate.setPassword(userDTO.getPassword());

        this.userRepository.save(userUpdate);
        return userUpdate;
    }

    public void delete(int id) {
        this.userRepository.deleteById(id);
    }

}
