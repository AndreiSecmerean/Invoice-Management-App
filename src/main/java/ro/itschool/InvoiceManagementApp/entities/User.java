package ro.itschool.InvoiceManagementApp.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user" , schema = "invoice_db_v2")
@ToString
public class User {
         // Start of the user details section \\
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "email",nullable = false, length = 50)
    private String email;

    @Column(name="password", nullable = false, length = 50)
    private String password;
        // End of the user details section \\


        // Start of the address section \\
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "city", referencedColumnName = "id")
    @ToString.Exclude
    private City city;


    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "county", referencedColumnName = "id")
    @ToString.Exclude
    private County county;

    @Column(name = "address")
    private String address;
        // End of the address section \\


         // Start of the relations section \\
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "id")
    private Admin admin;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "id")
    private Client client;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "id")
    private UtilityProvider utilityProvider;
        // End of the relations section \\
}
