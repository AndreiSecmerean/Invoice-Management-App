package ro.itschool.InvoiceManagementApp.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.itschool.InvoiceManagementApp.dtos.client.ClientDTO;
import ro.itschool.InvoiceManagementApp.entities.*;
import ro.itschool.InvoiceManagementApp.exceptions.InexistentResourceException;
import ro.itschool.InvoiceManagementApp.repositories.CityRepository;
import ro.itschool.InvoiceManagementApp.repositories.ClientRepository;
import ro.itschool.InvoiceManagementApp.repositories.CountyRepository;
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

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CountyRepository countyRepository;


    public ClientEntity findById(int index) throws InexistentResourceException {
        // return (ClientEntity) this.userRepository.findById(index).orElseThrow(() -> new InexistentResourceException("Client does not exist", id));
        return this.clientRepository.findById(index).orElseThrow(() -> new InexistentResourceException("Client does not exist", index));
    }

    public Iterable<ClientEntity> findAll() {
        return this.clientRepository.findAll();
    }


    @Transactional
    public ClientEntity add(ClientDTO clientDTO) {
        log.info("Adding new client");
        CityEntity city = this.cityRepository.findByNameIgnoreCase(clientDTO.getCity());
        CountyEntity county = this.countyRepository.findByNameIgnoreCase((clientDTO.getCounty()));

        log.info("Saving new ClientEntity for new db");
        ClientEntity newClientEntity = new ClientEntity();

        newClientEntity.setName(clientDTO.getName());
        newClientEntity.setEmail(clientDTO.getEmail());
        newClientEntity.setPassword(clientDTO.getPassword());
        newClientEntity.setHousingType(clientDTO.getHousingType());
        newClientEntity.setCity(city);
        newClientEntity.setCounty(county);
        newClientEntity.setAddress(clientDTO.getAddress());

        log.info("Saved new ClientEntity for new db");
        log.info("Saving client to db");

        ClientEntity savedClient = this.clientRepository.save(newClientEntity);

        log.info("Saved client to db");
        System.out.println(savedClient);

        return savedClient;


    }

    public List<ClientEntity> searchClients(String searchTerm) {
        return this.clientRepository.searchClients(searchTerm);
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

        if (clientDTO.getEmail() != null) {
            clientPartialUpdate.setName(clientDTO.getEmail());
        }

        if (clientDTO.getPassword() != null) {
            clientPartialUpdate.setPassword(clientPartialUpdate.getPassword());
        }

        this.clientRepository.save(clientPartialUpdate);
        return clientPartialUpdate;
    }

    public void delete(Integer index) {
        this.clientRepository.deleteById(index);
    }



}
