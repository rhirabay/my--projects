package sample

import org.gradle.api.Plugin
import org.gradle.api.Project

class ModSetting implements Plugin<Project> {
    @Override
    void apply(Project project) {
        // 利用者側で設定可能なのはこっち
        project.tasks.getByName('bootJar').property('classifier', 'custom')

        // 利用者側の設定も上書きしちゃうのはこっち
        project.afterEvaluate {
            project.tasks.getByName('bootJar').setProperty('classifier', 'custom')
        }
    }
}
