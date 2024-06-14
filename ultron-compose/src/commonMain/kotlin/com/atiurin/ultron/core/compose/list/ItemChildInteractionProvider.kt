package com.atiurin.ultron.core.compose.list

import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasAnyAncestor
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.performScrollToNode
import com.atiurin.ultron.core.compose.SemanticsNodeInteractionProviderContainer

interface ItemChildInteractionProvider {
    fun onItemChild(
        listMatcher: SemanticsMatcher,
        itemMatcher: SemanticsMatcher,
        childMatcher: SemanticsMatcher,
        useUnmergedTree: Boolean
    ): () -> SemanticsNodeInteraction = {
        SemanticsNodeInteractionProviderContainer.withSemanticsProvider {
            it.onNode(listMatcher).performScrollToNode(itemMatcher)
            it.onNode(
                hasAnyAncestor(listMatcher)
                    .and(hasAnyAncestor(itemMatcher))
                    .and(childMatcher),
                useUnmergedTree
            )
        }
    }

    fun onVisibleItemChild(listMatcher: SemanticsMatcher, index: Int, childMatcher: SemanticsMatcher, useUnmergedTree: Boolean): () -> SemanticsNodeInteraction = {
        SemanticsNodeInteractionProviderContainer.withSemanticsProvider {
            it.onNode(listMatcher, useUnmergedTree)
                .onChildAt(index)
                .onChildren()
                .filterToOne(childMatcher)
        }
    }
}

expect fun getItemChildInteractionProvider(): ItemChildInteractionProvider