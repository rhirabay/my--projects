package hirabay.batch.tasklet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Slf4j
@StepScope // Step実行時にBean生成させるためのアノテーション
@Component
public class HelloWorldTasklet implements Tasklet {
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
            throws Exception {
        System.out.println(" _   _      _ _         __        __         _     _ ");
        System.out.println("| | | | ___| | | ___    \\ \\      / /__  _ __| | __| |");
        System.out.println("| |_| |/ _ \\ | |/ _ \\    \\ \\ /\\ / / _ \\| '__| |/ _` |");
        System.out.println("|  _  |  __/ | | (_) |    \\ V  V / (_) | |  | | (_| |");
        System.out.println("|_| |_|\\___|_|_|\\___( )    \\_/\\_/ \\___/|_|  |_|\\__,_|");
        System.out.println("                    |/                               ");

        return RepeatStatus.FINISHED;
    }
}