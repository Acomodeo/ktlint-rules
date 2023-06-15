package de.mhlz.ktlintrules

import com.pinterest.ktlint.test.KtLintAssertThat
import org.junit.Test

class NoEmptyLinesInSingleExpressionFunctionsRuleTest {

    private val wrappingRuleAssertThat =
        KtLintAssertThat.assertThatRule { NoEmptyLinesInSingleExpressionFunctionsRule() }

    @Test
    fun `should report on function expressions`() {
        val code = """
        fun test() =
        
        
        
        
            "test"
        """.trimIndent()
        wrappingRuleAssertThat(code).hasLintViolation(2, 1, NO_EMPTY_LINE_IN_SINGLE_EXPRESSION_FUNCTION)
    }

    @Test
    fun `should report on function expressions with parameters`() {
        val code = """
        fun test(param: String) =
        
        
        
        
            "test"
        """.trimIndent()
        wrappingRuleAssertThat(code).hasLintViolation(2, 1, NO_EMPTY_LINE_IN_SINGLE_EXPRESSION_FUNCTION)
    }

    @Test
    fun `should not report`() {
        val code = """
        fun test() =
            "test"
                    
        fun test2() = {
            
            "test2"
        }
        
        val a = 
            
            4
        """.trimIndent()
        wrappingRuleAssertThat(code).hasNoLintViolations()
    }
}
