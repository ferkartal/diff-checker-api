package com.waes.diffcheck.controller;

import com.waes.diffcheck.domain.response.DiffType;
import com.waes.diffcheck.exception.DomainNotFoundException;
import com.waes.diffcheck.service.StorageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class DiffCheckerControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    @Qualifier("cacheStorageService")
    private StorageService storageService;

    @Test
    public void it_should_save_left_value() throws Exception {
        //given
        String requestBody = "eyJkYXRhIjoidmFsdWUifQ==";

        //when
        ResultActions resultActions = mockMvc.perform(post("/v1/diff/1/left")
                .contentType(MediaType.TEXT_PLAIN)
                .content(requestBody));

        //then
        resultActions.andExpect(status().isCreated());
    }

    @Test
    public void it_should_throw_illegal_argument_exception_when_left_value_is_not_base64_format() throws Exception {
        //given
        String requestBody = "eyJkYXRhIjoidmFsdWfQ==";

        //when
        ResultActions resultActions = mockMvc.perform(post("/v1/diff/1/left")
                .contentType(MediaType.TEXT_PLAIN)
                .content(requestBody));

        //then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Input byte array has wrong 4-byte ending unit"));
    }

    @Test
    public void it_should_save_right_value() throws Exception {
        //given
        String requestBody = "eyJkYXRhIjoidmFsdWUifQ==";

        //when
        ResultActions resultActions = mockMvc.perform(post("/v1/diff/1/right")
                .contentType(MediaType.TEXT_PLAIN)
                .content(requestBody));

        //then
        resultActions.andExpect(status().isCreated());
    }

    @Test
    public void it_should_throw_invalid_json_format_exception_when_right_value_is_not_json_format() throws Exception {
        //given
        String requestBody = "eyJkYXRhIjp9";

        //when
        ResultActions resultActions = mockMvc.perform(post("/v1/diff/1/right")
                .contentType(MediaType.TEXT_PLAIN)
                .content(requestBody));

        //then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid input value: " + requestBody));

    }

    @Test
    public void it_should_return_equal_if_left_and_right_value_is_equal() throws Exception {
        //given
        String id = "1";
        String left = "{\"data\":\"value\"}";
        String right = "{\"data\":\"value\"}";
        given(storageService.getLeft(id)).willReturn(left);
        given(storageService.getRight(id)).willReturn(right);

        //when
        ResultActions resultActions = mockMvc.perform(get("/v1/diff/1")
                .contentType(MediaType.TEXT_PLAIN));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.diffType").value(DiffType.Equal.name()));
    }

    @Test
    public void it_should_return_different_size_if_left_and_right_value_is_equal() throws Exception {
        //given
        String id = "1";
        String left = "{\"data\":\"value\"}";
        String right = "{\"data\":\"value1\"}";
        given(storageService.getLeft(id)).willReturn(left);
        given(storageService.getRight(id)).willReturn(right);

        //when
        ResultActions resultActions = mockMvc.perform(get("/v1/diff/1")
                .contentType(MediaType.TEXT_PLAIN));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.diffType").value(DiffType.DifferentSize.name()));
    }

    @Test
    public void it_should_return_not_equal_if_left_and_right_value_is_equal() throws Exception {
        //given
        String id = "1";
        String left = "{\"data\":\"value\"}";
        String right = "{\"date\":\"value\"}";
        given(storageService.getLeft(id)).willReturn(left);
        given(storageService.getRight(id)).willReturn(right);

        //when
        ResultActions resultActions = mockMvc.perform(get("/v1/diff/1")
                .contentType(MediaType.TEXT_PLAIN));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.diffType").value(DiffType.NotEqual.name()))
                .andExpect(jsonPath("$.diffs[0].offset").value(5))
                .andExpect(jsonPath("$.diffs[0].length").value(1));
    }

    @Test
    public void it_should_throw_domain_not_found_exception_when_left_data_does_not_exist() throws Exception {
        //given
        String id = "1";
        DomainNotFoundException domainNotFoundException = new DomainNotFoundException("Left value is not present with id : " + id);
        given(storageService.getLeft(id)).willThrow(domainNotFoundException);

        //when
        ResultActions resultActions = mockMvc.perform(get("/v1/diff/1")
                .contentType(MediaType.TEXT_PLAIN));

        //then
        resultActions.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Left value is not present with id : " + id));

    }
}