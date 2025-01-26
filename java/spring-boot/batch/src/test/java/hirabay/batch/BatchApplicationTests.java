package hirabay.batch;

import static org.assertj.core.api.Assertions.assertThat;

import hirabay.batch.chunk.mybatis.reader.ReaderConfiguration;
import hirabay.batch.tasklet.CheckAllTransactionTasklet;
import hirabay.batch.tasklet.HelloWorldTasklet;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringBatchTest
@SpringBootTest
class BatchApplicationTests {

    @Autowired private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    public void test_hello_world(@Autowired Job helloWorldTaskletJob) throws Exception {
        this.jobLauncherTestUtils.setJob(helloWorldTaskletJob);

        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");
    }

    @Test
    public void test_mybatis_sample(@Autowired Job mybatisSampleJob) throws Exception {
        this.jobLauncherTestUtils.setJob(mybatisSampleJob);

        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");
    }
}
