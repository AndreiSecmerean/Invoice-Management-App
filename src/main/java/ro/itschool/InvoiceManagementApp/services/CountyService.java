package ro.itschool.InvoiceManagementApp.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.itschool.InvoiceManagementApp.entities.CountyEntity;
import ro.itschool.InvoiceManagementApp.exceptions.InexistentResourceException;
import ro.itschool.InvoiceManagementApp.repositories.CountyRepository;

import java.util.Optional;

@Service
@Slf4j
public class CountyService{

    @Autowired
    private CountyRepository countyRepository;

    public CountyEntity findById(int id) throws InexistentResourceException {
        return this.countyRepository.findById(id).orElseThrow(()->new InexistentResourceException("County does not exist",id));
    }

    public Iterable<CountyEntity> findAll(){
        return this.countyRepository.findAll();
    }

    @Transactional
    public void add(String countyName){
        log.info("Saving new county....");
        CountyEntity county = new CountyEntity();

        county.setName(countyName);

        countyRepository.save(county);
        log.info("Saved new county!");
    }

    public void deleteById(int id) throws InexistentResourceException {
        log.debug("Deleting county");
        Optional<CountyEntity> foundCounty = this.countyRepository.findById(id);

        if(!foundCounty.isPresent()){
            throw new InexistentResourceException("County does not exist",id);
        }else {
            countyRepository.deleteById(id);
            log.info("Deleted county");
        }
        log.debug("Finished deleting county");
    }
}
