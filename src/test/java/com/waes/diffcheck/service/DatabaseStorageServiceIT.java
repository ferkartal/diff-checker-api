package com.waes.diffcheck.service;

import com.waes.diffcheck.domain.entity.LeftEntity;
import com.waes.diffcheck.domain.entity.RightEntity;
import com.waes.diffcheck.exception.DomainNotFoundException;
import com.waes.diffcheck.repository.LeftDataRepository;
import com.waes.diffcheck.repository.RightDataRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DatabaseStorageServiceIT {

    @Autowired
    private DatabaseStorageService databaseStorageService;

    @Autowired
    private LeftDataRepository leftDataRepository;

    @Autowired
    private RightDataRepository rightDataRepository;

    @Test
    @Sql(value = "/delete-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void it_should_save_left() {
        //given
        String id = "1";
        String leftJson = "{\"data\":\"value\"}";

        //when
        databaseStorageService.storeLeft(id, leftJson);

        //then
        Optional<LeftEntity> optionalLeftEntity = leftDataRepository.findById(id);
        assertThat(optionalLeftEntity.isPresent()).isTrue();
    }

    @Test
    @Sql(value = "/delete-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void it_should_save_right() {
        //given
        String id = "1";
        String rightJson = "{\"data\":\"value\"}";

        //when
        databaseStorageService.storeRight(id, rightJson);

        //then
        Optional<RightEntity> optionalRightEntity = rightDataRepository.findById(id);
        assertThat(optionalRightEntity.isPresent()).isTrue();
    }

    @Test
    @SqlGroup({
            @Sql(value = "/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "/delete-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void it_should_retrieve_left() {
        //given
        String id = "1";
        String leftJson = "{\"data\":\"value\"}";

        //when
        String left = databaseStorageService.getLeft(id);

        //then
        assertThat(left).isEqualTo(leftJson);
    }

    @Test
    public void it_should_throw_domain_not_found_exception_when_id_not_exist_in_left() {
        //given
        String id = "1";

        //when
        Throwable throwable = catchThrowable(() -> databaseStorageService.getLeft(id));

        //then
        assertThat(throwable).isInstanceOf(DomainNotFoundException.class);
    }

    @Test
    @SqlGroup({
            @Sql(value = "/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "/delete-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void it_should_retrieve_right() {
        //given
        String id = "1";
        String rightJson = "{\"data\":\"value\"}";

        //when
        String right = databaseStorageService.getRight(id);

        //then
        assertThat(right).isEqualTo(rightJson);
    }

    @Test
    public void it_should_throw_domain_not_found_exception_when_id_not_exist_in_right() {
        //given
        String id = "1";

        //when
        Throwable throwable = catchThrowable(() -> databaseStorageService.getRight(id));

        //then
        assertThat(throwable).isInstanceOf(DomainNotFoundException.class);
    }
}