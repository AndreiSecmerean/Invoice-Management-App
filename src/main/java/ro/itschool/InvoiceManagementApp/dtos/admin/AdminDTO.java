package ro.itschool.InvoiceManagementApp.dtos.admin;

import lombok.*;
import ro.itschool.InvoiceManagementApp.entities.AdminEntity;
import ro.itschool.InvoiceManagementApp.entities.AdminTypeEnum;
import ro.itschool.InvoiceManagementApp.validators.NoDigits;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminDTO {

    private Integer id;

    @NotEmpty
    @NoDigits(message = "Name type cannot contain digits")
    private String name;

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    @Enumerated(EnumType.STRING)
    private AdminTypeEnum adminType;

    public static AdminDTO from(AdminEntity adminEntity){
        return AdminDTO.builder()
                .id(adminEntity.getId())
                .name(adminEntity.getName())
                .email(adminEntity.getEmail())
                .password(adminEntity.getPassword())
                .adminType(adminEntity.getAdminType())
                .build();
    }

    public static List<AdminDTO> from(List<AdminEntity> adminEntities){
        List<AdminDTO> resultingAdminDTOs = new ArrayList<>();

        for(AdminEntity admin : adminEntities){
            resultingAdminDTOs.add(AdminDTO.from(admin));
        }
        return resultingAdminDTOs;
    }
}
