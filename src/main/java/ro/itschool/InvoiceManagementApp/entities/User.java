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

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ToString.Exclude
    private Admin admin;
}
