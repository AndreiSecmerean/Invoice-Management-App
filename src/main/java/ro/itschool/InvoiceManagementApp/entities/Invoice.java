package ro.itschool.InvoiceManagementApp.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "invoice" , schema = "invoice_db_v2")
@ToString
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = " client_id", referencedColumnName = "id")
    @ToString.Exclude
    private Client clientId;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "utility_provider_id", referencedColumnName = "id")
    @ToString.Exclude
    private List<UtilityProvider> utilityProviderId;

    @Column(name = "sum_to_pay", nullable = false)
    private int sumToPay;

    @Column(name = "date_sent",nullable = false)
    private LocalDateTime dateSent;
}
