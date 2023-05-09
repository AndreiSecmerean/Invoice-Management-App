package ro.itschool.InvoiceManagementApp.entities;


import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "client", schema = "invoice_db_v2")
@PrimaryKeyJoinColumn(name = "index")
public class ClientEntity extends UserEntity{



//    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @ToString.Exclude
//    private UserEntity userEntity;

    @Enumerated(EnumType.STRING)
    @Column(name = "housing_type")
    private HousingTypeEnum housingType;
}
