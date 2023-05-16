package ro.itschool.InvoiceManagementApp.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user" , schema = "invoice_db_v2")
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
public class UserEntity implements Serializable {
    // Start of the user details section \\
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name="password", nullable = false, length = 50)
    private String password;
    // End of the user details section \\


    // Start of the address section \\
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city", referencedColumnName = "id")
    @ToString.Exclude
    private CityEntity city;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "county", referencedColumnName = "id")
    @ToString.Exclude
    private CountyEntity county;

    @Column(name = "address")
    private String address;
    // End of the address section \\

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="user_roles",
            joinColumns = @JoinColumn(name="id_user", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="id_role", referencedColumnName = "id")
    )
    private Set<RoleEntity> roles = new HashSet<>();
}


