package notblank.boatvote.domain.gpt.controller;

import notblank.boatvote.domain.gpt.service.ChatGptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ChatGptController {

    @Autowired
    private ChatGptService chatGptService;

    @GetMapping("/split")
    public List<Map.Entry<String, Integer>> splitTextIntoWords(@RequestParam String text) {
        return chatGptService.splitTextIntoWords(text);
    }
}
