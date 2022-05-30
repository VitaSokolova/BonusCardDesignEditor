package com.example.bonuscarddesigneditor.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun LongPressDraggable(
    draggingItemState: DragTargetInfo,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
        Box(
            modifier = modifier
                .fillMaxSize()
        ) {
            content()
            if (draggingItemState.isDragging) {
                var targetSize by remember {
                    mutableStateOf(IntSize.Zero)
                }
                Box(modifier = Modifier
                    .graphicsLayer {
                        val offset = draggingItemState.getNewPosition()
                        scaleX = 1.3f
                        scaleY = 1.3f
                        alpha = if (targetSize == IntSize.Zero) 0f else .9f
                        translationX = offset.x
                        translationY = offset.y
                    }
                    .onGloballyPositioned {
                        targetSize = it.size
                    }
                ) {
                    draggingItemState.draggableComposable?.invoke()
                }
            }
        }
    }