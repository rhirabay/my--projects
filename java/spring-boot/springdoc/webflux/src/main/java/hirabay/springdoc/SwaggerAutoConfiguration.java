package hirabay.springdoc;

import hirabay.springdoc.model.CommonErrorResponse;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import lombok.Data;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        servers = {
                @Server(url = "https://<開発環境ドメイン>", description = "開発環境"),
                @Server(url = "https://<本番環境ドメイン>", description = "本番環境")
        },
        info = @Info(title = "Sample API", description = "解説用のサンプルAPIです。")
)
public class SwaggerAutoConfiguration {
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("sample apis")
                .addOpenApiCustomizer(new CommonErrorResponseCustomizer())
                .build();
    }

    public static class CommonErrorResponseCustomizer implements OpenApiCustomizer {
        @Override
        public void customise(OpenAPI openApi) {
            openApi.getComponents().getSchemas().putAll(ModelConverters.getInstance().read(CommonErrorResponse.class));
            openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {
                ApiResponses apiResponses = operation.getResponses();

                Schema schema = new Schema();
                schema.setName("CommonErrorResponse");
                schema.set$ref("#/components/schemas/CommonErrorResponse");

                MediaType mediaType = new MediaType();
                mediaType.schema(schema);
                ApiResponse apiResponse = new ApiResponse()
                        .content(new Content()
                                .addMediaType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE, mediaType));
                apiResponses.addApiResponse("500", apiResponse);
            }));
        }
    }
}
