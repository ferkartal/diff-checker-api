package com.waes.diffcheck.service;

import com.waes.diffcheck.domain.entity.LeftEntity;
import com.waes.diffcheck.domain.entity.RightEntity;
import com.waes.diffcheck.exception.DomainNotFoundException;
import com.waes.diffcheck.repository.LeftDataRepository;
import com.waes.diffcheck.repository.RightDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * StorageService implementation using Spring Data Jpa [H2]
 */
@Service
public class DatabaseStorageService implements StorageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseStorageService.class);

    private final LeftDataRepository leftDataRepository;
    private final RightDataRepository rightDataRepository;

    /**
     * *
     *
     * @param leftDataRepository
     * @param rightDataRepository
     */
    public DatabaseStorageService(LeftDataRepository leftDataRepository, RightDataRepository rightDataRepository) {
        this.leftDataRepository = leftDataRepository;
        this.rightDataRepository = rightDataRepository;
    }

    /**
     * Stores LeftEntity in to database
     *
     * @param id       The id presents unique identifier of leftJson
     * @param leftJson Json data
     */
    @Override
    public void storeLeft(String id, String leftJson) {
        LeftEntity leftEntity = LeftEntity.builder().id(id).value(leftJson).build();
        leftDataRepository.save(leftEntity);
        LOGGER.info("{} is saved database successfully", leftEntity.toString());
    }

    /**
     * Stores RightEntity in to database
     *
     * @param id        The id presents unique identifier of rightJson
     * @param rightJson Json data
     */
    @Override
    public void storeRight(String id, String rightJson) {
        RightEntity rightEntity = RightEntity.builder().id(id).value(rightJson).build();
        rightDataRepository.save(rightEntity);
        LOGGER.info("{} is saved database successfully", rightEntity.toString());
    }

    /**
     * Retrieves leftJson from database based on id
     *
     * @param id The id presents unique identifier of leftJson
     * @return Json data
     * @throws DomainNotFoundException In case of id does not exist in the left_data database
     */
    @Override
    public String getLeft(String id) {
        Optional<LeftEntity> optionalLeftEntity = leftDataRepository.findById(id);
        var leftEntity = optionalLeftEntity.orElseThrow(() -> new DomainNotFoundException("Left value not found with id : " + id));
        return leftEntity.getValue();
    }

    /**
     * Retrieves rightJson from database based on id
     *
     * @param id The id presents unique identifier of leftJson
     * @return Json data
     * @throws DomainNotFoundException In case of id does not exist in the right_data database
     */
    @Override
    public String getRight(String id) {
        Optional<RightEntity> optionalRightEntity = rightDataRepository.findById(id);
        var rightEntity = optionalRightEntity.orElseThrow(() -> new DomainNotFoundException("Right value not found with id : " + id));
        return rightEntity.getValue();
    }
}