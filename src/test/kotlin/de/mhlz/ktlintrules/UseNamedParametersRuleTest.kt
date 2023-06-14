package de.mhlz.ktlintrules

import com.pinterest.ktlint.test.KtLintAssertThat
import org.junit.Test

/**
 * @author Mischa Holz
 */
class UseNamedParametersRuleTest {

    private val wrappingRuleAssertThat = KtLintAssertThat.assertThatRule { UseNamedParametersRule() }

    @Test
    fun testNamedParametersRule() {
        val code = """
fun test(a1: String, a2: String, a3: String, a4: String, a5: String) {}

fun otherTest() {
    test("", "", "", "", "")
    System.out.println("")
}

fun anotherTest() {
    test(a1 = "", a2 = "", a3 = "", a4 = "", a5 = "")
}
        """

        wrappingRuleAssertThat(code).hasLintViolationWithoutAutoCorrect(
            5,
            5,
            USE_NAMED_PARAMETERS_RULE_ERROR_MESSAGE + "test(\"\", \"\", \"\", \"\", \"\")"
        )
    }
}
