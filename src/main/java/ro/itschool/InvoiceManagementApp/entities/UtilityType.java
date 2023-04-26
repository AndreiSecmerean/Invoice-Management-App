package ro.itschool.InvoiceManagementApp.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "utility_type " , schema = "invoice_db_v2")
public class UtilityType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "utility_name",nullable = false)
    private UtilityTypeName utilityTypeName;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_important",nullable = false)
    private IsUtilityImportant isUtilityImportant;
}
