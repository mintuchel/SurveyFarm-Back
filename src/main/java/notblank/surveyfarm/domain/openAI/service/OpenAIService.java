package notblank.surveyfarm.domain.openAI.service;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
public class OpenAIService {
    private final String apiKey = System.getenv("GPT_API_KEY");

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Map.Entry<String, Integer>> splitTextIntoWords(List<String> inputTexts) {
        String url = "https://api.openai.com/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        ObjectNode requestBody = objectMapper.createObjectNode();
        requestBody.put("model", "gpt-3.5-turbo");

        ArrayNode messages = objectMapper.createArrayNode();

        ObjectNode systemMessage = objectMapper.createObjectNode();
        systemMessage.put("role", "system");
        systemMessage.put("content",
                "You will be given multiple short answer responses. Tokenize the responses, " +
                        "count the frequency of each word, and return the results in JSON format as " +
                        "[{\"word\":\"frequency\"}]. Please only include nouns, adjectives, and adverbs, " +
                        "excluding any articles, prepositions, conjunctions, or particles. " +
                        "If the response contains verbs, please return their base forms as keywords.");
        messages.add(systemMessage);

        ObjectNode userMessage = objectMapper.createObjectNode();
        userMessage.put("role", "user");
        userMessage.put("content", String.join(" ", inputTexts));
        messages.add(userMessage);

        requestBody.set("messages", messages);
        requestBody.put("temperature", 0.7);
        requestBody.put("max_tokens", 150);

        try {
            String body = objectMapper.writeValueAsString(requestBody);
            HttpEntity<String> entity = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            String responseBody = response.getBody();

            JsonNode jsonNode = objectMapper.readTree(responseBody);
            String generatedText = jsonNode.path("choices").get(0).path("message").path("content").asText().trim();

            List<Map.Entry<String, Integer>> wordCountList = parseWordFrequency(generatedText);
            return wordCountList;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private List<Map.Entry<String, Integer>> parseWordFrequency(String text) {
        List<Map.Entry<String, Integer>> wordCountList = new ArrayList<>();

        try {
            JsonNode rootNode = objectMapper.readTree(text);
            for (JsonNode node : rootNode) {
                String word = node.fieldNames().next();
                int frequency = node.path(word).asInt();
                wordCountList.add(new AbstractMap.SimpleEntry<>(word, frequency));
            }
        } catch (Exception e) {
            System.err.println("Failed to parse word frequency: " + e.getMessage());
        }

        return wordCountList;
    }

    public String summarizeAnswers(List<String> answers){

        String url = "https://api.openai.com/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        ObjectNode requestBody = objectMapper.createObjectNode();
        requestBody.put("model", "gpt-3.5-turbo");

        ArrayNode messages = objectMapper.createArrayNode();

        ObjectNode systemMessage = objectMapper.createObjectNode();
        systemMessage.put("role", "system");
        systemMessage.put("content","You will be given multiple short answer responses. Please summarize the answers in Korean.");
        messages.add(systemMessage);

        ObjectNode userMessage = objectMapper.createObjectNode();
        userMessage.put("role", "user");
        userMessage.put("content", String.join(" ", answers));
        messages.add(userMessage);

        requestBody.set("messages", messages);
        requestBody.put("temperature", 0.7);
        requestBody.put("max_tokens", 150);

        try {
            String body = objectMapper.writeValueAsString(requestBody);
            HttpEntity<String> entity = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            String responseBody = response.getBody();

            JsonNode jsonNode = objectMapper.readTree(responseBody);
            String generatedText = jsonNode.path("choices").get(0).path("message").path("content").asText().trim();

            return generatedText;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}