package ru.clevertec.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import ru.clevertec.exception.GitIsNotInstalledException
import ru.clevertec.extensions.TagExtension
import ru.clevertec.services.GitChecker


abstract class HasTagOnCommit : DefaultTask() {
    @TaskAction
    fun action() {
        val exten = project.extensions.getByType(TagExtension::class.java)
        if (exten.isGitInstalled) {
            exten.hasTagOnCommit = GitChecker().hasTagOnCommit()
            if (GitChecker().hasTagOnCommit()) {
                println("commit has tag")
            } else {
                println("commit not has tag")
            }
        } else {
            throw GitIsNotInstalledException("git is not installed")
        }
    }
}