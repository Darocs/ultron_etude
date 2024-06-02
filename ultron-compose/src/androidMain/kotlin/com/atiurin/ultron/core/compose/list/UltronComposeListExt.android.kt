package com.atiurin.ultron.core.compose.list

import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.performScrollToNode
import com.atiurin.ultron.core.compose.nodeinteraction.UltronComposeSemanticsNodeInteraction
import com.atiurin.ultron.core.compose.operation.ComposeOperationType
import com.atiurin.ultron.core.compose.operation.UltronComposeOperationParams
import com.atiurin.ultron.extensions.findNodeInTree

fun UltronComposeList.onItemChild(itemMatcher: SemanticsMatcher, childMatcher: SemanticsMatcher): UltronComposeSemanticsNodeInteraction =
    UltronComposeSemanticsNodeInteraction(
        UltronComposeSemanticsNodeInteraction(listMatcher, true)
            .execute(
                UltronComposeOperationParams(
                    operationName = "Get item '${itemMatcher.description}' child '${childMatcher.description}' in list '${getInteraction().elementInfo.name}'",
                    operationDescription = "Get Compose list item '${itemMatcher.description}' child '${childMatcher.description}' in list '${getInteraction().elementInfo.name}'",
                    operationType = ComposeOperationType.GET_LIST_ITEM_CHILD
                )
            ) { listInteraction ->
                listInteraction.performScrollToNode(itemMatcher)
                    .onChildren().filterToOne(itemMatcher)
                    .findNodeInTree(childMatcher, useUnmergedTree)
            }
    )

fun UltronComposeList.onVisibleItemChild(index: Int, childMatcher: SemanticsMatcher) = UltronComposeSemanticsNodeInteraction(
    this.getInteraction().execute(
        UltronComposeOperationParams(
            operationName = "Get child '${childMatcher.description}' of visible item at index $index in list '${getInteraction().elementInfo.name}'",
            operationDescription = "Get Compose list child '${childMatcher.description}' of visible item at index $index in list '${getInteraction().elementInfo.name}'",
            operationType = ComposeOperationType.GET_LIST_ITEM_CHILD
        )
    ) { listInteraction ->
        listInteraction.onChildAt(index).findNodeInTree(childMatcher, useUnmergedTree)
    }
)

