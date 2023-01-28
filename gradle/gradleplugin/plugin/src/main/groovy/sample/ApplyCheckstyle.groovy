package sample

import org.gradle.api.Plugin
import org.gradle.api.Project

class ApplyCheckstyle implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.plugins.apply('checkstyle')
    }
}
