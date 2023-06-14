package de.mhlz.ktlintrules

import com.pinterest.ktlint.test.KtLintAssertThat
import com.pinterest.ktlint.test.LintViolation
import org.junit.Test

/**
 * @author Mischa Holz
 */
class NoNullAssertionsRuleTest {
    private val wrappingRuleAssertThat = KtLintAssertThat.assertThatRule { NoNullAssertionsRule() }

    @Test
    fun `should report null assertions`() {
        val test = """
val test = t!!
val otherTest = t!!.bla
val shouldNotTrigger = "t!!"
"""

        wrappingRuleAssertThat(test).hasLintViolationsWithoutAutoCorrect(
            LintViolation(2, 13, NO_NULL_ASSERTION_ERROR_MESSAGE),
            LintViolation(3, 18, NO_NULL_ASSERTION_ERROR_MESSAGE),
        )
    }
}
