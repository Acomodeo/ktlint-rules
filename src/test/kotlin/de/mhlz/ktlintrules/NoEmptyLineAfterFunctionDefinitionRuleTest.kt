package de.mhlz.ktlintrules

import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import org.junit.Test

class NoEmptyLineAfterFunctionDefinitionRuleTest {

    private val wrappingRuleAssertThat = assertThatRule { NoEmptyLineAfterFunctionDefinitionRule() }

    @Test
    fun `should report empty lines after function definitions`() {
        val test = """
fun test() {

    println("out")
}
"""
        wrappingRuleAssertThat(test).hasLintViolation(3, 1, NO_EMPTY_LINE_AFTER_FUN_DEFINITION_ERROR_MESSAGE)
    }

    @Test
    fun `should not report spaces after function definitions`() {
        val test = """
fun test() {
    println("out")
}
"""
        wrappingRuleAssertThat(test).hasNoLintViolations()
    }

    @Test
    fun `should not report on function expressions`() {
        val test = """
fun test() = println("test")
"""
        wrappingRuleAssertThat(test).hasNoLintViolations()
    }

    @Test
    fun `should not report on function expressions2`() {
        val test = """
fun test() =
    println("test")
"""
        wrappingRuleAssertThat(test).hasNoLintViolations()
    }

    @Test
    fun `should report on function expressions`() {
        val test = """
fun test() =




    "test"
"""
        wrappingRuleAssertThat(test).hasLintViolation(3, 1, NO_EMPTY_LINE_AFTER_FUN_DEFINITION_ERROR_MESSAGE)
    }

    @Test
    fun `should report on function expressions with lambdas`() {
        val test = """
fun test2() = ::println

fun test() = { a, b ->




    "test"
}
"""
        wrappingRuleAssertThat(test)
            .hasLintViolation(4, 1, NO_EMPTY_LINE_AFTER_FUN_DEFINITION_ERROR_MESSAGE)
    }

    @Test
    fun `should report on function expressions with lambdas2`() {
        val test = """
fun test2() = ::println

fun test() = {




    "test"
}
"""
        wrappingRuleAssertThat(test).hasLintViolation(5, 1, NO_EMPTY_LINE_AFTER_FUN_DEFINITION_ERROR_MESSAGE)
    }

    @Test
    fun `should not report on lambdas without linebreaks`() {
        val test = """
fun test() {
    doSomething("abc") { "test" }
}
"""
        wrappingRuleAssertThat(test).hasNoLintViolations()
    }

    @Test
    fun `should report on lambdas`() {
        val test = """
fun test() {
    doSomething("abc") {


        "test"
    }
}
"""
        wrappingRuleAssertThat(test).hasLintViolation(4, 1, NO_EMPTY_LINE_AFTER_FUN_DEFINITION_ERROR_MESSAGE)
    }

    @Test
    fun `should report on lambdas2`() {
        val test = """
fun test() {
    doSomething("abc") { a, b ->


        "test"
    }
}
"""
        wrappingRuleAssertThat(test)
            .hasLintViolation(3, 1, NO_EMPTY_LINE_AFTER_FUN_DEFINITION_ERROR_MESSAGE)
    }

    @Test
    fun `should fix empty lines after function definitions`() {
        val test = """
fun test() {





    println("out")
}
"""
        val afterFormatting = """
fun test() {
    println("out")
}
"""

        wrappingRuleAssertThat(test)
            .withEditorConfigOverride()
            .hasLintViolation(3, 1, NO_EMPTY_LINE_AFTER_FUN_DEFINITION_ERROR_MESSAGE)
            .isFormattedAs(afterFormatting)
    }
}
