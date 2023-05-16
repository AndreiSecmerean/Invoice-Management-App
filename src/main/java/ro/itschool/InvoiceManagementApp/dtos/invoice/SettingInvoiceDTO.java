package ro.itschool.InvoiceManagementApp.dtos.invoice;

import lombok.*;
import ro.itschool.InvoiceManagementApp.entities.InvoiceEntity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SettingInvoiceDTO {

    @Email
    private String clientEmail;
    @Email
    private String utilityProviderEmail;

    private Integer sumToPay;

    private LocalDateTime dateSent;

}
