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
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private int id;

    @Column(name = "name", nullable = false)
    private String cityName;

//    @ToString.Exclude
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "id")
//    @MapsId
//    private User user;
}
