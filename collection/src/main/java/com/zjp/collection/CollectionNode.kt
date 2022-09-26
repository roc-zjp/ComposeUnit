package com.zjp.collection

import androidx.compose.runtime.Composable
import com.zjp.collection.collection.*

@Composable
fun CollectionNodeMap(id: Int) {
    when (id) {
        1 -> ClickableSample()
        2 -> LaunchedEffectBase()
        3 -> CoroutineScopeBase()
        4 -> RememberUpdatedStateBase()
        5 -> DisposableEffectBase()
        6 -> SideEffectBase()
        7 -> ProduceStateBase()
        8 -> DerivedStateOfBase()
        9 -> SnapshotFlowBase()
        10 -> ScrollBoxes()
        11 -> ScrollableSample()
        12 -> AutoNestScroll()
        13 -> NestedScrollBase()
        14 -> NestedScrollDispatcherBase()
        15 -> DragPointInput()
        16 -> DragAble()
        17 -> Swipeable()
        18 -> TransformableSample()
        19 -> RemoteControl()
    }
}