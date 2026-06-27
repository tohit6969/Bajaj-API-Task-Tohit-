package com.bfhl.service;

import com.bfhl.dto.BfhlRequest;
import com.bfhl.dto.BfhlResponse;

public interface BfhlService {

    /**
     * Processes the input data array and returns categorised results.
     *
     * @param request the incoming request containing the data array
     * @return BfhlResponse with all computed fields
     */
    BfhlResponse processData(BfhlRequest request);
}
