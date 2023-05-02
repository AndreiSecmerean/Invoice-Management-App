package ro.itschool.InvoiceManagementApp.entities;


import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "client", schema = "invoice_db_v2")
@PrimaryKeyJoinColumn(name = "id")
public class ClientEntity extends UserEntity{


    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ToString.Exclude
    private UserEntity userEntity;

    @Enumerated(EnumType.STRING)
    private HousingTypeEnum housingType;
}
