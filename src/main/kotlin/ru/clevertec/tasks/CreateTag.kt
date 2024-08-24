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
        if (GitChecker().isGitInstalled() && !GitChecker().hasTagOnCommit() && tag != null) {
            val gitCommandExecutor = GitCommandExecutor()
            gitCommandExecutor.createGitTag(tag)
            gitCommandExecutor.runGitPush()
        }
    }
}

