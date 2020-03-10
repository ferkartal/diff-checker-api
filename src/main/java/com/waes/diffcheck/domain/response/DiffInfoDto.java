package com.waes.diffcheck.domain.response;

import lombok.Builder;
import lombok.Getter;

/**
 * Dto object in order to present diff information such as offset and length
 * offset: diff start index
 * length: the number that presents of the count of differences
 */
@Builder
@Getter
public class DiffInfoDto {
    private int offset;
    private int length;
}