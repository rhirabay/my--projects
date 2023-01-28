package sample

import org.gradle.api.Plugin
import org.gradle.api.Project
import sample.extensions.HelloExtension

// パッケージ・クラス名は「implementationClass」と対応させる
class SampleGroovyPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        // extensionを登録
        project.extensions.create("hello", HelloExtension)

        project.tasks.create('hello') {
            doLast {
                // extensionの読み込みは`project.extensions.getByXxxx`を使う
                println("Hello, ${project.extensions.getByType(HelloExtension.class).message}")
            }
        }

        new ApplyCheckstyle().apply(project)
        new AddDependency().apply(project)
        new ModSetting().apply(project)
    }
}
