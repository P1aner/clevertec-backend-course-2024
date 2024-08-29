package ru.clevertec.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import ru.clevertec.extensions.TagExtension
import ru.clevertec.services.GitChecker
import ru.clevertec.services.GitCommandExecutor

abstract class CreateTag : DefaultTask() {

    @TaskAction
    fun action() {
        val exten = project.extensions.getByType(TagExtension::class.java)
        val tag = exten.tagOnCommit
        val gitCommandExecutor = GitCommandExecutor()
        if (GitChecker().isGitInstalled() && !GitChecker().hasTagOnCommit() && tag != null) {
            gitCommandExecutor.createGitTag(tag)
        }
        if (exten.isGitConnectedToOrigin) {
            gitCommandExecutor.runGitPush()
            println("tag pushed to origin")
        }
    }
}

