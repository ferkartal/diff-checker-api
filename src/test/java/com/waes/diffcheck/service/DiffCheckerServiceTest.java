package com.waes.diffcheck.service;

import com.waes.diffcheck.domain.response.DiffCheckerResponse;
import com.waes.diffcheck.domain.response.DiffType;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DiffCheckerServiceTest {

    private DiffCheckerService diffCheckerService;

    @Before
    public void setUp() {
        this.diffCheckerService = new DiffCheckerService();
    }

    @Test
    public void it_should_return_different_size() {
        //given
        String leftJson = "{\"data\":\"value\"}";
        String rightJson = "{\"data\":\"value1\"}";

        //when
        DiffCheckerResponse diffCheckerResponse = diffCheckerService.checkDiff(leftJson, rightJson);

        //then
        assertThat(diffCheckerResponse.getDiffType()).isEqualTo(DiffType.DifferentSize);
        assertThat(diffCheckerResponse.getDiffInfoDtos()).isNull();
    }

    @Test
    public void it_should_return_equal() {
        //given
        String leftJson = "{\"data\":\"value\"}";
        String rightJson = "{\"data\":\"value\"}";

        //when
        DiffCheckerResponse diffCheckerResponse = diffCheckerService.checkDiff(leftJson, rightJson);

        //then
        assertThat(diffCheckerResponse.getDiffType()).isEqualTo(DiffType.Equal);
        assertThat(diffCheckerResponse.getDiffInfoDtos()).isNull();
    }

    @Test
    public void it_should_return_not_equal_and_diff_information() {
        //given
        String leftJson = "{\"data\":\"monday\"}";
        String rightJson = "{\"date\":\"sunday\"}";

        //when
        DiffCheckerResponse diffCheckerResponse = diffCheckerService.checkDiff(leftJson, rightJson);

        //then
        assertThat(diffCheckerResponse.getDiffType()).isEqualTo(DiffType.NotEqual);
        assertThat(diffCheckerResponse.getDiffInfoDtos()).hasSize(2);
        assertThat(diffCheckerResponse.getDiffInfoDtos().get(0).getOffset()).isEqualTo(5);
        assertThat(diffCheckerResponse.getDiffInfoDtos().get(0).getLength()).isEqualTo(1);
        assertThat(diffCheckerResponse.getDiffInfoDtos().get(1).getOffset()).isEqualTo(9);
        assertThat(diffCheckerResponse.getDiffInfoDtos().get(1).getLength()).isEqualTo(2);
    }
}