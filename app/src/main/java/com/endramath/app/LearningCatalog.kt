package com.endramath.app

enum class Companion(val label: String) { NONE("No dragon"), SOLARIS("Solaris"), AURORA("Aurora") }
data class Learner(val id: String, val name: String, val age: Int, val grade: String, val companion: Companion = Companion.NONE)
enum class LearningStage { LESSON, GUIDED_PRACTICE, INDEPENDENT_PRACTICE, QUIZ, REVIEW }
data class Academy(val id: String, val title: String, val summary: String, val inDevelopment: Boolean)
data class Skill(val id: String, val academyId: String, val title: String, val prerequisiteIds: List<String> = emptyList())
data class MasteryRecord(val learnerId: String, val skillId: String, val verified: Boolean, val updatedAt: Long)
object LearningCatalog {
    val academies = listOf(
        Academy("early-number", "Early Number Academy", "Counting, quantities, and number relationships", true),
        Academy("arithmetic", "Arithmetic Academy", "Addition, subtraction, multiplication, and division", true),
        Academy("fractions", "Fractions & Measurement", "Future content", false),
        Academy("pre-algebra", "Pre-Algebra", "Future content", false),
        Academy("algebra", "Algebra", "Future content", false),
        Academy("geometry", "Geometry", "Future content", false),
        Academy("trigonometry", "Trigonometry", "Future content", false),
        Academy("data", "Data & Statistics", "Future content", false),
        Academy("probability", "Probability", "Future content", false),
        Academy("precalculus", "Precalculus", "Future content", false),
        Academy("calculus", "Calculus", "Future content", false),
        Academy("college", "College Extensions", "Future content", false)
    )
}
object LearnerValidation {
    fun error(name: String, age: Int?, grade: String): String? = when {
        name.trim().isEmpty() -> "Please enter a learner name."
        name.trim().length > 40 -> "Please use a name of 40 characters or fewer."
        age == null || age !in 1..120 -> "Please enter an age from 1 to 120."
        grade !in grades -> "Please select a school grade or Not in school."
        else -> null
    }
    val grades = listOf("Not specified", "Pre-K", "Kindergarten") + (1..12).map { "Grade $it" } + listOf("College", "Not in school")
}
