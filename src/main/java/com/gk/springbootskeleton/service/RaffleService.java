package com.gk.springbootskeleton.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RaffleService {
    private static final Map<String, List<String>> raffleStorage = new HashMap<>();

    /**
     * Provides raffle codes by address
     *
     * @param address raffle address
     * @return {@link Optional} raffle code list
     */
    public Optional<List<String>> getRaffleByAddress(String address) {
        return Optional.ofNullable(raffleStorage.get(address));
    }

    /**
     * Provides all stored raffles
     *
     * @return all stored raffles
     */
    public Map<String, List<String>> getAllRaffles() {
        return raffleStorage;
    }

    /**
     * Creates new raffle
     *
     * @param address raffle address
     * @param codes raffle codes
     * @return {@link SubmitResult} operation result, that contain operation status and errors if operation is failed
     */
    public SubmitResult submitRaffle(String address, List<String> codes) {
        Map<String, String> validationMap = RaffleValidator.validateRaffle(address, codes, new HashMap<>());

        if (validationMap.isEmpty()) {
            raffleStorage.put(address, codes);
        }

        return new SubmitResult(validationMap.isEmpty(), validationMap);
    }

}

