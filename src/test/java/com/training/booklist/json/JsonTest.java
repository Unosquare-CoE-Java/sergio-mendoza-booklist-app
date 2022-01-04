package com.training.booklist.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.training.booklist.dto.LoginDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    String jsonSource = "{\"test\": \"I'm testing\"}";
    String loginString = "{\"username\": \"tester\"," +
            "\"password\": \"12345\"}";
    @Test
    void parse() throws JsonProcessingException {
        JsonNode node = Json.parse(jsonSource);

        assertEquals(node.get("test").asText(), "I'm testing");
    }

    @Test
    void fromJson() throws JsonProcessingException {
        JsonNode node = Json.parse(loginString);
        LoginDto login = Json.fromJson(node, LoginDto.class);

        assertEquals(node.get("username").asText(), login.getUsername());
    }

    @Test
    void toJson() {
        LoginDto user = new LoginDto();
        user.setUsername("SaulGoodman");
        user.setPassword("BetterCallSaul");

        JsonNode node = Json.toJson(user);

        assertEquals(node.get("username").asText(), user.getUsername());
    }

    @Test
    void stringify() throws JsonProcessingException {
        String expectedResult = "{\"username\":\"SaulGoodman\"," +
                "\"password\":\"BetterCallSaul\"}";
        LoginDto user = new LoginDto();
        user.setUsername("SaulGoodman");
        user.setPassword("BetterCallSaul");

        JsonNode node = Json.toJson(user);
        String result = Json.stringify(node);

        assertEquals(result, expectedResult);
    }
}
