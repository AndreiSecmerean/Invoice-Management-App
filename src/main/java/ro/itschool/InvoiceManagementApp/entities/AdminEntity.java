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
@PrimaryKeyJoinColumn(name = "id")
public class AdminEntity extends UserEntity {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;


    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ToString.Exclude
    private UserEntity userId;
}
