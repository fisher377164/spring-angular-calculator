package com.techmix.fisher.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author fisher
 * @since 4/8/16
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CalcDTO implements Serializable{

    private static final long serialVersionUID = 1L;

    @NotNull
    private Double leftOperand;

    @NotNull
    private Double rightOperand;

    @NotNull
    private String operation;

    public CalcDTO() {
    }

    public CalcDTO(Double leftOperand, Double rightOperand, String operation) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.operation = operation;
    }
}
