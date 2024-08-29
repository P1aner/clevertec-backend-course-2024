package ru.clevertec.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import ru.clevertec.extensions.TagExtension
import ru.clevertec.services.GitChecker
import ru.clevertec.services.GitCommandExecutor
import ru.clevertec.services.TagGenerator
import ru.clevertec.services.TagParser


abstract class ShowIntendedTag : DefaultTask() {
    @TaskAction
    fun action() {
        val exten = project.extensions.getByType(TagExtension::class.java)
        if (exten.isGitInstalled && !exten.hasTagOnCommit) {
            val gitCommandExecutor = GitCommandExecutor()
            gitCommandExecutor.runGitFetch()
            val tagsOnCurrentCommit = gitCommandExecutor.getGitTagsOnCurrentCommit()
            val majorNumberOfGreatestVersion =
                TagParser().getMajorNumberOfGreatestVersion(tagsOnCurrentCommit)
            val minorNumberOfGreatestVersion =
                TagParser().getMinorNumberOfGreatestVersion(tagsOnCurrentCommit)
            val branch = gitCommandExecutor.getGitCurrentBranch()
            val tag = TagGenerator().generateTag(
                branch,
                majorNumberOfGreatestVersion,
                minorNumberOfGreatestVersion
            )

            if (GitChecker().hasModifiedFiles() || GitChecker().hasUntrackedFiles()) {
                println("$tag.uncommitted")
                println("tag will be not created")
            } else {
                println(tag)
                exten.tagOnCommit = tag
            }
        }
    }
}