package com.waes.diffcheck.service;

/**
 * This interface allows us to store and retrieve the data
 */
public interface StorageService {

    /**
     * Stores leftJson
     *
     * @param id       The id presents unique identifier of leftJson
     * @param leftJson Json data
     */
    void storeLeft(String id, String leftJson);

    /**
     * Stores rightJson
     *
     * @param id        The id presents unique identifier of rightJson
     * @param rightJson Json data
     */
    void storeRight(String id, String rightJson);

    /**
     * Retrieves leftJson based on id
     *
     * @param id The id presents unique identifier of leftJson
     * @return Stored Json data
     */
    String getLeft(String id);

    /**
     * Retrieves rightJson based on id
     *
     * @param id The id presents unique identifier of rightJson
     * @return Stored Json data
     */
    String getRight(String id);
}