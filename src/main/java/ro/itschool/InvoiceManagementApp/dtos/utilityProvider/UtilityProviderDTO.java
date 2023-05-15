package ro.itschool.InvoiceManagementApp.dtos.utilityProvider;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.ReadOnlyProperty;
import ro.itschool.InvoiceManagementApp.entities.UtilityProviderEntity;
import ro.itschool.InvoiceManagementApp.entities.UtilityTypeEntity;
import ro.itschool.InvoiceManagementApp.entities.UtilityTypeNameEnum;
import ro.itschool.InvoiceManagementApp.validators.NoDigits;

import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UtilityProviderDTO {
    private Integer id;

    private UtilityTypeNameEnum typeName;

    private List<UtilityTypeEntity> utilityType;

    private double sustainabilityScore;

    @NoDigits(message = "Name type cannot contain digits")
    private String name;

    @Email
    private String email;

    private String password;

    private String city;
    private String county;
    private String address;

    public static UtilityProviderDTO from(UtilityProviderEntity utilityProvider) {
        return UtilityProviderDTO.builder()
                .id(utilityProvider.getId())
                .utilityType(utilityProvider.getUtilityType())
                .sustainabilityScore(utilityProvider.getSustainabilityScore())
                .name(utilityProvider.getName())
                .email(utilityProvider.getEmail())
                .password(utilityProvider.getPassword())
                .city(utilityProvider.getCity().getName())
                .county(utilityProvider.getCounty().getName())
                .address(utilityProvider.getAddress())
                .build();
    }

    public static List<UtilityProviderDTO> from(List<UtilityProviderEntity> utilityProviderList) {
        List<UtilityProviderDTO> resultingUTP = new ArrayList<>();

        for (UtilityProviderEntity foundUTP : utilityProviderList) {
            resultingUTP.add(UtilityProviderDTO.from(foundUTP));
        }
        return resultingUTP;
    }
}
