package com.example;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import static org.junit.jupiter.api.Assertions.*;

class OperatorRestrictionServiceTest {
    private OperatorRestrictionService service;

    @BeforeEach
    void setup() {
        service = new OperatorRestrictionService();
        ReflectionTestUtils.setField(service, "restrictedOperators", "111,222,333");
    }

    @Test void shouldReturnTrueWhenOperatorIsRestricted() { assertTrue(service.isRestricted("111")); }
    @Test void shouldReturnFalseWhenOperatorIsNotRestricted() { assertFalse(service.isRestricted("999")); }
    @Test void shouldReturnFalseWhenOperatorIsNull() { assertFalse(service.isRestricted(null)); }
    @Test void shouldReturnFalseWhenOperatorIsBlank() { assertFalse(service.isRestricted(" ")); }
}
