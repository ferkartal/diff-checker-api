package com.waes.diffcheck.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.waes.diffcheck.exception.DomainNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * StorageService implementation using Caffeine cache
 */
@Service
public class CacheStorageService implements StorageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheStorageService.class);
    private static final int EXPIRE_AFTER_WRITE_IN_MINUTES = 15;
    private static final int MAXIMUM_SIZE = 1000;
    public static final String LEFT_CACHE = "left";
    public static final String RIGHT_CACHE = "right";
    private CaffeineCache left;
    private CaffeineCache right;

    public CacheStorageService() {
        left = new CaffeineCache(LEFT_CACHE, buildCaffeineCache());
        right = new CaffeineCache(RIGHT_CACHE, buildCaffeineCache());
    }

    /**
     * Stores leftJson in to cache based on id
     *
     * @param id       The id presents unique identifier of leftJson
     * @param leftJson Json data
     */
    @Override
    public void storeLeft(String id, String leftJson) {
        left.put(id, leftJson);
        LOGGER.info("{} is stored in cache successfully", leftJson);
    }

    /**
     * Stores rightJson in to cache based on id
     *
     * @param id        The id presents unique identifier of rightJson
     * @param rightJson Json data
     */
    @Override
    public void storeRight(String id, String rightJson) {
        right.put(id, rightJson);
        LOGGER.info("{} is stored in cache successfully", rightJson);
    }

    /**
     * Returns leftJson from cache based on id
     *
     * @param id The id presents unique identifier of leftJson
     * @return cached Json data
     * @throws DomainNotFoundException In case of id does not exist in the left cache
     */
    @Override
    public String getLeft(String id) {
        String cachedLeft = left.get(id, String.class);
        if (cachedLeft == null) {
            throw new DomainNotFoundException("Left value not found with id : " + id);
        }
        return cachedLeft;
    }

    /**
     * Returns rightJson from cache based on id
     *
     * @param id The id presents unique identifier of rightJson
     * @return cached Json data
     * @throws DomainNotFoundException In case of id does not exist in the right cache
     */
    @Override
    public String getRight(String id) {
        String cachedRight = right.get(id, String.class);
        if (cachedRight == null) {
            throw new DomainNotFoundException("Right value not found with id : " + id);
        }
        return cachedRight;
    }

    /**
     * Build caffeine cache with expireAfterWrite and maximumSize settings
     *
     * @return manual populated caffeine cache
     */
    private Cache<Object, Object> buildCaffeineCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(EXPIRE_AFTER_WRITE_IN_MINUTES, TimeUnit.MINUTES)
                .maximumSize(MAXIMUM_SIZE)
                .build();
    }
}