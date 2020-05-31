package com.pedrosessions.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.pedrosessions.dto.Client;
import com.pedrosessions.dto.Expense;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ValidationServiceTest {

    private static final LocalDateTime CLIENT_SINCE = LocalDateTime.now().minusYears(2);
    private static final String ACCOUNT = "1234";
    private static final String AGENCY = "587";
    private static final String NAME = "Pedro";
    private static final String SEGMENT = "Premium";
    private static final String SEGMENT_STANDARD = "Standard";

    @InjectMocks
    private ValidationService validationService;

    private Expense expense;
    private Expense expense2;
    private Expense expense3;
    private Expense expense4;
    private Expense expense5;
    private Expense expense6;

    @BeforeEach
    public void setUp() {
        expense = Expense.builder().amount(new BigDecimal("200"))
                .dateOfOccurence(LocalDateTime.now().minusMonths(1).withHour(9)).build();
        expense2 = Expense.builder().amount(new BigDecimal("300"))
                .dateOfOccurence(LocalDateTime.now().minusMonths(1).withHour(12)).build();
        expense3 = Expense.builder().amount(new BigDecimal("500"))
                .dateOfOccurence(LocalDateTime.now().minusMonths(1).withHour(16).withDayOfMonth(25)).build();
        expense4 = Expense.builder().amount(new BigDecimal("450"))
                .dateOfOccurence(LocalDateTime.now().minusMonths(1).withHour(14).withDayOfMonth(12)).build();
        expense5 = Expense.builder().amount(new BigDecimal("600"))
                .dateOfOccurence(LocalDateTime.now().minusMonths(1).withHour(17).withDayOfMonth(18)).build();
        expense6 = Expense.builder().amount(new BigDecimal("1500"))
                .dateOfOccurence(LocalDateTime.now().minusMonths(1).withHour(20).withDayOfMonth(8)).build();
    }

    @Test
    @DisplayName("Validates that the client meets the requirements to receive the cashback and returns true")
    void validateSuccess() {
        // Arrange
        Client client = Client.builder().account(ACCOUNT).agency(AGENCY).name(NAME).clientSince(CLIENT_SINCE)
                .segment(SEGMENT).expenses(Arrays.asList(expense, expense2, expense3, expense4, expense5, expense6)).build();

        // Act
        boolean returned = validationService.validate(client);

        // Assert
        assertTrue(returned);
    }

    @Test
    @DisplayName("Validates that the client has expended less than $2000 and returns false")
    void validateExpenses() {
        // Arrange
        Client client = Client.builder().account(ACCOUNT).agency(AGENCY).name(NAME).clientSince(CLIENT_SINCE)
                .segment(SEGMENT).expenses(Arrays.asList(expense)).build();

        // Act
        boolean returned = validationService.validate(client);

        // Assert
        assertFalse(returned);
    }

    @Test
    @DisplayName("Validates that the client is not on the Premium segment and returns false")
    void validateSegment() {
        // Arrange
        Client client = Client.builder().account(ACCOUNT).agency(AGENCY).name(NAME).clientSince(CLIENT_SINCE)
                .segment(SEGMENT_STANDARD).expenses(Arrays.asList(expense, expense2, expense3, expense4, expense5,
                        expense6)).build();

        // Act
        boolean returned = validationService.validate(client);

        // Assert
        assertFalse(returned);
    }

    @Test
    @DisplayName("Test the validation of the time as client, when less than two years should return false")
    void validateTimeAsClient() {
        // Arrange
        LocalDateTime newer = LocalDateTime.now().minusMonths(3);

        Client client = Client.builder().account(ACCOUNT).agency(AGENCY).name(NAME).clientSince(newer)
                .segment(SEGMENT).expenses(Arrays.asList(expense, expense2, expense3, expense4, expense5, expense6)).build();

        // Act
        boolean returned = validationService.validate(client);

        // Assert
        assertFalse(returned);
    }
}
