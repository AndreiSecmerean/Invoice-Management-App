package ro.itschool.InvoiceManagementApp.dtos;

import lombok.*;
import org.apache.catalina.User;
import ro.itschool.InvoiceManagementApp.entities.UserEntity;
import ro.itschool.InvoiceManagementApp.validators.NoDigits;

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
public class UserDTO {
        // Start of the user details section \\
    private Integer id;

    @NotEmpty
    @NoDigits(message = "Name cannot contain digits")
    private String name;

    @Email
    @NotNull
    private String email;

    private String password;
    // End of the user details section \\


    // Start of the address section \\
    private String cityName;

    private String countyName;

    private String address;
    // End of the address section \\


    public static UserDTO from(UserEntity userEntity){      //only one studentDTO from studentEntity\\
        return UserDTO.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .cityName(userEntity.getCity().getName())
                .countyName(userEntity.getCounty().getName())
                .address(userEntity.getAddress())
                .build();
    }

    public static List<UserDTO> from(List<UserEntity> userEntities){
        List<UserDTO> resultingUserDTO = new ArrayList<>();

        for(UserEntity resultingUsers : userEntities){
            resultingUserDTO.add(UserDTO.from(resultingUsers));
        }
        return resultingUserDTO;
    }
}
