package sample.recipe;

import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.JavaTemplate;
import org.openrewrite.java.tree.J;

public class AddHelloMethod extends Recipe {
    // 表示名なので任意の文字列でOK
    @Override
    public String getDisplayName() {
        return "Add Hello Method";
    }

    // Visitorクラスを実装して返却する
    @Override
    protected JavaIsoVisitor<ExecutionContext> getVisitor() {
        return new AddHelloMethodVisitor();
    }

    // Visitorクラス（書き換えの具体内容を実装するクラス）
    public class AddHelloMethodVisitor extends JavaIsoVisitor<ExecutionContext> {

        @Override
        public J.ClassDeclaration visitClassDeclaration(J.ClassDeclaration classDecl, ExecutionContext executionContext) {
            // 指定したクラス以外はスキップ
            if (!classDecl.getSimpleName().equals("SampleClass")) {
                return classDecl;
            }

            // helloメソッドの存在を確認
            boolean helloMethodExists = classDecl.getBody().getStatements().stream()
                    .filter(statement -> statement instanceof J.MethodDeclaration)
                    .map(J.MethodDeclaration.class::cast)
                    .anyMatch(methodDeclaration -> methodDeclaration.getName().getSimpleName().equals("hello"));
            // helloメソッドが存在する場合はスキップ
            if (helloMethodExists) {
                return classDecl;
            }

            // 挿入するコードを定義
            var helloTemplate = JavaTemplate.builder(this::getCursor, """
                        public String hello() {
                            return "Hello, OpenRewrite!!!";
                        }
                        """)
                    .build();

            // クラスの最後尾に定義したコードを挿入
            classDecl = classDecl.withBody(
                    classDecl.getBody().withTemplate(helloTemplate, classDecl.getBody().getCoordinates().lastStatement())
            );

            return classDecl;
        }
    }
}
