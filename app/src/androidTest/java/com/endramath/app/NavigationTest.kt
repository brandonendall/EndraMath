package com.endramath.app

import android.content.Intent
import android.test.InstrumentationTestCase
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ScrollView
import android.widget.TextView

@Suppress("DEPRECATION")
class NavigationTest : InstrumentationTestCase() {
    fun testHomeNavigationSwitchingAndOptionalCompanions() {
        val context = instrumentation.targetContext
        context.getSharedPreferences("endramath_local_v1", 0).edit().clear().commit()
        val repo = LearnerRepository(context)
        assertTrue(repo.save(Learner("one", "One", 8, "Grade 2")))
        assertTrue(repo.save(Learner("two", "Two", 35, "Not in school")))
        assertTrue(repo.select("one"))
        val activity = instrumentation.startActivitySync(Intent(context, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        instrumentation.waitForIdleSync()
        fun views(view: View): List<View> = listOf(view) + if(view is ViewGroup) (0 until view.childCount).flatMap { views(view.getChildAt(it)) } else emptyList()
        fun click(label: String) {
            instrumentation.runOnMainSync {
                val button = views(activity.window.decorView).filterIsInstance<Button>().firstOrNull { it.text.toString() == label }
                assertNotNull("Missing button: $label", button)
                assertTrue(button!!.performClick())
            }
            instrumentation.waitForIdleSync()
        }
        fun shows(label: String) {
            instrumentation.runOnMainSync { assertTrue("Missing text: $label", views(activity.window.decorView).filterIsInstance<TextView>().any { it.text.toString().contains(label) }) }
        }
        instrumentation.runOnMainSync {
            val scroll = views(activity.window.decorView).filterIsInstance<ScrollView>().first()
            assertTrue("Home requires scrolling on portrait test viewport", scroll.getChildAt(0).height <= scroll.height)
        }
        click("How EndraMath Works"); shows("Created and directed by Kat"); click("Home")
        click("Settings"); click("Aurora")
        assertEquals(Companion.AURORA, LearnerRepository(context).selected()!!.companion)
        click("No dragon"); click("Home")
        click("Continue learning • One"); click("Early Number Academy"); shows("being prepared")
        click("Explore other academies"); click("Arithmetic Academy"); shows("Arithmetic Academy"); click("Home")
        click("Welcome, One! • Choose or edit learners"); click("Select Two")
        assertEquals("two", LearnerRepository(context).selected()!!.id)
        shows("Continue learning • Two")
        instrumentation.runOnMainSync { activity.finish() }
        context.getSharedPreferences("endramath_local_v1", 0).edit().clear().commit()
    }
}
