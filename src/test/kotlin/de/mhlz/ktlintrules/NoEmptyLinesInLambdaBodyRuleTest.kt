package de.mhlz.ktlintrules

import com.pinterest.ktlint.test.KtLintAssertThat
import org.junit.Test

class NoEmptyLinesInLambdaBodyRuleTest {

    private val wrappingRuleAssertThat = KtLintAssertThat.assertThatRule { NoEmptyLineInLambdaBody() }

    @Test
    fun `should report when lambda body contains leading empty lines`() {
        val code = """
        fun test2() = ::println
        
        fun test() = { a, b ->
        
        
        
        
            "test"
        }
        """.trimIndent()
        val formattedCode = """
        fun test2() = ::println
        
        fun test() = { a, b ->
            "test"
        }
        """.trimIndent()
        wrappingRuleAssertThat(code)
            .hasLintViolation(4, 1, NO_EMPTY_LINE_IN_LAMBDA_BODY_ERROR_MESSAGE)
            .isFormattedAs(formattedCode)
    }

    @Test
    fun `should not report when lambda body contains not leading empty lines`() {
        val code = """
        fun test2() = ::println
        
        fun test() = { a, b ->
            "test"
            
            
        }
        """.trimIndent()
        wrappingRuleAssertThat(code)
            .hasNoLintViolations()
    }

    @Test
    fun `should report when inner lambda body contains leading empty lines`() {
        val code = """
        fun test() {
            doSomething("abc") { a, b ->
        
        
                "test"
            }
        }
        """.trimIndent()
        val formattedCode = """
        fun test() {
            doSomething("abc") { a, b ->
                "test"
            }
        }
        """.trimIndent()
        wrappingRuleAssertThat(code)
            .hasLintViolation(3, 1, NO_EMPTY_LINE_IN_LAMBDA_BODY_ERROR_MESSAGE)
            .isFormattedAs(formattedCode)
    }
}
