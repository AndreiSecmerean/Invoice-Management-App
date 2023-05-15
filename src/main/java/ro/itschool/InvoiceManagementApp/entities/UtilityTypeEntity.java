package ro.itschool.InvoiceManagementApp.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "utility_type " , schema = "invoice_db_v2")
public class UtilityTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name",nullable = false)
    @Getter
    private UtilityTypeNameEnum name;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_important",nullable = false)
    private IsUtilityImportantBoolean isImportant;

}
