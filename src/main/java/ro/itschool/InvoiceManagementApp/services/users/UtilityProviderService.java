package ro.itschool.InvoiceManagementApp.services.users;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.itschool.InvoiceManagementApp.dtos.utilityProvider.UtilityProviderDTO;
import ro.itschool.InvoiceManagementApp.entities.*;
import ro.itschool.InvoiceManagementApp.exceptions.DuplicateResourceException;
import ro.itschool.InvoiceManagementApp.exceptions.InexistentResourceException;
import ro.itschool.InvoiceManagementApp.repositories.CityRepository;
import ro.itschool.InvoiceManagementApp.repositories.CountyRepository;
import ro.itschool.InvoiceManagementApp.repositories.UtilityProviderRepository;
import ro.itschool.InvoiceManagementApp.repositories.UtilityTypeRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UtilityProviderService {
    @Autowired
    private UtilityProviderRepository utilityProviderRepository;

    @Autowired
    private UtilityTypeRepository utilityTypeRepository;

    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private CountyRepository countyRepository;

    public Iterable<UtilityProviderEntity> findAll() {
        return this.utilityProviderRepository.findAll();
    }

    public UtilityProviderEntity findById(Integer index) throws InexistentResourceException {
        return this.utilityProviderRepository.findById(index).orElseThrow(() -> new InexistentResourceException("Utility provider does not exist ", index));
    }

    public Optional<List<UtilityProviderEntity>> search(String searchTerm) {
        return this.utilityProviderRepository.searchUtilityProvider(searchTerm);
    }


    @Transactional
    public UtilityProviderEntity add(UtilityTypeNameEnum utilityTypeName, UtilityProviderDTO utilityProviderDTO) {
        log.info("Adding new Utility Provider");

        CityEntity city = this.cityRepository.findByNameIgnoreCase(utilityProviderDTO.getCity());
        CountyEntity county = this.countyRepository.findByNameIgnoreCase(utilityProviderDTO.getCounty());


        Optional<UtilityTypeEntity> foundUtilityTypeEntityOptional = this.utilityTypeRepository.findByName(utilityTypeName);
        UtilityTypeEntity foundType = foundUtilityTypeEntityOptional.get();


        log.debug("Saving UTP to db");
        UtilityProviderEntity newUtilityProvider = new UtilityProviderEntity();


        newUtilityProvider.setName(utilityProviderDTO.getName());
        newUtilityProvider.setEmail(utilityProviderDTO.getEmail());
        newUtilityProvider.setPassword(utilityProviderDTO.getPassword());

        newUtilityProvider.getUtilityType().add(foundType);

        newUtilityProvider.setSustainabilityScore(utilityProviderDTO.getSustainabilityScore());

        newUtilityProvider.setCity(city);
        newUtilityProvider.setCounty(county);
        newUtilityProvider.setAddress(utilityProviderDTO.getAddress());

        log.debug("Saving UTP to db");

        UtilityProviderEntity savedUTP = this.utilityProviderRepository.save(newUtilityProvider);
        log.info("Added Utility provider to database");
        return savedUTP;
    }


    @Transactional
    public UtilityProviderEntity updateCredentials(Integer id, UtilityTypeNameEnum utilityTypeName, UtilityProviderDTO utilityProviderDTO) throws InexistentResourceException {
        log.info("Searching for utility provider to update");

        CityEntity city = this.cityRepository.findByNameIgnoreCase(utilityProviderDTO.getCity());
        CountyEntity county = this.countyRepository.findByNameIgnoreCase(utilityProviderDTO.getCounty());


        UtilityTypeEntity foundType = this.utilityTypeRepository.findByName(utilityTypeName).orElseThrow(() -> new InexistentResourceException("Utility type does not exist", null));


        log.debug("Found UTP in db");
        UtilityProviderEntity updateUTP = findById(id);

        log.info("Updating utility provider");
        updateUTP.setName(utilityProviderDTO.getName());
        updateUTP.setEmail(utilityProviderDTO.getEmail());
        updateUTP.setPassword(utilityProviderDTO.getPassword());
        updateUTP.getUtilityType().add(foundType);
        updateUTP.setCity(city);
        updateUTP.setCounty(county);
        updateUTP.setAddress(utilityProviderDTO.getAddress());

        this.utilityProviderRepository.save(updateUTP);
        log.info("Updated details for utility provider with the id: " + id);
        return updateUTP;
    }

    @Transactional
    public UtilityProviderEntity updateCredentialsPartial(Integer id, UtilityProviderDTO utilityProviderDTO) throws InexistentResourceException, DuplicateResourceException {
        log.info("Searching for utility provider to update");
        Optional<UtilityProviderEntity> foundUTP = this.utilityProviderRepository.findById(id);
        Optional<UtilityTypeEntity> foundUtilityTypeEntityOptional = this.utilityTypeRepository.findByName(utilityProviderDTO.getTypeName());


        if (foundUTP.isEmpty()) {
            throw new InexistentResourceException("No utility provider found with the id: ", id);
        }
        if (foundUtilityTypeEntityOptional.isEmpty()) {
            throw new InexistentResourceException("No utility type found ", null);

        }

        UtilityProviderEntity updateUTP = foundUTP.get();
        UtilityTypeEntity foundType = foundUtilityTypeEntityOptional.get();
        int index = updateUTP.getUtilityType().indexOf(foundType) + 1;


        if (utilityProviderDTO.getSustainabilityScore() != 0.0) {
            updateUTP.setSustainabilityScore(utilityProviderDTO.getSustainabilityScore());
        }
        if (foundType != null && updateUTP.getUtilityType().get(index) != foundType && index != -1) {
            updateUTP.getUtilityType().add(foundType);
        } else if (updateUTP.getUtilityType().get(index) == foundType) {
            throw new DuplicateResourceException("Utility type already exists");
        }

        if (utilityProviderDTO.getPassword() != null) {
            updateUTP.setPassword(utilityProviderDTO.getPassword());
        }

        this.utilityProviderRepository.save(updateUTP);
        log.info("Updated details for utility provider with the id: " + id);

        return updateUTP;
    }

    @Transactional
    public void assignType(Integer idProvider, Integer idProviderType) throws InexistentResourceException {
        Optional<UtilityTypeEntity> foundUtilityType = this.utilityTypeRepository.findById(idProviderType);
        if (foundUtilityType.isEmpty()) {
            throw new InexistentResourceException("Provider type does not exist with id: ", idProviderType);
        }

        Optional<UtilityProviderEntity> foundProvider = this.utilityProviderRepository.findById(idProvider);
        if (foundProvider.isEmpty()) {
            throw new InexistentResourceException("Provider provider does not exist with id: ", idProviderType);
        }


        UtilityProviderEntity utilityProvider = foundProvider.get();
        UtilityTypeEntity utilityType = foundUtilityType.get();

        utilityProvider.getUtilityType().add(utilityType);
    }

    public void delete(Integer id) throws InexistentResourceException {
        this.utilityProviderRepository.deleteById(findById(id).getId());

    }
}
