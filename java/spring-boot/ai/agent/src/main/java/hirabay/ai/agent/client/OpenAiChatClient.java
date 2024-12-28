package hirabay.ai.agent.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class OpenAiChatClient {
    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;

    public OpenAiChatClient(ChatClient.Builder builder, ObjectMapper objectMapper) {
        this.chatClient = builder
                .build();
        this.objectMapper = objectMapper;
    }

    public String chat(String message) {
        return chatClient.prompt(message)
                .call()
                .content();
    }

    public String chat(String message, Set<Class<? extends Function<?, ?>>> functionTypes) {
        var functionSet = functionTypes.stream()
                .map(Class::getSimpleName)
                .collect(Collectors.toSet());
        var prompt = new Prompt(
                message,
                OpenAiChatOptions.builder()
                        .withFunctions(functionSet)
                        .build()
        );

        return chatClient.prompt(prompt)
                .call()
                .content();
    }

    @SneakyThrows
    public <T> T chat(String prompt, Class<T> responseType, Set<Class<? extends Function<?, ?>>> functionTypes) {
        var functionSet = functionTypes.stream()
                .map(Class::getSimpleName)
                .collect(Collectors.toSet());

        var promptObj = new Prompt(
                prompt,
                OpenAiChatOptions.builder()
                        .withFunctions(functionSet)
                        .build()
        );

        var response = chatClient.prompt(promptObj)
                .call()
                .content();

        try {
            return objectMapper.readValue(response, responseType);
        } catch (Exception ex) {
            System.out.println("failed to parse json.\n" + response);
            throw ex;
        }
    }

    @SneakyThrows
    public <T> T chat(String prompt, Class<T> responseType) {
        var response = chatClient.prompt(prompt)
                .call()
                .content();

        return objectMapper.readValue(response, responseType);
    }
}
