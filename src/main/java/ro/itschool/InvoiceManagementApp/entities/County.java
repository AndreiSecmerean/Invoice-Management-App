package ro.itschool.InvoiceManagementApp.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "county" , schema = "invoice_db_v2")
@ToString
public class County {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private int id;

    @Column(name = "name", nullable = false)
    private String countyName;

//    @ToString.Exclude
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "id")
//    @MapsId
//    private User user;
}
