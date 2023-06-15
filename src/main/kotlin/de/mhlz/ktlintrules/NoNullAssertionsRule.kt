package de.mhlz.ktlintrules

import com.pinterest.ktlint.rule.engine.core.api.Rule
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement
import org.jetbrains.kotlin.lexer.KtSingleValueToken

const val NO_NULL_ASSERTION_ERROR_MESSAGE =
    "Do not use '!!' assertions as they produce unhelpful error messages. Try to use ?.let or ?. instead"

/**
 * @author Mischa Holz
 */
class NoNullAssertionsRule : Rule(
    ruleId = RuleId("mhlz:no-null-assertion"),
    about = About(),
) {

    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        if (node is LeafPsiElement) {
            val nodeValue = (node.elementType as? KtSingleValueToken)?.value

            if (nodeValue == "!!") {
                emit(node.startOffset, NO_NULL_ASSERTION_ERROR_MESSAGE, false)
            }
        }
    }
}
