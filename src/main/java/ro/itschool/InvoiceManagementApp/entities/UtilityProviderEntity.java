package ro.itschool.InvoiceManagementApp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "utility_provider", schema = "invoice_db_v2")
@PrimaryKeyJoinColumn(name = "user_id")
public class UtilityProviderEntity extends UserEntity {



    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "utility_provider_types",
            joinColumns = {@JoinColumn(name = "utp_id")}, //owning
            inverseJoinColumns = {@JoinColumn(name = "utt_id")} //non-owning
    )
    private List<UtilityTypeEntity> utilityType = new ArrayList<>();

    @Column(name = "sustainability_score", nullable = false)
    private double sustainabilityScore;
}
