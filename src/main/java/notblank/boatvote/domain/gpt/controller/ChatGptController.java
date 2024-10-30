package notblank.boatvote.domain.gpt.controller;

import notblank.boatvote.domain.gpt.service.ChatGptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

package notblank.boatvote.domain.gpt.controller;

import notblank.boatvote.domain.gpt.service.ChatGptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/gpt")
public class ChatGptController {

    @Autowired
    private ChatGptService chatGptService;

    @PostMapping("/split")
    public List<Map.Entry<String, Integer>> splitTextIntoWords(@RequestBody List<String> text) {
        return chatGptService.splitTextIntoWords(text);
    }

    @PostMapping("/summarize")
    public String summarizeAnswers(@RequestBody Map<String, List<String>> requestBody) {
        List<String> answers = requestBody.get("answers");
        return chatGptService.summarizeAnswers(answers);
    }
}
