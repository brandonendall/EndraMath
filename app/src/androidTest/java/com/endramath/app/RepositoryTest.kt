package com.endramath.app
import android.test.InstrumentationTestCase

@Suppress("DEPRECATION")
class RepositoryTest : InstrumentationTestCase() {
    fun testProfilesAndPreferencesSurviveNewRepository() {
        val context = instrumentation.targetContext
        context.getSharedPreferences("endramath_local_v1", 0).edit().clear().commit()
        val first = LearnerRepository(context)
        assertEquals(0, first.learners().size)
        assertTrue(first.save(Learner("first", "Test learner", 8, "Grade 2")))
        assertTrue(first.save(Learner("second", "Another learner", 40, "Not in school", Companion.AURORA)))
        assertTrue(first.select("first"))
        first.largeText = true
        val reopened = LearnerRepository(context)
        assertEquals(2, reopened.learners().size)
        assertEquals("first", reopened.selected()!!.id)
        assertTrue(reopened.largeText)
        assertTrue(reopened.save(reopened.selected()!!.copy(name = "Edited learner", age = 9, grade = "Grade 3")))
        val final = LearnerRepository(context)
        assertEquals("Edited learner", final.selected()!!.name)
        assertEquals(Companion.AURORA, final.learners().first { it.id == "second" }.companion)
        assertEquals(Companion.NONE, final.selected()!!.companion)
        context.getSharedPreferences("endramath_local_v1", 0).edit().clear().commit()
    }
}
