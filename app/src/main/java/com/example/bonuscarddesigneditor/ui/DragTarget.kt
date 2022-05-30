package com.example.bonuscarddesigneditor.ui

import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun <T> DragTarget(
    state: DragTargetInfo,
    modifier: Modifier,
    dataToDrop: T,
    content: @Composable (() -> Unit),
    onDragEnd: (dragTargetInfo: DragTargetInfo) -> Unit
) {
    var currentPosition by remember { mutableStateOf(Offset.Zero) }

    Box(modifier = modifier
        .onGloballyPositioned {
            currentPosition = it.localToRoot(Offset.Zero)
        }
        .pointerInput(Unit) {
            // detect DragGestures After LongPress
            detectDragGesturesAfterLongPress(
                onDragStart = {
                    state.dataToDrop = dataToDrop
                    state.isDragging = true
                    state.initialOffset = it
                    state.dragPosition = currentPosition + it
                    state.draggableComposable = content
                },
                onDrag = { change, dragAmount ->
                    change.consumeAllChanges()
                    state.dragOffset += Offset(dragAmount.x, dragAmount.y)
                },
                onDragEnd = {
                    onDragEnd(state)
                    state.isDragging = false
                    state.dragOffset = Offset.Zero
                },
                onDragCancel = {
                    state.dragOffset = Offset.Zero
                    state.isDragging = false
                })
        }) {
        content()
    }
}