package sample

import org.gradle.api.Plugin
import org.gradle.api.Project

class AddDependency implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.dependencies {
            compileOnly 'org.projectlombok:lombok'
            annotationProcessor 'org.projectlombok:lombok'
        }
    }
}
