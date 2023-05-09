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
@Table(name = "admin", schema = "invoice_db_v2")
@PrimaryKeyJoinColumn(name = "user_id")
public class AdminEntity extends UserEntity {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;


    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private AdminTypeEnum adminTypeEnum;
}
