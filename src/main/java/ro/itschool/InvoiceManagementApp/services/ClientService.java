package ro.itschool.InvoiceManagementApp.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.itschool.InvoiceManagementApp.dtos.ClientDTO;
import ro.itschool.InvoiceManagementApp.dtos.UserDTO;
import ro.itschool.InvoiceManagementApp.entities.ClientEntity;
import ro.itschool.InvoiceManagementApp.entities.HousingTypeEnum;
import ro.itschool.InvoiceManagementApp.entities.UserEntity;
import ro.itschool.InvoiceManagementApp.exceptions.InexistentResourceException;
import ro.itschool.InvoiceManagementApp.repositories.ClientRepository;
import ro.itschool.InvoiceManagementApp.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;


    public ClientEntity findById(int id) throws InexistentResourceException {
        return this.clientRepository.findById(id).orElseThrow(() -> new InexistentResourceException("Client does not exist", id));
    }

    public Iterable<ClientEntity> findAll() {
        return clientRepository.findAll();
    }


    @Transactional
    public ClientEntity add(ClientDTO clientDTO, UserDTO userDTO) {
        log.info("Adding new client");

        UserEntity newUser = new UserEntity();

        newUser.setName(userDTO.getName());
        newUser.setEmail(userDTO.getEmail());
        newUser.setPassword(userDTO.getPassword());
        newUser.setCity(userDTO.getCityName());
        newUser.setCounty(userDTO.getCountyName());
        newUser.setAddress(userDTO.getAddress());

        UserEntity savedUser = userRepository.save(newUser);

        ClientEntity newClient = new ClientEntity();
        newClient.setHousingType(clientDTO.getHousingType());
        newClient.setUserEntity(newUser);


        ClientEntity savedClient = clientRepository.save(newClient);

        //TODO: Test the both versions to see which works

//        ClientEntity newClientEntity = new ClientEntity();
//        newClientEntity.setName(clientDTO.getName());
//        newClientEntity.setEmail(clientDTO.getEmail());
//        newClientEntity.setPassword(clientDTO.getPassword());
//        newClientEntity.setCity(clientDTO.getCity());
//        newClientEntity.setCounty(clientDTO.getCounty());
//        newClientEntity.setAddress(clientDTO.getAddress());
//        newClientEntity.setHousingType(clientDTO.getHousingType());
//
//        ClientEntity savedNewClientEntity = clientRepository.save(newClientEntity);
//        return savedNewClientEntity;
        log.info("Added new client");
        return savedClient;

    }

    @Transactional
    public List<ClientEntity> search(String name, String email, HousingTypeEnum housingTypeEnum) {
        Iterable<ClientEntity> dbClients = clientRepository.findAll();

        List<ClientEntity> foundClients = new ArrayList<>();

        for (ClientEntity clientEntity : dbClients) {
            foundClients.add(clientEntity);
        }
        //TODO : To implement
        if (name != null && !name.isEmpty()) {
            List<ClientEntity> clientByName = (List<ClientEntity>) this.userRepository.findByNameIgnoreCase(name);
            foundClients.retainAll(clientByName);
        }

        if (email != null && !email.isEmpty()) {
            List<ClientEntity> clientByEmail = (List<ClientEntity>) this.userRepository.findByEmailIgnoreCase(email);
            foundClients.retainAll(clientByEmail);
        }

        if (housingTypeEnum != null && !housingTypeEnum.name().isEmpty()) { //TODO: ask if i should use .toString
            List<ClientEntity> clientByHousingType = (List<ClientEntity>) this.clientRepository.findByHousingType(housingTypeEnum);
            foundClients.retainAll(clientByHousingType);
        }

        return foundClients;
    }

    public ClientEntity updateCredentials(int id, ClientDTO clientDTO) throws InexistentResourceException {
        Optional<ClientEntity> foundUser = this.clientRepository.findById(id);

        if (!foundUser.isPresent()) {
            throw new InexistentResourceException("Client does not exist", id);
        }

        ClientEntity clientUpdate = foundUser.get();

        clientUpdate.setName(clientDTO.getName());
        clientUpdate.setEmail(clientDTO.getEmail());
        clientUpdate.setPassword(clientDTO.getPassword());
        this.clientRepository.save(clientUpdate);
        return clientUpdate;
    }

    public ClientEntity updateCredentialsPartial(int id, ClientDTO clientDTO) throws InexistentResourceException {
        Optional<ClientEntity> foundClient = this.clientRepository.findById(id);

        if (!foundClient.isPresent()) {
            throw new InexistentResourceException("Client does not exist", id);
        }

        ClientEntity clientPartialUpdate = foundClient.get();

        if (clientDTO.getName() != null) {
            clientPartialUpdate.setName(clientDTO.getName());
        }

        if(clientDTO.getEmail()!=null){
            clientPartialUpdate.setName(clientDTO.getEmail());
        }

        if(clientDTO.getPassword()!=null){
            clientPartialUpdate.setPassword(clientPartialUpdate.getPassword());
        }

        this.clientRepository.save(clientPartialUpdate);
        return clientPartialUpdate;
    }

    public void delete(int id) {
        this.clientRepository.deleteById(id);
    }
}
