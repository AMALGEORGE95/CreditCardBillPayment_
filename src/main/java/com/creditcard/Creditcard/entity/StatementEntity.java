package com.creditcard.Creditcard.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "statement_generation")
public class StatementEntity {
    @Id
    @Column(name = "statement_id")
    @SequenceGenerator(name = "statementIdGen", allocationSize = 1, initialValue = 1000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "statementIdGen")
    private Long statementId;
    @Column(name = "due_amount",nullable = false)
    private double dueAmount;

    @Column(name = "billing_date",nullable = false)
    private LocalDate billingDate;

    @Column(name = "due_date",nullable = false)
    private LocalDate dueDate;
    /*
     * JPA allows you to define Many-to-one relationships between entity classes
     * using the @ManyToOne annotation
     */
    private Long userId;
}
