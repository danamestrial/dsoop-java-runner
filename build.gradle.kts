import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import groovy.time.TimeCategory
import java.util.Date


plugins {
    kotlin("jvm") version "1.8.21"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
//    maxParallelForks = 5
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "16"
//    kotlinOptions.suppressWarnings = true
//    kotlinOptions.verbose = false
}

application {
    mainClass.set("MainKt")
}

dependencies {
    // Other dependencies.
    implementation(kotlin("reflect"))
    testImplementation(kotlin("test"))
//    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
}

/**
 * based on the groovy code by lwasyl:
 * https://gist.github.com/lwasyl/f5b2b4ebe9e348ebbd8ee4cb995f8362
 */
var testResults by extra(mutableListOf<TestOutcome>()) // Container for tests summaries

tasks.withType<Test>().configureEach {
    val testTask = this

    testLogging {
        events = setOf(
            TestLogEvent.PASSED,
            TestLogEvent.FAILED,
            TestLogEvent.SKIPPED,
            TestLogEvent.STANDARD_OUT,
            TestLogEvent.STANDARD_ERROR
        )

        exceptionFormat = TestExceptionFormat.FULL
        showExceptions = true
        showCauses = true
        showStackTraces = false
    }

    ignoreFailures = true // Always try to run all tests for all modules

    //addTestListener is a workaround https://github.com/gradle/kotlin-dsl-samples/issues/836
    addTestListener(object : TestListener {
        override fun beforeSuite(suite: TestDescriptor) {}
        override fun beforeTest(testDescriptor: TestDescriptor) {}
        override fun afterTest(testDescriptor: TestDescriptor, result: TestResult) {}
        override fun afterSuite(desc: TestDescriptor, result: TestResult) {
            if (desc.parent != null) return // Only summarize results for whole modules

            val summary = TestOutcome().apply {
                add( "${testTask.project.name}:${testTask.name} results: ${result.resultType} " +
                        "(" +
                        "${result.testCount} tests, " +
                        "${result.successfulTestCount} successes, " +
                        "${result.failedTestCount} failures, " +
                        "${result.skippedTestCount} skipped" +
                        ") " +
                        "in ${TimeCategory.minus(Date(result.endTime), Date(result.startTime))}")
//                add("Report file: ${testTask.reports.html.entryPoint}")
//                add("Test Fail: ${testTask.reports}")
            }

            // Add reports in `testsResults`, keep failed suites at the end
            if (result.resultType == TestResult.ResultType.SUCCESS) {
                testResults.add(0, summary)
            } else {
                testResults.add(summary)
            }
        }
    })
}

gradle.buildFinished {
    if (testResults.isNotEmpty()) {
        printResults(testResults)
    }
}

fun printResults(allResults:List<TestOutcome>) {
    val maxLength = allResults.map{ it.maxWidth() }
        .max() ?: 0

    println("┌${"─".repeat(maxLength)}┐")

    println(allResults.joinToString("├${"─".repeat(maxLength)}┤\n") { testOutcome ->
        testOutcome.lines.joinToString("│\n│", "│", "│") {
            it + " ".repeat(maxLength - it.length)
        }
    })

    println("└${"─".repeat(maxLength)}┘")
}

data class TestOutcome(val lines:MutableList<String> = mutableListOf()) {
    fun add(line:String) {
        lines.add(line)
    }

    fun maxWidth(): Int {
        return lines.maxBy { it.length }?.length ?: 0
    }
}