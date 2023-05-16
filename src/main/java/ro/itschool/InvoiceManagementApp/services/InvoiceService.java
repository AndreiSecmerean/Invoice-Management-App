package ro.itschool.InvoiceManagementApp.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.itschool.InvoiceManagementApp.dtos.invoice.SettingInvoiceDTO;
import ro.itschool.InvoiceManagementApp.entities.ClientEntity;
import ro.itschool.InvoiceManagementApp.entities.InvoiceEntity;
import ro.itschool.InvoiceManagementApp.entities.UtilityProviderEntity;
import ro.itschool.InvoiceManagementApp.exceptions.InexistentResourceException;
import ro.itschool.InvoiceManagementApp.repositories.InvoiceRepository;
import ro.itschool.InvoiceManagementApp.services.users.ClientService;
import ro.itschool.InvoiceManagementApp.services.users.UtilityProviderService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private UtilityProviderService utilityProviderService;

    public Iterable<InvoiceEntity> findAll() {
        return this.invoiceRepository.findAll();
    }

    public InvoiceEntity findById(Integer id) throws InexistentResourceException {
        return this.invoiceRepository.findById(id).orElseThrow(() -> new InexistentResourceException("Invoice does not exist with id: ", id));
    }

    public List<InvoiceEntity> searchByClient(String searchTerm) {
        return this.invoiceRepository.searchInvoicesByClient(searchTerm);
    }

    public List<InvoiceEntity> searchByUtilityProvider(String searchTerm) {
        return this.invoiceRepository.searchInvoicesByUtilityProvider(searchTerm);
    }

    @Transactional
    public InvoiceEntity add(SettingInvoiceDTO settingInvoiceDTO) throws InexistentResourceException {
        log.info("Adding new invoice");
        log.debug("search city");
        Optional<List<ClientEntity>> searchClient = this.clientService.searchClients(settingInvoiceDTO.getClientEmail());
        log.debug("search county");
        Optional<List<UtilityProviderEntity>> searchUtp = this.utilityProviderService.search(settingInvoiceDTO.getUtilityProviderEmail());

        if (searchClient.isEmpty()) {
            throw new InexistentResourceException("Client not found in database", null);
        }
        if (searchUtp.isEmpty()) {
            throw new InexistentResourceException("Utility provider not found in database", null);
        }

        List<ClientEntity> foundClient = searchClient.get();
        List<UtilityProviderEntity> foundUtp = searchUtp.get();
        log.debug("Found client and utility provider");

        ClientEntity client = foundClient.get(0);
        UtilityProviderEntity utilityProvider = foundUtp.get(0);

        log.debug("Saving new invoice");
        InvoiceEntity newInvoice = new InvoiceEntity();

        newInvoice.setClient(client);
        newInvoice.setUtilityProvider(utilityProvider);
        newInvoice.setSumToPay(settingInvoiceDTO.getSumToPay());
        newInvoice.setDateSent(LocalDateTime.now());

        InvoiceEntity savedInvoice = this.invoiceRepository.save(newInvoice);
        log.info("Saved new invoice");
        return savedInvoice;
    }

    public InvoiceEntity updateData(Integer id, SettingInvoiceDTO settingInvoiceDTO) throws InexistentResourceException {
        log.info("Updating invoice with id: " + id);
        InvoiceEntity foundInvoice = findById(id);

        if(settingInvoiceDTO.getSumToPay() != null){
            foundInvoice.setSumToPay(settingInvoiceDTO.getSumToPay());
        }
        if(settingInvoiceDTO.getDateSent() != null){
            foundInvoice.setDateSent(settingInvoiceDTO.getDateSent());
        }

        this.invoiceRepository.save(foundInvoice);
        return foundInvoice;
    }

    public void delete(Integer id) throws InexistentResourceException {


         this.invoiceRepository.deleteById(findById(id).getId());
    }
}
