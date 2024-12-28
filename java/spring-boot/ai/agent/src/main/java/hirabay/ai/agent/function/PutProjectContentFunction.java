package hirabay.ai.agent.function;

import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Function;

@Component(value = "PutProjectContentFunction")
@Description("実装コードをプロジェクトに追加する")
public class PutProjectContentFunction implements Function<PutProjectContentFunction.Request, PutProjectContentFunction.Response> {

    public record Request(
            @Description("ディレクトリ")
            String directory,
            @Description("ファイル名（要：拡張子）")
            String filename,
            @Description("クラスの実装内容")
            String content
    ) {
    }

    public record Response(
            String resultMessage
    ) {
    }

    @Override
    public Response apply(Request request) {
        try {
            // request.packageNameのディレクトリを作成する
            var directory = "src/main/java/" + request.packageName.replace(".", "/");
            Files.createDirectories(Paths.get(directory));

            // request.packageNameのディレクトリにrequest.className.javaファイルを作成する
            var filePath = directory + "/" + request.className + ".java";
            Files.writeString(Paths.get(filePath), request.content);

            System.out.printf("""
                    PutProjectContentFunction success.
                    - filePath: %s
                    
                    """, filePath);
            return new Response("プロジェクトにファイルを追加しました.");
        } catch (Exception ex) {
            return new Response("ファイルに失敗しました。");
        }
    }

}
