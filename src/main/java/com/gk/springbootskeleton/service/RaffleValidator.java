package com.gk.springbootskeleton.service;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Raffle validator.
 */
@UtilityClass
public class RaffleValidator {

    static final String INVALID_ADDRESS = "Your address should satisfy next criteria: 42 symbols long," + " starts from '0x', contain only characters and digits";
    static final String INVALID_SIZE_OF_RAFFLE_LIST_KEY = "Invalid size of raffle list";
    static final String INVALID_SIZE_OF_RAFFLE_LIST = "Raffle list should be from 1 to 10";
    static final String INVALID_CODE = "Code should be 5 digits long and contain only unique digits";

    static final Pattern ADDRESS_PATTERN = Pattern.compile("^0x([\\dA-Za-z]){40}$");
    static final Pattern CODE_PATTERN = Pattern.compile("^\\d{5}$");

    /**
     * Validate Raffle.
     * Validate that raffle address 42 symbols long, starts from '0x', contain only characters and digits
     * Validate that code list has from 1 to 10 codes, and each code had 5 digits, and they are unique
     *
     * @param address        - address to validate
     * @param raffleCodeList - list to validate
     * @param errorMap       - map that will be used for filling exceptions
     */
    public static Map<String, String> validateRaffle(String address, List<String> raffleCodeList, Map<String, String> errorMap) {
        validateAddress(address, errorMap);
        validateRaffle(raffleCodeList, errorMap);
        return errorMap;
    }

    /**
     * Validate that raffle address 42 symbols long, starts from '0x', contain only characters and digits
     *
     * @param address  - address to validate
     * @param errorMap - map that will be used for filling exceptions
     */
    public static void validateAddress(String address, Map<String, String> errorMap) {
        if (Objects.isNull(address) || !ADDRESS_PATTERN.matcher(address).matches()) {
            errorMap.put(address, INVALID_ADDRESS);
        }
    }

    /**
     * Validate that code list has from 1 to 10 codes, and each code had 5 digits, and they are unique
     *
     * @param raffleCodeList - list to validate
     * @param errors         - map that will be used for filling exceptions
     */
    public static void validateRaffle(List<String> raffleCodeList, Map<String, String> errors) {
        validateRaffleListSize(raffleCodeList, errors);
        if (errors.isEmpty()) {
            raffleCodeList.forEach(code -> validateCode(code, errors));
        }
    }

    private static void validateCode(String code, Map<String, String> errorsMap) {
        if (Objects.isNull(code) || !CODE_PATTERN.matcher(code).matches()) {
            errorsMap.put(code, INVALID_CODE);
        } else if (!isAllElementsAreUnique(code)) {
            errorsMap.put(code, INVALID_CODE);
        }
    }

    private static boolean isAllElementsAreUnique(String code) {
        int distinctCodeLength = code.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toSet())
                .size();
        return code.length() == distinctCodeLength;
    }

    private static void validateRaffleListSize(List<String> raffleList, Map<String, String> errors) {
        if (Objects.isNull(raffleList) || raffleList.isEmpty() || raffleList.size() >= 10) {
            errors.put(INVALID_SIZE_OF_RAFFLE_LIST_KEY, INVALID_SIZE_OF_RAFFLE_LIST);
        }
    }
}
