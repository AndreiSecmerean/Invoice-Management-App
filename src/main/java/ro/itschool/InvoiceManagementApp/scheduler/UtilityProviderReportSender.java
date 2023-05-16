package ro.itschool.InvoiceManagementApp.scheduler;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ro.itschool.InvoiceManagementApp.entities.UtilityProviderEntity;
import ro.itschool.InvoiceManagementApp.repositories.UtilityProviderRepository;
import ro.itschool.InvoiceManagementApp.services.EmailService;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Slf4j
@Component
public class UtilityProviderReportSender {
    @Autowired
    private EmailService emailService;

    @Autowired
    private UtilityProviderRepository  utilityProviderRepository;

    @Value("${invoice.app.emailReceiver}")
    private String emailReceiver;

    @Scheduled(cron = "0 12 30 * * FRI")
    public void sendLog(){
        Iterable<UtilityProviderEntity> providerEntities = this.utilityProviderRepository.findAll();
        List<String> foundProviders =new ArrayList<>();
        for(UtilityProviderEntity utilityProvider : providerEntities) {
            foundProviders.add(utilityProvider.getName() + Double.toString(utilityProvider.getSustainabilityScore()));

            try (StringWriter stringWriter = new StringWriter()) {

                CSVWriter csvWriter = new CSVWriter(stringWriter);

                StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(csvWriter).build();

                beanToCsv.write(foundProviders);

                String csvReport = stringWriter.toString();

                csvWriter.close();

                log.info("Closed csv");
                this.emailService.sendEmail(emailReceiver, "Utility provider report", "This is an automated report regarding the sustainability score of all of the utility providers", csvReport);
                log.debug("Email sent sucessfully!");

            } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
                log.error("Error creating report to {} due to {}",emailReceiver , e.getMessage());
            } catch (MessagingException e) {
                log.error("Error sending email to {} due to {}",emailReceiver, e.getMessage());
            }
        }
    }
}
