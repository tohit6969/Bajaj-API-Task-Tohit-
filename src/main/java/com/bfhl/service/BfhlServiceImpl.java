package com.bfhl.service;

import com.bfhl.dto.BfhlRequest;
import com.bfhl.dto.BfhlResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BfhlServiceImpl implements BfhlService {

    // ── User constants ──────────────────────────────────────────────────────────
    private static final String FULL_NAME   = "john_doe";
    private static final String DOB         = "17091999";
    private static final String USER_ID     = FULL_NAME + "_" + DOB;
    private static final String EMAIL       = "john@xyz.com";
    private static final String ROLL_NUMBER = "ABCD123";

    @Override
    public BfhlResponse processData(BfhlRequest request) {

        List<String> data = request.getData();

        List<String> oddNumbers       = new ArrayList<>();
        List<String> evenNumbers      = new ArrayList<>();
        List<String> alphabets        = new ArrayList<>();
        List<String> specialChars     = new ArrayList<>();
        long         numericSum       = 0;

        // ── Categorise every element ────────────────────────────────────────────
        for (String element : data) {
            if (isNumeric(element)) {
                long value = Long.parseLong(element);
                numericSum += value;
                if (value % 2 == 0) {
                    evenNumbers.add(element);   // keep original string form
                } else {
                    oddNumbers.add(element);
                }
            } else if (isAlphabetic(element)) {
                // Each element (single char or multi-char word) is uppercased as a whole
                alphabets.add(element.toUpperCase());
            } else {
                specialChars.add(element);
            }
        }

        // ── Build concat_string ─────────────────────────────────────────────────
        String concatString = buildConcatString(alphabets);

        return BfhlResponse.builder()
                .isSuccess(true)
                .userId(USER_ID)
                .email(EMAIL)
                .rollNumber(ROLL_NUMBER)
                .oddNumbers(oddNumbers)
                .evenNumbers(evenNumbers)
                .alphabets(alphabets)
                .specialCharacters(specialChars)
                .sum(String.valueOf(numericSum))
                .concatString(concatString)
                .build();
    }

    // ── Helpers ─────────────────────────────────────────────────────────────────

    /**
     * Returns true when the entire token is a valid integer (positive or negative).
     */
    private boolean isNumeric(String s) {
        if (s == null || s.isEmpty()) return false;
        try {
            Long.parseLong(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Returns true when every character in the token is a letter (a-z / A-Z).
     * This correctly handles multi-character words like "ABCD" or "DOE".
     */
    private boolean isAlphabetic(String s) {
        if (s == null || s.isEmpty()) return false;
        for (char c : s.toCharArray()) {
            if (!Character.isLetter(c)) return false;
        }
        return true;
    }

    /**
     * Builds the concat_string:
     *  1. Collect every individual letter from all alphabet tokens (already uppercased).
     *  2. Reverse the collected letter list.
     *  3. Apply alternating-caps starting from the first reversed letter as uppercase.
     *
     * Example (from spec):
     *   alphabets = ["A", "ABCD", "DOE"]
     *   collected letters = [A, A, B, C, D, D, O, E]
     *   reversed          = [E, O, D, D, C, B, A, A]
     *   alternating-caps  = E(upper) o(lower) D(upper) d(lower) C(upper) b(lower) A(upper) a(lower)
     *                     = "EoDdCbAa"  ✓
     */
    private String buildConcatString(List<String> uppercasedAlphabets) {
        // Step 1 – flatten to individual letters (all uppercase since alphabets list is already uppercased)
        List<Character> letters = new ArrayList<>();
        for (String word : uppercasedAlphabets) {
            for (char c : word.toCharArray()) {
                letters.add(c);
            }
        }

        if (letters.isEmpty()) return "";

        // Step 2 – reverse
        List<Character> reversed = new ArrayList<>();
        for (int i = letters.size() - 1; i >= 0; i--) {
            reversed.add(letters.get(i));
        }

        // Step 3 – alternating caps (index 0 → uppercase, index 1 → lowercase, …)
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < reversed.size(); i++) {
            char c = reversed.get(i);
            if (i % 2 == 0) {
                sb.append(Character.toUpperCase(c));
            } else {
                sb.append(Character.toLowerCase(c));
            }
        }

        return sb.toString();
    }
}
