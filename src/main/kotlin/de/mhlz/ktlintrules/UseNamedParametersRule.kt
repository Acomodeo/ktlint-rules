package de.mhlz.ktlintrules

import com.pinterest.ktlint.rule.engine.core.api.Rule
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import org.jetbrains.kotlin.KtNodeTypes
import org.jetbrains.kotlin.KtNodeTypes.CALL_EXPRESSION
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/**
 * @author Mischa Holz
 */

val ignoredFunctions = listOf(
    "listOf",
    "mapOf",
    "mutableListOf",
    "mutableMapOf",
    "byteArrayOf",
    "setOf",
    "mutableSetOf",
    "arrayOf",
    "listOfNotNull"
)

const val USE_NAMED_PARAMETERS_RULE_ERROR_MESSAGE =
    "Should use named parameters for function calls with more than 4 arguments: "

class UseNamedParametersRule : Rule(
    ruleId = RuleId("mhlz:use-named-parameters"),
    about = About(),
) {
    override fun afterVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        if (node is CompositeElement && node.elementType == CALL_EXPRESSION) {
            val callText = node
                .getChildren(TokenSet.create(KtNodeTypes.REFERENCE_EXPRESSION))
                .firstOrNull()
                ?.text
            if (callText in ignoredFunctions) {
                return
            }

            val argumentList = node.getChildren(TokenSet.create(KtNodeTypes.VALUE_ARGUMENT_LIST))
            val arguments = argumentList
                .singleOrNull()
                ?.getChildren(TokenSet.create(KtNodeTypes.VALUE_ARGUMENT))
                ?: return

            if (arguments.size > 4) {
                val error = arguments.any {
                    it.getChildren(TokenSet.create(KtNodeTypes.VALUE_ARGUMENT_NAME)).isEmpty()
                }

                if (error) {
                    emit(
                        node.startOffset,
                        USE_NAMED_PARAMETERS_RULE_ERROR_MESSAGE + node.text,
                        false
                    )
                }
            }
        }
    }
}
