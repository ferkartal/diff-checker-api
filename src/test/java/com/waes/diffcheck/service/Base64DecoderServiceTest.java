package com.waes.diffcheck.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waes.diffcheck.exception.InvalidJsonFormatException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class Base64DecoderServiceTest {
    @InjectMocks
    private Base64DecoderService base64DecoderService;

    @Mock
    private ObjectMapper objectMapper;

    @Test
    public void it_should_decode_json() {
        //given
        String encodedJson = "eyJkYXRhIjoidmFsdWUifQ==";
        String expectedDecodedJson = "{\"data\":\"value\"}";

        //when
        String decodedJson = base64DecoderService.decodeJson(encodedJson);

        //then
        assertThat(decodedJson).isEqualTo(expectedDecodedJson);
    }

    @Test
    public void it_should_throw_exception_when_decoded_string_is_not_json_format() throws JsonProcessingException {
        //given
        String encodedJson = "eyJkYXRhIjp9";
        String decodedJson = "{\"data\":}";
        given(objectMapper.readTree(decodedJson)).willThrow(JsonProcessingException.class);

        //when
        Throwable throwable = catchThrowable(() -> base64DecoderService.decodeJson(encodedJson));

        //then
        assertThat(throwable).isInstanceOf(InvalidJsonFormatException.class);
    }
}