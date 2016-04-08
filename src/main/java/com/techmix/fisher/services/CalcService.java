package com.techmix.fisher.services;

import com.techmix.fisher.dto.CalcDTO;
import com.techmix.fisher.dto.TransactionLogDTO;
import com.techmix.fisher.exeptions.DivisionZeroException;
import com.techmix.fisher.exeptions.NoSuchOperationException;

import java.util.List;

/**
 * @author fisher
 * @since 4/8/16
 */
public interface CalcService {

    TransactionLogDTO calculate(CalcDTO calcDTO)
            throws NoSuchOperationException, DivisionZeroException;

    TransactionLogDTO getLog(int operationId);

    List<TransactionLogDTO> getAllLogs();
}
