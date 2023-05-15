package ro.itschool.InvoiceManagementApp.dtos.utilityProvider;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UtilityProviderListDTO {
    private List<UtilityProviderDTO> utilityProviderDTOS;
}
