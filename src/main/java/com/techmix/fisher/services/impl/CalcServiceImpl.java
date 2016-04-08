package com.techmix.fisher.services.impl;

import com.techmix.fisher.dto.CalcDTO;
import com.techmix.fisher.dto.TransactionLogDTO;
import com.techmix.fisher.entity.TransactionLog;
import com.techmix.fisher.exeptions.DivisionZeroException;
import com.techmix.fisher.exeptions.NoSuchOperationException;
import com.techmix.fisher.repository.TransactionLogRepository;
import com.techmix.fisher.services.CalcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author fisher
 * @since 4/8/16
 */

@Service
@Transactional
public class CalcServiceImpl implements CalcService {

    @Autowired
    private TransactionLogRepository transactionLogRepository;

    @Override
    public TransactionLogDTO calculate(CalcDTO calcDTO) throws NoSuchOperationException, DivisionZeroException {
        TransactionLog.Operations operation = TransactionLog.Operations.parse(calcDTO.getOperation());
        if (operation == null) {
            throw new NoSuchOperationException(calcDTO.getOperation());
        }
        if (operation == TransactionLog.Operations.DIVISION && calcDTO.getRightOperand() == 0) {
            throw new DivisionZeroException("Division by zero is not possible in this system =(");
        }

        Double result = 0.;
        switch (operation) {
            case ADDITION:
                result = calcDTO.getLeftOperand() + calcDTO.getRightOperand();
                break;
            case SUBTRACTION:
                result = calcDTO.getLeftOperand() - calcDTO.getRightOperand();
                break;
            case MULTIPLICATION:
                result = calcDTO.getLeftOperand() * calcDTO.getRightOperand();
                break;
            case DIVISION:
                result = calcDTO.getLeftOperand() / calcDTO.getRightOperand();
                break;
        }

        TransactionLog log = new TransactionLog();
        log.setLeftOperand(calcDTO.getLeftOperand());
        log.setRightOperand(calcDTO.getRightOperand());
        log.setResult(result);
        log.setOperationId(operation.getId());
        TransactionLog logFromDb = transactionLogRepository.save(log);

        return new TransactionLogDTO(logFromDb);
    }

    @Override
    public TransactionLogDTO getLog(int operationId) {
        return null;
    }

    @Override
    public List<TransactionLogDTO> getAllLogs() {
        return null;
    }
}
