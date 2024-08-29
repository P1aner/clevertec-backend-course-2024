package ru.clevertec.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import ru.clevertec.extensions.TagExtension
import ru.clevertec.services.GitChecker


abstract class CheckGitInstalled : DefaultTask() {
    @TaskAction
    fun action() {
        val exten = project.extensions.getByType(TagExtension::class.java)
        exten.isGitInstalled = GitChecker().isGitInstalled()
        exten.isGitConnectedToOrigin = GitChecker().isGitConnectedToOrigin()
        if (GitChecker().isGitInstalled()) {
            println("git installed")
        } else {
            println("git not installed")
        }
        if (GitChecker().isGitConnectedToOrigin()) {
            println("git connected to origin")
        } else {
            println("git not connected to origin")
        }
    }
}