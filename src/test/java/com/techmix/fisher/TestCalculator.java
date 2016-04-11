package com.techmix.fisher;

import com.techmix.fisher.dto.CalcDTO;
import com.techmix.fisher.entity.TransactionLog;
import com.techmix.fisher.repository.TransactionLogRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author fisher
 * @since 4/11/16
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class TestCalculator {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private TransactionLog transactionLog;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private TransactionLogRepository logRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {
        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
                hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();
        Assert.assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        this.logRepository.deleteAllInBatch();
        this.transactionLog = logRepository.save(new TransactionLog(1., 1., 2., 1));
    }

    @Test
    public void operationDoesNotExist() throws Exception {
        mockMvc.perform(post("/api/calculate")
                .content(this.json(new CalcDTO(1., 2., "add")))
                .contentType(contentType))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getLogs() throws Exception {
        mockMvc.perform(get("/api/getLogs/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].logId", is(this.transactionLog.getLogId().intValue())))
                .andExpect(jsonPath("$[0].leftOperand", is(this.transactionLog.getLeftOperand())))
                .andExpect(jsonPath("$[0].rightOperand", is(this.transactionLog.getRightOperand())))
                .andExpect(jsonPath("$[0].result", is(this.transactionLog.getResult())))
                .andExpect(jsonPath("$[0].operation",
                        is(TransactionLog.Operations.parse(this.transactionLog.getOperationId()).getOperation())));
    }

    @Test
    public void createLog() throws Exception {
        String logJson = json(new CalcDTO(1., 2., "+"));
        this.mockMvc.perform(post("/api/calculate")
                .contentType(contentType)
                .content(logJson))
                .andExpect(status().isCreated());
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

}
