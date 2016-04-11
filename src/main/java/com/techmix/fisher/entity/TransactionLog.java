package com.techmix.fisher.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author fisher
 * @since 4/8/16
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "transaction_log")
public class TransactionLog {

    public enum Operations {
        ADDITION(1, "+"),
        SUBTRACTION(2, "-"),
        MULTIPLICATION(3, "*"),
        DIVISION(4, "/");

        private int id;
        private String operation;

        Operations(int id, String operation) {
            this.id = id;
            this.operation = operation;
        }

        public int getId() {
            return id;
        }

        public String getOperation() {
            return operation;
        }

        public static Operations parse(String operation) {
            Operations right = null;
            for (Operations item : Operations.values()) {
                if (item.getOperation().equals(operation)) {
                    right = item;
                    break;
                }
            }
            return right;
        }

        public static Operations parse(int id) {
            Operations right = null;
            for (Operations item : Operations.values()) {
                if (item.getId() == id) {
                    right = item;
                    break;
                }
            }
            return right;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long logId;

    @Column(name = "left_operand")
    private Double leftOperand;

    @Column(name = "right_operand")
    private Double rightOperand;

    @Column(name = "result")
    private Double result;

    @Column(name = "operation_id")
    private int operationId;

    public Operations getRight() {
        return Operations.parse(this.operationId);
    }

    public TransactionLog(Double leftOperand, Double rightOperand, Double result, int operationId) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.result = result;
        this.operationId = operationId;
    }

    public TransactionLog() {
    }
}
