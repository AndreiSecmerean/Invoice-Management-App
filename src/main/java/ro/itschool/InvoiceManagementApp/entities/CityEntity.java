package ro.itschool.InvoiceManagementApp.entities;

import lombok.*;

import javax.persistence.*;
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "city" , schema = "invoice_db_v2")
@ToString
public class CityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

//    @ToString.Exclude                       /\/\ new cities and counties can be added only by the admin separately and not when creating a new user/\/\
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "id")
//    @MapsId
//    private User user;


}
