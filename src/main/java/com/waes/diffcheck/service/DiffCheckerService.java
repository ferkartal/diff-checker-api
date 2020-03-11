package com.waes.diffcheck.service;

import com.waes.diffcheck.domain.response.DiffCheckerResponse;
import com.waes.diffcheck.domain.response.DiffInfoDto;
import com.waes.diffcheck.domain.response.DiffType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * This service allows us to compare two Json data and find out the differences
 */
@Service
public class DiffCheckerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiffCheckerService.class);

    /**
     * It compares given two Json data and finds the differences
     *
     * @param left  Json data
     * @param right Json data
     * @return Differences information given two Json data
     */
    public DiffCheckerResponse checkDiff(String left, String right) {
        if (haveDifferentSize(left, right)) {
            LOGGER.info("Given left value: {} and right value: {} have different size", left, right);
            return DiffCheckerResponse.builder()
                    .diffType(DiffType.DifferentSize)
                    .build();
        }

        if (isEqual(left, right)) {
            LOGGER.info("Given left value: {} and right value: {} are equal", left, right);
            return DiffCheckerResponse.builder()
                    .diffType(DiffType.Equal)
                    .build();
        }

        LOGGER.info("Given left value: {} and right value: {} are not equal", left, right);
        return DiffCheckerResponse.builder()
                .diffType(DiffType.NotEqual)
                .diffInfoDtos(getDiffs(left, right))
                .build();
    }

    /**
     * Finds offset and length information based on comparison
     *
     * @param left  Json data
     * @param right Json data
     * @return offset and length information of compared data
     */
    private List<DiffInfoDto> getDiffs(String left, String right) {
        var isDiffExist = false;
        List<DiffInfoDto> diffInfoDtoList = new ArrayList<>();
        var offset = 0;
        for (var index = 0; index < left.length(); ++index) {
            boolean noMatches = left.codePointAt(index) != right.codePointAt(index);
            if (!isDiffExist && noMatches) {
                isDiffExist = true;
                offset = index;
            } else if (isDiffExist && !noMatches) {
                isDiffExist = false;
                diffInfoDtoList.add(DiffInfoDto.builder().offset(offset).length(index - offset).build());
            }
        }
        return diffInfoDtoList;
    }

    /**
     * Checks length of two Json data
     *
     * @param left  Json data
     * @param right Json data
     * @return True if data lengths are not equal, otherwise false
     */
    private boolean haveDifferentSize(String left, String right) {
        return left.length() != right.length();
    }

    /**
     * Compares equality of two Json data
     *
     * @param left  Json data
     * @param right Json data
     * @return True if strings are equal, otherwise false
     */
    private boolean isEqual(String left, String right) {
        return left.equals(right);
    }
}