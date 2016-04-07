package com.techmix.fisher.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author fisher
 * @since 4/8/16
 */
@Data
@Entity
public class TransactionLog {

    public enum Operations {
        ADDITION,
        SUBTRACTION,
        MULTIPLICATION,
        DIVISION

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long leftOperand;

    private Long rightOperand;

    private Double result;

    @Enumerated
    private Operations operations;

}
