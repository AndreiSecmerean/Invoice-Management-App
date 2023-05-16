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
public class InvoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", referencedColumnName = "user_id")
    @ToString.Exclude
    private ClientEntity client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "utility_provider_id", referencedColumnName = "user_id")
    @ToString.Exclude
    private UtilityProviderEntity utilityProvider;

    @Column(name = "sum_to_pay", nullable = false)
    private int sumToPay;

    @Column(name = "date_sent",nullable = false)
    private LocalDateTime dateSent;
}
