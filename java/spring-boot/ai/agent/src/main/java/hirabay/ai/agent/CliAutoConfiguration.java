package hirabay.ai.agent;

import hirabay.ai.agent.actor.Engineer;
import hirabay.ai.agent.actor.Planner;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
@RequiredArgsConstructor
public class CliAutoConfiguration {
    private final Planner planner;
    private final Engineer engineer;


    @Bean
    public CommandLineRunner runner() {
        return args -> {
            // 設計書の読み込み
            var design = String.join("\n", Files.readAllLines(Paths.get("設計書_参照API.md")));
            // プロンプトを読み込む
            var prompts = String.join("\n", Files.readAllLines(Paths.get("README.md")));

            // 計画
            var engineers = planner.handlePrompts(prompts);

            // 計画通りにエンジニアにタスクを振り分け
            var planList = planner.plan(engineers.keySet(), design);
            System.out.println("### 計画 ###\n" + planList + "\n");

            var codingResult = new Engineer.CodingResultList();
            for (var plan : planList) {
                var prompt = engineers.get(plan.getAssignee());
                var result = engineer.coding(prompt, plan.getInstruction(), design, codingResult);
                codingResult = Engineer.CodingResultList.merge(codingResult, result);
                System.out.println("### コーディング結果 by " + plan.getAssignee() + " ###\n" + codingResult + "\n");
            }
        };
    }
}
