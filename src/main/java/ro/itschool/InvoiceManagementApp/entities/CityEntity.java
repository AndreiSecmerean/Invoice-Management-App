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
public class CityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

}
