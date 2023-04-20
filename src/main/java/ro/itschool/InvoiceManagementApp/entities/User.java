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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "email",nullable = false, length = 50)
    private String email;

    @Column(name="password", nullable = false, length = 50)
    private String password;


    @Column(name = "city")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "city", referencedColumnName = "id")
    @ToString.Exclude
    private City city;

    @Column(name = "county")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @ToString.Exclude
    private String county;

    @Column(name = "address")
    private String address;
}
