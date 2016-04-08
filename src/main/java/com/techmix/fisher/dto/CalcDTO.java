package com.techmix.fisher.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @author fisher
 * @since 4/8/16
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CalcDTO {

    @NotNull
    private Double leftOperand;

    @NotNull
    private Double rightOperand;

    @NotNull
    private String operation;

}
