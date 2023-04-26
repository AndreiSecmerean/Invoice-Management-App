package ro.itschool.InvoiceManagementApp.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "utility_provider" , schema = "invoice_db_v2")
public class UtilityProviderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserEntity userId;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "utility_type_id", referencedColumnName ="id")
    private List<UtilityTypeEntity> utilityTypeId;

    @Column(name = "price_per_unit", nullable = false)
    private double pricePerUnit;
}
