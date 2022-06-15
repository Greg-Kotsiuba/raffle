package com.gk.springbootskeleton.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test for {@link RaffleService}
 */
class RaffleServiceTest {

    private static final String VALID_ADDRESS = "0x0000000000000000000000000000000000000Abc";
    private static final String VALID_CODE = "12345";

    private RaffleService raffleService;

    @BeforeEach
    void setUp() {
        raffleService = new RaffleService();
    }

    @DisplayName("Test submit raffle with invalid address")
    @ParameterizedTest
    @NullAndEmptySource
    @MethodSource("provideInvalidAddresses")
    public void testSubmitRaffle_withInvalidAddress(String address) {
        List<String> validCodes = List.of(VALID_CODE);

        SubmitResult result = raffleService.submitRaffle(address, validCodes);

        assertFalse(result.getApproved(), "Should be NOT approved");
        assertEquals(1, result.getErrors().size(), "Should have one error");
        assertEquals(RaffleValidator.INVALID_ADDRESS, result.getErrors().get(address));
    }

    private static Stream<Arguments> provideInvalidAddresses() {
        String tooLongAddress = "0x0000000000abc0000000000000000000000000000";
        String tooShortAddress = "0x0000000000abc00000000000000000000000000";
        String addressWithSpecialSymbol = "0x0000000000%bc000000000000000000000000000";
        String addressThatStartsFrom1 = "1x0000000000abc000000000000000000000000000";
        return Stream.of(
                Arguments.of(tooLongAddress),
                Arguments.of(tooShortAddress),
                Arguments.of(addressWithSpecialSymbol),
                Arguments.of(addressThatStartsFrom1)
        );
    }

    @DisplayName("Test submit raffle with invalid code list size")
    @ParameterizedTest
    @NullSource
    @MethodSource("provideCodeListsWithInvalidAmountOfCodes")
    public void testSubmitRaffle_withInvalidCodeListSize(List<String> testCodeList) {
        SubmitResult result = raffleService.submitRaffle(VALID_ADDRESS, testCodeList);

        assertFalse(result.getApproved(), "Should be NOT approved");
        assertEquals(1, result.getErrors().size(), "Should have one error");
        assertEquals(
                RaffleValidator.INVALID_SIZE_OF_RAFFLE_LIST,
                result.getErrors().get(RaffleValidator.INVALID_SIZE_OF_RAFFLE_LIST_KEY)
        );
    }

    private static Stream<Arguments> provideCodeListsWithInvalidAmountOfCodes() {
        List<String> listWithTooManyCodes = List.of("12345", "12345", "12345", "12345", "12345",
                "12345", "12345", "12345", "12345", "12345", "12345");
        return Stream.of(
                Arguments.of(Collections.emptyList()),
                Arguments.of(listWithTooManyCodes)
        );
    }

    @DisplayName("Test submit raffle with invalid codes ")
    @ParameterizedTest
    @NullSource
    @MethodSource("provideCodeListsWithInvalidContent")
    void testSubmitRaffle_withInvalidCode(String invalidCode) {
        List<String> codes = Arrays.asList(VALID_CODE, invalidCode);

        SubmitResult result = raffleService.submitRaffle(VALID_ADDRESS, codes);

        assertFalse(result.getApproved(), "Should be NOT approved");
        assertNull(result.getErrors().get(VALID_CODE), "Should have NO error for valid code");
        assertEquals(1, result.getErrors().size(), "Should have one error for invalid code");
        assertEquals(
                RaffleValidator.INVALID_CODE,
                result.getErrors().get(invalidCode),
                "Should have correct error for invalid code"
        );
    }

    private static Stream<Arguments> provideCodeListsWithInvalidContent() {
        String tooShortCode = "1234";
        String tooLongCode = "123456";
        String codeWithCharacters = "a2345";
        String codeWithSpecialSymbol = "1234%";
        String codeWithDuplicates = "11234";
        return Stream.of(
                Arguments.of(tooShortCode),
                Arguments.of(tooLongCode),
                Arguments.of(codeWithCharacters),
                Arguments.of(codeWithSpecialSymbol),
                Arguments.of(codeWithDuplicates)
        );
    }

    @DisplayName("Test submit raffle")
    @Test
    public void testSubmitRaffle() {
        List<String> validCodes = List.of(VALID_CODE, VALID_CODE, "23456", "34567", "45678", "56789");

        SubmitResult result = raffleService.submitRaffle(VALID_ADDRESS, validCodes);

        assertTrue(result.getApproved(), "Should be approved");
        assertTrue(result.getErrors().isEmpty(), "Should have NO errors");
    }

    @DisplayName("Test get codes by address")
    @Test
    public void testGetCodesByAddress() {
        List<String> validCodes = List.of(VALID_CODE, VALID_CODE, "23456", "34567", "45678", "56789");

        raffleService.submitRaffle(VALID_ADDRESS, validCodes);
        Optional<List<String>> codesByAddress = raffleService.getRaffleByAddress(VALID_ADDRESS);

        assertTrue(codesByAddress.isPresent(), "Codes should be presented");
        assertEquals(validCodes, codesByAddress.get(), "Codes should match expected list");
    }

    @DisplayName("Test get codes by address")
    @Test
    public void testGetAllCodes() { //continue from here
        List<String> validCodes = List.of(VALID_CODE, VALID_CODE, "23456", "34567", "45678", "56789");

        raffleService.submitRaffle(VALID_ADDRESS, validCodes);
        Map<String, List<String>> allRaffles = raffleService.getAllRaffles();

        Map<String, List<String>> expectedRaffles = new HashMap<>();
        expectedRaffles.put(VALID_ADDRESS, validCodes);

        assertFalse(allRaffles.isEmpty(), "Raffles should be presented");
        assertEquals(expectedRaffles, allRaffles, "Raffle list should be presented");
    }


}