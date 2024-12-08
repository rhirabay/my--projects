package hirabay.testcontainers;

import org.springframework.boot.SpringApplication;

public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.from(MainApplication::main) // main配下のメインクラスを指定
                .with(TestContainersConfiguration.class) // 先ほど作成したConfigurationクラスを指定
                .run(args);
    }
}
