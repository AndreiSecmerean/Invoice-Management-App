package ro.itschool.InvoiceManagementApp.services.users;

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
;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CountyRepository countyRepository;



    //For admin:
    public ClientEntity findById( int index) throws InexistentResourceException {
        return this.clientRepository.findById(index).orElseThrow(() -> new InexistentResourceException("Client does not exist", index));
    }

    public Iterable<ClientEntity> findAll() {
        return this.clientRepository.findAll();
    }

    public Optional<List<ClientEntity>> searchClients(String searchTerm) {
        return this.clientRepository.searchClients(searchTerm);
    }



    //For Client And Admin:
    @Transactional
    public ClientEntity add(ClientDTO clientDTO) {
        log.info("Adding new client");
        CityEntity city = this.cityRepository.findByNameIgnoreCase(clientDTO.getCity());
        CountyEntity county = this.countyRepository.findByNameIgnoreCase(clientDTO.getCounty());

        log.debug("Saving new ClientEntity to db");
        ClientEntity newClientEntity = new ClientEntity();

        newClientEntity.setName(clientDTO.getName());
        newClientEntity.setEmail(clientDTO.getEmail());
        newClientEntity.setPassword(clientDTO.getPassword());
        newClientEntity.setHousingType(clientDTO.getHousingType());
        newClientEntity.setCity(city);
        newClientEntity.setCounty(county);
        newClientEntity.setAddress(clientDTO.getAddress());

        log.debug("Saving client to db");

        ClientEntity savedClient = this.clientRepository.save(newClientEntity);

        log.info("Added client to db");

        return savedClient;


    }


    @Transactional
    public ClientEntity updateCredentials(Integer id, ClientDTO clientDTO) throws InexistentResourceException {

        ClientEntity clientUpdate = findById(id);

        clientUpdate.setName(clientDTO.getName());
        clientUpdate.setEmail(clientDTO.getEmail());
        clientUpdate.setPassword(clientDTO.getPassword());

        this.clientRepository.save(clientUpdate);
        return clientUpdate;
    }

    public ClientEntity updateCredentialsPartial(Integer id, ClientDTO clientDTO) throws InexistentResourceException {
        Optional<ClientEntity> foundClient = this.clientRepository.findById(id);

        if (foundClient.isEmpty()) {
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

    public void delete(Integer id) throws InexistentResourceException {
        this.clientRepository.deleteById(findById(id).getId());

    }
}
