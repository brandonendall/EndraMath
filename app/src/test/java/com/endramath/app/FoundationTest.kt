package com.endramath.app
import org.junit.Assert.*
import org.junit.Test
class FoundationTest {
    @Test fun namesAndPrivateInputsAreValidated() {
        assertNotNull(LearnerValidation.error(" ", 8, "Grade 2"))
        assertNotNull(LearnerValidation.error("Learner", null, "Grade 2"))
        assertNotNull(LearnerValidation.error("Learner", 0, "Grade 2"))
        assertNull(LearnerValidation.error("Adult learner", 65, "Not in school"))
        assertNull(LearnerValidation.error("Learner", 8, "Grade 2"))
    }
    @Test fun academyFoundationHasNoGradeGates() {
        assertEquals(12, LearningCatalog.academies.size)
        assertEquals(2, LearningCatalog.academies.count { it.inDevelopment })
        assertEquals(12, LearningCatalog.academies.map { it.id }.toSet().size)
        assertEquals(Companion.NONE, Learner("a", "Learner", 12, "Grade 6").companion)
    }
}
