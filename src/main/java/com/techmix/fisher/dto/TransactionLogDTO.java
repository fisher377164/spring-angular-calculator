package com.techmix.fisher.dto;

import com.techmix.fisher.entity.TransactionLog;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author fisher
 * @since 4/8/16
 */

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TransactionLogDTO {

    private Long logId;

    private Double leftOperand;

    private Double rightOperand;

    private Double result;

    private String operation;

    public TransactionLogDTO(TransactionLog log) {
        this.logId = log.getLogId();
        this.leftOperand = log.getLeftOperand();
        this.rightOperand = log.getRightOperand();
        this.result = log.getResult();
        this.operation = TransactionLog.Operations.parse(log.getOperationId()).getOperation();
    }
}
