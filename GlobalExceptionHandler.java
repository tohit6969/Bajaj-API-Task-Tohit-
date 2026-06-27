package com.bfhl;

import com.bfhl.dto.BfhlRequest;
import com.bfhl.dto.BfhlResponse;
import com.bfhl.service.BfhlService;
import com.bfhl.service.BfhlServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BfhlServiceTest {

    private BfhlService service;

    @BeforeEach
    void setUp() {
        service = new BfhlServiceImpl();
    }

    // ── Example A ────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Example A: mixed input with single chars")
    void testExampleA() {
        BfhlRequest request = new BfhlRequest(List.of("a", "1", "334", "4", "R", "$"));
        BfhlResponse response = service.processData(request);

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getUserId()).isEqualTo("john_doe_17091999");
        assertThat(response.getOddNumbers()).containsExactly("1");
        assertThat(response.getEvenNumbers()).containsExactlyInAnyOrder("334", "4");
        assertThat(response.getAlphabets()).containsExactlyInAnyOrder("A", "R");
        assertThat(response.getSpecialCharacters()).containsExactly("$");
        assertThat(response.getSum()).isEqualTo("339");
        assertThat(response.getConcatString()).isEqualTo("Ra");
    }

    // ── Example B ────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Example B: mixed input with multiple specials")
    void testExampleB() {
        BfhlRequest request = new BfhlRequest(List.of("2", "a", "y", "4", "&", "-", "*", "5", "92", "b"));
        BfhlResponse response = service.processData(request);

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getOddNumbers()).containsExactly("5");
        assertThat(response.getEvenNumbers()).containsExactlyInAnyOrder("2", "4", "92");
        assertThat(response.getAlphabets()).containsExactlyInAnyOrder("A", "Y", "B");
        assertThat(response.getSpecialCharacters()).containsExactlyInAnyOrder("&", "-", "*");
        assertThat(response.getSum()).isEqualTo("103");
        assertThat(response.getConcatString()).isEqualTo("ByA");
    }

    // ── Example C ────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Example C: only multi-char alphabetic strings")
    void testExampleC() {
        BfhlRequest request = new BfhlRequest(List.of("A", "ABCD", "DOE"));
        BfhlResponse response = service.processData(request);

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getOddNumbers()).isEmpty();
        assertThat(response.getEvenNumbers()).isEmpty();
        assertThat(response.getAlphabets()).containsExactly("A", "ABCD", "DOE");
        assertThat(response.getSpecialCharacters()).isEmpty();
        assertThat(response.getSum()).isEqualTo("0");
        assertThat(response.getConcatString()).isEqualTo("EoDdCbAa");
    }

    // ── Edge cases ────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Empty data array returns zeroed-out response")
    void testEmptyData() {
        BfhlRequest request = new BfhlRequest(Collections.emptyList());
        BfhlResponse response = service.processData(request);

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getOddNumbers()).isEmpty();
        assertThat(response.getEvenNumbers()).isEmpty();
        assertThat(response.getAlphabets()).isEmpty();
        assertThat(response.getSpecialCharacters()).isEmpty();
        assertThat(response.getSum()).isEqualTo("0");
        assertThat(response.getConcatString()).isEmpty();
    }

    @Test
    @DisplayName("Only special characters")
    void testOnlySpecialChars() {
        BfhlRequest request = new BfhlRequest(List.of("@", "#", "!"));
        BfhlResponse response = service.processData(request);

        assertThat(response.getSpecialCharacters()).containsExactlyInAnyOrder("@", "#", "!");
        assertThat(response.getSum()).isEqualTo("0");
        assertThat(response.getConcatString()).isEmpty();
    }

    @Test
    @DisplayName("Only even numbers")
    void testOnlyEvenNumbers() {
        BfhlRequest request = new BfhlRequest(List.of("2", "4", "100"));
        BfhlResponse response = service.processData(request);

        assertThat(response.getEvenNumbers()).containsExactlyInAnyOrder("2", "4", "100");
        assertThat(response.getOddNumbers()).isEmpty();
        assertThat(response.getSum()).isEqualTo("106");
    }

    @Test
    @DisplayName("Only odd numbers")
    void testOnlyOddNumbers() {
        BfhlRequest request = new BfhlRequest(List.of("1", "3", "7"));
        BfhlResponse response = service.processData(request);

        assertThat(response.getOddNumbers()).containsExactlyInAnyOrder("1", "3", "7");
        assertThat(response.getEvenNumbers()).isEmpty();
        assertThat(response.getSum()).isEqualTo("11");
    }

    @Test
    @DisplayName("Numbers are returned as strings in odd/even lists")
    void testNumbersReturnedAsStrings() {
        BfhlRequest request = new BfhlRequest(List.of("1", "2"));
        BfhlResponse response = service.processData(request);

        assertThat(response.getOddNumbers()).containsExactly("1");
        assertThat(response.getEvenNumbers()).containsExactly("2");
    }

    @Test
    @DisplayName("user_id format is correct")
    void testUserId() {
        BfhlRequest request = new BfhlRequest(List.of("x"));
        BfhlResponse response = service.processData(request);

        // format: fullname_ddmmyyyy, all lowercase
        assertThat(response.getUserId()).matches("[a-z_]+_\\d{8}");
        assertThat(response.getUserId()).isEqualTo("john_doe_17091999");
    }

    @Test
    @DisplayName("Concat string with single alphabet is correct")
    void testConcatStringSingleAlphabet() {
        // Single letter "b" → uppercase → ["B"]
        // Reversed letters = [B], alternating-caps index 0 → uppercase → "B"
        BfhlRequest request = new BfhlRequest(List.of("b"));
        BfhlResponse response = service.processData(request);
        assertThat(response.getConcatString()).isEqualTo("B");
    }

    @Test
    @DisplayName("Large numeric sum")
    void testLargeSum() {
        BfhlRequest request = new BfhlRequest(List.of("1000", "2000", "3000"));
        BfhlResponse response = service.processData(request);
        assertThat(response.getSum()).isEqualTo("6000");
    }
}
