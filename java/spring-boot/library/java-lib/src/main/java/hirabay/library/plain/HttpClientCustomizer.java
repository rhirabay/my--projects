package hirabay.library.plain;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;

public class HttpClientCustomizer {
    public static HttpClient customize(HttpClientBuilder builder) {
        // カスタマイズ
        return builder.build();
    }
}
