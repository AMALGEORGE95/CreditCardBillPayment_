package com.creditcard.Creditcard.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bill_generation")
public class BillGenerationEntity {
    @Id
    @Column(name = "bill_id")
    @SequenceGenerator(name = "billIdGen", allocationSize = 1, initialValue = 3000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "billIdGen")
    private Long billId;
    private LocalDateTime DueOn;
    private Long userId;
    private double dueAmount;
    private String status;
}
