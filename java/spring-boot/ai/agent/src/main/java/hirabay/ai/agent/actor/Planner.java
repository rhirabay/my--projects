package hirabay.ai.agent.actor;

import hirabay.ai.agent.client.OpenAiChatClient;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Planner {
    private final OpenAiChatClient openAiChatClient;
    private final HashMap<String, String> responseType = new HashMap<String, String>();

    public static class PromptMap extends HashMap<String, String> {
    }

    @Data
    public static class Plan {
        private String assignee;
        private String instruction;
    }

    public static class PlanList extends ArrayList<Plan> {
    }

    public PromptMap handlePrompts(String promptsText) {

        return openAiChatClient.chat("""
                プロジェクトとプロジェクトメンバーの説明から、情報を抜き出してください。
                抜き出した情報は出力イメージの通り、key valueのMapをjson形式で出力してください。
                回答はjsonのみを出力してください。
                コードブロックも不要です、削除してください。
                
                # 出力イメージ
                
                {
                  "エンジニア名", "エンジニアに対する指示・ドキュメントの全文（原文のまま）"
                }
                
                # プロジェクトとプロジェクトメンバーの説明
                %s
                """.formatted(promptsText), PromptMap.class);
    }

    public PlanList plan(Collection<String> engineers, String task) {
        var engineersText = engineers.stream()
                .map(engineer -> "・" + engineer)
                .collect(Collectors.joining("\n"));
        return openAiChatClient.chat("""
                あなたのタスクは、プロジェクトの進行計画をすることです。
                タスクの内容を注意深く確認し、タスクを分割＆担当エンジニアに振り分けてください。
                
                [制約]
                ・タスクは実現したいことベースのタスクにしてください（例：TODOテーブルからidでデータを検索する機能を実装してください）
                ・出力イメージの通りjson形式でjsonのみを出力してください（コードブロックも不要）
                
                [担当エンジニアの一覧]
                %s
                
                [出力イメージ]
                
                [{
                  "assignee", "担当エンジニア",
                  "instruction", "担当エンジニアへの実装指示。できるだけ詳述に"
                }]
                
                # プロジェクト
                %s
                """.formatted(engineersText, task), PlanList.class);
    }
}
