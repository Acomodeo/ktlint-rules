package de.mhlz.ktlintrules

import com.pinterest.ktlint.rule.engine.core.api.ElementType
import com.pinterest.ktlint.rule.engine.core.api.Rule
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import com.pinterest.ktlint.rule.engine.core.api.isPartOf
import com.pinterest.ktlint.rule.engine.core.api.prevLeaf
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement

const val NO_EMPTY_LINE_IN_LAMBDA_BODY_ERROR_MESSAGE = "First line in a lambda body should not be empty"

class NoEmptyLineInLambdaBody : Rule(
    ruleId = RuleId("mhlz:no-empty-lines-in-lambda-body"),
    about = About(),
) {

    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        if (node is PsiWhiteSpace && node.textContains('\n') &&
            node.prevLeaf()?.elementType == ElementType.ARROW && (
                node.isPartOf(ElementType.LAMBDA_ARGUMENT) || node.isPartOf(ElementType.LAMBDA_EXPRESSION)
                )
        ) {
            val split = node.getText().split("\n")
            if (split.size > 2) {
                emit(
                    node.startOffset + 1,
                    NO_EMPTY_LINE_IN_LAMBDA_BODY_ERROR_MESSAGE,
                    true,
                )
                if (autoCorrect) {
                    (node as LeafPsiElement).rawReplaceWithText("${split.first()}\n${split.last()}")
                }
            }
        }
    }
}
