package hirabay.migration.scheduledtask;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTaskSample {
    @Scheduled(fixedRate = 10_000) // ms
    public void sampleTask() {
        System.out.println("Scheduled Task Sample");
    }
}
