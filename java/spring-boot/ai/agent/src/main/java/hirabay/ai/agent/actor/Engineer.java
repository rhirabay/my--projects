package hirabay.ai.agent.actor;

import hirabay.ai.agent.client.OpenAiChatClient;
import hirabay.ai.agent.function.PutProjectContentFunction;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Engineer {
    private final OpenAiChatClient openAiChatClient;

    public record CordingResult(
            String packageName,
            String className,
            String[] methods,
            String[] fields
    ) {
        @Override
        public String toString() {
            return """
                    %s.%s
                    - methods: [%s]
                    - fields: [%s]
                    
                    """.formatted(packageName, className, String.join(", ", methods), String.join(", ", fields));
        }
    }

    public static class CodingResultList extends ArrayList<CordingResult> {
        public static CodingResultList merge(CodingResultList... lists) {
            var result = new CodingResultList();
            for (var list : lists) {
                result.addAll(list);
            }
            return result;
        }

        public String toString() {
            return this.stream()
                    .map(CordingResult::toString)
                    .collect(Collectors.joining("\n"));
        }
    }

    public CodingResultList coding(String prompt, String request, String project, CodingResultList codingResultList) {
        var customizedPrompt = """
                %s
                
                [制約]
                ・実装したコードはすべてプロジェクトに追加すること
                ・Javaクラスの情報を以下のjson形式でjsonのみ返却してください。（コードブロックも不要）
                [{
                  packageName: string, // パッケージ名
                  className: string, // クラス名
                  methods: string[], // メソッド名＋引数　例：sample(String)
                  fields: string[], // フィールド名（公開されているフィールドのみ）
                }]
                
                [実装指示]
                %s
                
                [プロジェクト全体（実装指示外の内容を含みます）]
                %s
                
                [実装済みクラス]
                %s
                """.formatted(prompt, request, project, codingResultList.toString());

        return openAiChatClient.chat(customizedPrompt, CodingResultList.class, Set.of(PutProjectContentFunction.class));
    }
}
