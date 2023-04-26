package ro.itschool.InvoiceManagementApp.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.itschool.InvoiceManagementApp.entities.CityEntity;
import ro.itschool.InvoiceManagementApp.exceptions.InexistentResourceException;
import ro.itschool.InvoiceManagementApp.repositories.CityRepository;

import java.util.Optional;

@Service
@Slf4j
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    public CityEntity findById(int id) throws InexistentResourceException {
        return this.cityRepository.findById(id).orElseThrow(()->new InexistentResourceException("City does not exist",id));
    }

    public void add(String name){
        log.info("Saving new city...");

        CityEntity savedCity = new CityEntity();

        savedCity.setName(name);

        cityRepository.save(savedCity);
        log.info("Saved new city successfully!");
    }

    public void deleteById(int id) throws InexistentResourceException {
        Optional<CityEntity> foundCity = this.cityRepository.findById(id);
        if(!foundCity.isPresent()){
            throw new InexistentResourceException("City does not exist", id);
        }else {
            cityRepository.deleteById(id);
        }
    }
}
