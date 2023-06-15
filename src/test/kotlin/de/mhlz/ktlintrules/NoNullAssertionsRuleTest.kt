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
        val code = """
        val test = t!!
        val otherTest = t!!.bla
        val shouldNotTrigger = "t!!"
        """.trimIndent()

        wrappingRuleAssertThat(code).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 13, NO_NULL_ASSERTION_ERROR_MESSAGE),
            LintViolation(2, 18, NO_NULL_ASSERTION_ERROR_MESSAGE),
        )
    }
}
