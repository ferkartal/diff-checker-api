package com.waes.diffcheck.service;

import com.waes.diffcheck.exception.DomainNotFoundException;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class CacheStorageServiceTest {

    private CacheStorageService cacheStorageService;

    @Before
    public void setUp() {
        this.cacheStorageService = new CacheStorageService();
    }

    @Test
    public void it_should_cache_left() {
        //given
        String id = "1";
        String leftJson = "{\"data\":\"value\"}";

        //when
        cacheStorageService.storeLeft(id, leftJson);

        //then
        String cachedLeftJson = cacheStorageService.getLeft(id);
        assertThat(cachedLeftJson).isEqualTo(leftJson);
    }

    @Test
    public void it_should_cache_right() {
        //given
        String id = "1";
        String rightJson = "{\"data\":\"value\"}";

        //when
        cacheStorageService.storeRight(id, rightJson);

        //then
        String cachedRightJson = cacheStorageService.getRight(id);
        assertThat(cachedRightJson).isEqualTo(rightJson);
    }

    @Test
    public void it_should_get_left_from_cache() {
        //given
        String id = "1";
        String leftJson = "{\"data\":\"value\"}";
        cacheStorageService.storeLeft(id, leftJson);

        //when
        String cachedLeft = cacheStorageService.getLeft(id);

        //then
        assertThat(cachedLeft).isEqualTo(leftJson);
    }

    @Test
    public void it_should_get_right_from_cache() {
        //given
        String id = "1";
        String rightJson = "{\"data\":\"value\"}";
        cacheStorageService.storeRight(id, rightJson);

        //when
        String cachedRight = cacheStorageService.getRight(id);

        //then
        assertThat(cachedRight).isEqualTo(rightJson);
    }

    @Test
    public void it_should_throw_domain_not_found_exception_when_id_not_exist_in_left_cache() {
        //given
        String id = "1";

        //when
        Throwable throwable = catchThrowable(() -> cacheStorageService.getLeft(id));

        //then
        assertThat(throwable).isInstanceOf(DomainNotFoundException.class);
    }

    @Test
    public void it_should_throw_domain_not_found_exception_when_id_not_exist_in_right_cache() {
        //given
        String id = "1";

        //when
        Throwable throwable = catchThrowable(() -> cacheStorageService.getRight(id));

        //then
        assertThat(throwable).isInstanceOf(DomainNotFoundException.class);
    }
}