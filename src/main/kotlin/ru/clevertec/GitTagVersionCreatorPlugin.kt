package ru.clevertec

import org.gradle.api.Plugin
import org.gradle.api.Project
import ru.clevertec.util.GitChecker
import ru.clevertec.util.GitListener
import ru.clevertec.util.TagParser

private const val MASTER = "master"
private const val STAGE = "stage/"
private const val DEV = "dev/"
private const val QA = "qa/"

class GitTagVersionCreatorPlugin : Plugin<Project> {

    override fun apply(project: Project) {

        project.tasks.register("createTag") { task ->
            task.group = "git tag version creator"
            task.doFirst() {
                if (GitChecker().hasTagOnCommit()) {
                    println("not commited")
                    println("commit has tag")
                } else {
                    val gitListener = GitListener()
                    gitListener.runGitFetch()
                    val majorNumberOfGreatestVersion = TagParser().getMajorNumberOfGreatestVersion()
                    val minorNumberOfGreatestVersion = TagParser().getMinorNumberOfGreatestVersion()
                    val branch = gitListener.getGitCurrentBranch()
                    val tag: String
                    when {
                        branch.contains(MASTER) -> {
                            tag = String.format(
                                "v%d.0",
                                (majorNumberOfGreatestVersion + 1)
                            )
                        }

                        branch.contains(STAGE) -> {
                            tag = String.format(
                                "v%d.%d-rc",
                                majorNumberOfGreatestVersion,
                                (minorNumberOfGreatestVersion + 1)
                            )
                        }

                        branch.contains(DEV) || branch.contains(QA) -> {
                            tag = String.format(
                                "v%d.%d",
                                majorNumberOfGreatestVersion,
                                (minorNumberOfGreatestVersion + 1)
                            )
                        }

                        else -> tag = "v$majorNumberOfGreatestVersion.$minorNumberOfGreatestVersion-SNAPSHOT"

                    }

                    if (GitChecker().hasModifiedFiles() && GitChecker().hasUntrackedFiles()) {
                        println("$tag.uncommitted")
                    } else {
                        println(tag)
                        gitListener.createGitTag(tag)
                        gitListener.runGitPush()
                    }
                }
            }
        }
    }
}
