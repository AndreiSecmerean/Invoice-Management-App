package ro.itschool.InvoiceManagementApp.dtos.invoice;

import lombok.*;
import ro.itschool.InvoiceManagementApp.entities.InvoiceEntity;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SettingInvoiceDTO {

    private String clientEmail;
    private String utilityProviderEmail;

    private Integer sumToPay;

    private LocalDateTime dateSent;

}
