package com.waes.diffcheck.controller;

import com.waes.diffcheck.domain.response.DiffCheckerResponse;
import com.waes.diffcheck.service.Base64DecoderService;
import com.waes.diffcheck.service.DiffCheckerService;
import com.waes.diffcheck.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for below endpoints
 * <p>
 * /v1/diff/{id}/left  store left Json data
 * /v1/diff/{id}/right store right Json data
 * /v1/diff/{id}       provides diff-ed information
 */
@RestController
@RequestMapping("/v1/diff")
public class DiffCheckerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiffCheckerController.class);

    private final StorageService storageService;
    private final DiffCheckerService diffCheckerService;
    private final Base64DecoderService base64DecoderService;

    /**
     * @param storageService       Stores Json data. It can be either database storage or cache storage
     * @param diffCheckerService   Checks differences between two json data
     * @param base64DecoderService Decodes encoded Json data
     */
    public DiffCheckerController(@Qualifier("cacheStorageService") StorageService storageService,
                                 DiffCheckerService diffCheckerService,
                                 Base64DecoderService base64DecoderService) {
        this.storageService = storageService;
        this.diffCheckerService = diffCheckerService;
        this.base64DecoderService = base64DecoderService;
    }

    /**
     * Rest end point for storing encoded json data
     *
     * @param id                The id presents unique identifier of given resource
     * @param base64EncodedJson Base64 Encoded Json data
     * @return HTTP Created(201)
     */
    @PostMapping("/{id}/left")
    @ResponseStatus(HttpStatus.CREATED)
    public void createLeft(@PathVariable("id") String id, @RequestBody String base64EncodedJson) {
        LOGGER.info("Left value storage process is started for id: {} and data: {}", id, base64EncodedJson);
        String decodedJson = base64DecoderService.decodeJson(base64EncodedJson);
        storageService.storeLeft(id, decodedJson);
    }

    /**
     * Rest end point for storing encoded json data
     *
     * @param id                The id presents unique identifier of given resource
     * @param base64EncodedJson Base64 Encoded Json data
     * @retunrn HTTP Created(201)
     */
    @PostMapping("/{id}/right")
    @ResponseStatus(HttpStatus.CREATED)
    public void createRight(@PathVariable("id") String id, @RequestBody String base64EncodedJson) {
        LOGGER.info("Right value storage process is started for id: {} and data: {}", id, base64EncodedJson);
        String decodedJson = base64DecoderService.decodeJson(base64EncodedJson);
        storageService.storeRight(id, decodedJson);
    }

    /**
     * Rest end point for checking differences between json data that are stored with
     * using left and right endpoints
     *
     * @param id The id presents unique identifier of given resource
     * @return HTTP OK(2OO) The information of differences between left and right Json data
     */
    @GetMapping("/{id}")
    public DiffCheckerResponse getDiff(@PathVariable("id") String id) {
        LOGGER.info("Diff checker process started for id: {}", id);
        String leftValue = storageService.getLeft(id);
        String rightValue = storageService.getRight(id);
        return diffCheckerService.checkDiff(leftValue, rightValue);
    }
}