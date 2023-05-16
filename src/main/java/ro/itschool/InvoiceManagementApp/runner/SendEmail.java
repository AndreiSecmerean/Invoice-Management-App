package ro.itschool.InvoiceManagementApp.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ro.itschool.InvoiceManagementApp.scheduler.UtilityProviderReportSender;

//@Component
public class SendEmail implements CommandLineRunner {

    @Autowired
    private UtilityProviderReportSender utilityProviderReportSender;

    @Override
    public void run(String... args) throws Exception {
        this.utilityProviderReportSender.sendLog();
    }
}
