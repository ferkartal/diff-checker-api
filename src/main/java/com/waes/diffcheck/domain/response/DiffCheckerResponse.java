package com.waes.diffcheck.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * Response model for diff checker
 */
@Builder
@Getter
public class DiffCheckerResponse {

    private DiffType diffType;
    @JsonProperty("diffs")
    private List<DiffInfoDto> diffInfoDtos;
}