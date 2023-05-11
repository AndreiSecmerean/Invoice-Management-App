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

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "housing_type")
    private HousingTypeEnum housingType;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id",insertable = false, updatable = false)
//    private UserEntity user;
}
