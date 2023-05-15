package ro.itschool.InvoiceManagementApp.dtos.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class AdminListDTO {
    private List<AdminDTO>adminDTOs;
}
