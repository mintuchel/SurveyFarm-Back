package notblank.boatvote.domain.gpt.service;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

@Service
public class ChatGptService {

    private final String apiKey = System.getenv("GPT_API_KEY")

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Map.Entry<String, Integer>> splitTextIntoWords(String inputText) {
        String url = "https://api.openai.com/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        ObjectNode requestBody = objectMapper.createObjectNode();
        requestBody.put("model", "gpt-3.5-turbo");

        ArrayNode messages = objectMapper.createArrayNode();

        ObjectNode systemMessage = objectMapper.createObjectNode();
        systemMessage.put("role", "system");
        systemMessage.put("content", "You will be given multiple short answer responses. Tokenize the responses, count the frequency of each word, and return the results in descending order. ");
        messages.add(systemMessage);

        ObjectNode userMessage = objectMapper.createObjectNode();
        userMessage.put("role", "user");
        userMessage.put("content", inputText);
        messages.add(userMessage);

        requestBody.set("messages", messages);
        requestBody.put("temperature", 0.7);
        requestBody.put("max_tokens", 50);

        try {
            String body = objectMapper.writeValueAsString(requestBody);
            HttpEntity<String> entity = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            String responseBody = response.getBody();

            JsonNode jsonNode = objectMapper.readTree(responseBody);
            String generatedText = jsonNode.path("choices").get(0).path("message").path("content").asText().trim();

            Map<String, Integer> wordCountMap = parseWordFrequency(generatedText);

            return wordCountMap.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .toList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private Map<String, Integer> parseWordFrequency(String text) {
        Map<String, Integer> wordCountMap = new HashMap<>();

        String[] words = text.split(",\\s*");

        for (String wordEntry : words) {
            String[] wordAndCount = wordEntry.split(":");
            if (wordAndCount.length == 2) {
                String word = wordAndCount[0].replaceAll("[^\\p{L}]", "").trim();

                try {
                    int count = Integer.parseInt(wordAndCount[1].trim());
                    wordCountMap.put(word, count);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid number format for word: " + wordEntry);
                }
            }
        }
        return wordCountMap;
    }
}