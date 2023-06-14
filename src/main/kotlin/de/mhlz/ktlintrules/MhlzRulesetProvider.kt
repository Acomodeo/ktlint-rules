package de.mhlz.ktlintrules

import com.pinterest.ktlint.cli.ruleset.core.api.RuleSetProviderV3
import com.pinterest.ktlint.rule.engine.core.api.RuleProvider
import com.pinterest.ktlint.rule.engine.core.api.RuleSetId

/**
 * @author Mischa Holz
 */
class MhlzRulesetProvider : RuleSetProviderV3(
    RuleSetId("mhlz"),
) {
    override fun getRuleProviders(): Set<RuleProvider> = setOf(
        RuleProvider { UseNamedParametersRule() },
        RuleProvider { NoNullAssertionsRule() },
        RuleProvider { NoEmptyLineAfterFunctionDefinitionRule() },
    )
}
