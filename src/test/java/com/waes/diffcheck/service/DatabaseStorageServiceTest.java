package com.waes.diffcheck.service;

import com.waes.diffcheck.domain.entity.LeftEntity;
import com.waes.diffcheck.domain.entity.RightEntity;
import com.waes.diffcheck.exception.DomainNotFoundException;
import com.waes.diffcheck.repository.LeftDataRepository;
import com.waes.diffcheck.repository.RightDataRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseStorageServiceTest {

    @InjectMocks
    private DatabaseStorageService databaseStorageService;

    @Mock
    private LeftDataRepository leftDataRepository;

    @Mock
    private RightDataRepository rightDataRepository;

    @Test
    public void it_should_save_left() {
        //given
        String id = "1";
        String leftJson = "{\"data\":\"value\"}";

        LeftEntity leftEntity = LeftEntity.builder().id(id).value(leftJson).build();
        given(leftDataRepository.save(refEq(leftEntity))).willReturn(leftEntity);

        //when
        databaseStorageService.storeLeft(id, leftJson);

        //then
        ArgumentCaptor<LeftEntity> argumentCaptor = ArgumentCaptor.forClass(LeftEntity.class);
        verify(leftDataRepository).save(argumentCaptor.capture());
        LeftEntity capturedEntity = argumentCaptor.getValue();
        assertThat(capturedEntity.getValue()).isEqualTo(leftJson);
    }

    @Test
    public void it_should_save_right() {
        //given
        String id = "1";
        String rightJson = "{\"data\":\"value\"}";

        RightEntity rightEntity = RightEntity.builder().id(id).value(rightJson).build();
        given(rightDataRepository.save(refEq(rightEntity))).willReturn(rightEntity);

        //when
        databaseStorageService.storeRight(id, rightJson);

        //then
        ArgumentCaptor<RightEntity> argumentCaptor = ArgumentCaptor.forClass(RightEntity.class);
        verify(rightDataRepository).save(argumentCaptor.capture());
        RightEntity capturedEntity = argumentCaptor.getValue();
        assertThat(capturedEntity.getValue()).isEqualTo(rightJson);
    }

    @Test
    public void it_should_retrieve_left() {
        //given
        String id = "1";
        String leftJson = "{\"data\":\"value\"}";

        LeftEntity leftEntity = LeftEntity.builder().id(id).value(leftJson).build();
        given(leftDataRepository.findById(id)).willReturn(Optional.of(leftEntity));

        //when
        String left = databaseStorageService.getLeft(id);

        //then
        assertThat(left).isEqualTo(leftJson);
    }

    @Test
    public void it_should_throw_domain_not_found_exception_when_id_not_exist_in_left() {
        //given
        String id = "1";

        given(leftDataRepository.findById(id)).willReturn(Optional.empty());

        //when
        Throwable throwable = catchThrowable(() -> databaseStorageService.getLeft(id));

        //then
        assertThat(throwable).isInstanceOf(DomainNotFoundException.class);
    }

    @Test
    public void it_should_retrieve_right() {
        //given
        String id = "1";
        String rightJson = "{\"data\":\"value\"}";

        RightEntity rightEntity = RightEntity.builder().id(id).value(rightJson).build();
        given(rightDataRepository.findById(id)).willReturn(Optional.of(rightEntity));

        //when
        String right = databaseStorageService.getRight(id);

        //then
        assertThat(right).isEqualTo(rightJson);
    }

    @Test
    public void it_should_throw_domain_not_found_exception_when_id_not_exist_in_right() {
        //given
        String id = "1";

        given(rightDataRepository.findById(id)).willReturn(Optional.empty());

        //when
        Throwable throwable = catchThrowable(() -> databaseStorageService.getRight(id));

        //then
        assertThat(throwable).isInstanceOf(DomainNotFoundException.class);
    }
}