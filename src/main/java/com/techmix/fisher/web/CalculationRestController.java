package com.techmix.fisher.web;

import com.techmix.fisher.dto.CalcDTO;
import com.techmix.fisher.dto.TransactionLogDTO;
import com.techmix.fisher.exeptions.DivisionZeroException;
import com.techmix.fisher.exeptions.NoSuchOperationException;
import com.techmix.fisher.services.CalcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * @author fisher
 * @since 4/8/16
 */

@Controller
@RequestMapping("/api")
public class CalculationRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CalculationRestController.class);

    @Autowired
    private CalcService calcService;

    @RequestMapping(value = "/calculate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionLogDTO> createNewDemoAccount(@Valid @RequestBody CalcDTO calcDTO)
            throws NoSuchOperationException, DivisionZeroException {
        LOGGER.info("calculate: {}", calcDTO);

        return new ResponseEntity<>(calcService.calculate(calcDTO), HttpStatus.OK);
    }
}
