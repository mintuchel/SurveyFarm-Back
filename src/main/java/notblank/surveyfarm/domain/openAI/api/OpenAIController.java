package notblank.surveyfarm.domain.openAI.api;

import notblank.surveyfarm.domain.openAI.service.OpenAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/gpt")
public class OpenAIController {

    @Autowired
    private OpenAIService openAIService;

    @PostMapping("/split")
    public List<Map.Entry<String, Integer>> splitTextIntoWords(@RequestBody List<String> text) {
        return openAIService.splitTextIntoWords(text);
    }

    @PostMapping("/summarize")
    public String summarizeAnswers(@RequestBody Map<String, List<String>> requestBody) {
        List<String> answers = requestBody.get("answers");
        return openAIService.summarizeAnswers(answers);
    }
}
