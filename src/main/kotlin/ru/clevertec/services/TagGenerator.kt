package ru.clevertec.services

private const val MASTER = "master"
private const val STAGE = "stage/"
private const val DEV = "dev/"
private const val QA = "qa/"

class TagGenerator {
    fun generateTag(branch: String, majorNumberOfGreatestVersion: Int, minorNumberOfGreatestVersion: Int): String {
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
        return tag
    }
}