package ro.itschool.InvoiceManagementApp.dtos.invoice;

import lombok.*;
import ro.itschool.InvoiceManagementApp.dtos.client.ClientDTO;
import ro.itschool.InvoiceManagementApp.dtos.utilityProvider.UtilityProviderDTO;
import ro.itschool.InvoiceManagementApp.entities.ClientEntity;
import ro.itschool.InvoiceManagementApp.entities.InvoiceEntity;
import ro.itschool.InvoiceManagementApp.entities.UtilityProviderEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO {

    private Integer id;
    private ClientDTO client;

    private UtilityProviderDTO utilityProvider;
    //TODO: brute force all the details of ClientDto and UTPDTO to in here to work

    private int sumToPay;

    private LocalDateTime dateSent;

    public static InvoiceDTO from(InvoiceEntity invoiceEntity) {
        return InvoiceDTO.builder()
                .id(invoiceEntity.getId())

                .client(ClientDTO.from(invoiceEntity.getClient()))
                .utilityProvider(UtilityProviderDTO.from(invoiceEntity.getUtilityProvider()))

                .sumToPay(invoiceEntity.getSumToPay())
                .dateSent(invoiceEntity.getDateSent())
                .build();
    }

    public static List<InvoiceDTO> from(List<InvoiceEntity> invoiceEntities){
        List<InvoiceDTO> resultingInvoices = new ArrayList<>();

        for(InvoiceEntity invoices : invoiceEntities){
            resultingInvoices.add(InvoiceDTO.from(invoices));
        }
        return resultingInvoices;
    }
}
