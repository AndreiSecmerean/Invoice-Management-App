package ro.itschool.InvoiceManagementApp.entities;


import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "client", schema = "invoice_db_v2")
@PrimaryKeyJoinColumn(name = "user_id")
public class ClientEntity extends UserEntity{

    @Enumerated(EnumType.STRING)
    @Column(name = "housing_type")
    private HousingTypeEnum housingType;
}
