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
    @Column(name="id")
    private Integer id;

    @Column(name = "name")
    private String name;

}
