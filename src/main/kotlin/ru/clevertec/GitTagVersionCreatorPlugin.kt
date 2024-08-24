package ru.clevertec

import org.gradle.api.Plugin
import org.gradle.api.Project
import ru.clevertec.extensions.TagExtension
import ru.clevertec.tasks.CheckGitInstalled
import ru.clevertec.tasks.CreateTag
import ru.clevertec.tasks.HasTagOnCommit
import ru.clevertec.tasks.ShowIntendedTag


private const val GIT_TAG_VERSION_CREATOR = "git tag version creator"

class GitTagVersionCreatorPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.tasks.register("checkGitInstalled", CheckGitInstalled::class.java) { task ->
            project.extensions.create("exten", TagExtension::class.java)
            task.group = GIT_TAG_VERSION_CREATOR
        }
        project.tasks.register("checkTagOnCommit", HasTagOnCommit::class.java) { task ->
            task.dependsOn(":checkGitInstalled")
            task.group = GIT_TAG_VERSION_CREATOR
        }
        project.tasks.register("showIntendedTag", ShowIntendedTag::class.java) { task ->
            task.dependsOn(":checkTagOnCommit")
            task.group = GIT_TAG_VERSION_CREATOR
        }
        project.tasks.register("createTag", CreateTag::class.java) { task ->
            task.dependsOn(":showIntendedTag")
            task.group = GIT_TAG_VERSION_CREATOR
        }
    }
}

