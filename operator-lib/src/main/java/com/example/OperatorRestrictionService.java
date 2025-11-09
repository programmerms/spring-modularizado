package com.example;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Component
public class OperatorRestrictionService {
    @Value("${app.restricted-operators}")
    private String restrictedOperators;

    public boolean isRestricted(String operator) {
        if (operator == null || operator.isBlank()) return false;
        return Arrays.stream(restrictedOperators.split(","))
                .map(String::trim)
                .anyMatch(op -> op.equals(operator));
    }
}
