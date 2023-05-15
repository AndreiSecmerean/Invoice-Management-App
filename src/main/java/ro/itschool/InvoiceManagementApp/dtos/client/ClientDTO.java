package ro.itschool.InvoiceManagementApp.dtos.client;

import lombok.*;
import ro.itschool.InvoiceManagementApp.entities.CityEntity;
import ro.itschool.InvoiceManagementApp.entities.ClientEntity;
import ro.itschool.InvoiceManagementApp.entities.CountyEntity;
import ro.itschool.InvoiceManagementApp.entities.HousingTypeEnum;
import ro.itschool.InvoiceManagementApp.validators.NoDigits;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {


    private Integer id;

    @Enumerated(EnumType.STRING)
    private HousingTypeEnum housingType;

    @NotEmpty
    @NoDigits(message = "Name type cannot contain digits")
    private String name;

    @Email
    @NotNull
    private String email;

    @NotNull
    private String password;

    private String city;
    private String county;
    private String address;

    public static ClientDTO from(ClientEntity clientEntity){
        return ClientDTO.builder()
                .id(clientEntity.getId())
                .name(clientEntity.getName())
                .housingType(clientEntity.getHousingType())
                .email(clientEntity.getEmail())
                .password(clientEntity.getPassword())
                .city(clientEntity.getCity().getName())
                .county(clientEntity.getCounty().getName())
                .address(clientEntity.getAddress())
                .build();
    }


    public static List<ClientDTO> from(List<ClientEntity> clientEntity) {
        List<ClientDTO> resultingUsersDto = new ArrayList<>();

        for (ClientEntity resultingClients : clientEntity) {
            resultingUsersDto.add(ClientDTO.from(resultingClients));
        }

        return resultingUsersDto;
    }
}
