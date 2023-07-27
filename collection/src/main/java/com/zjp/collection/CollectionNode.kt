package com.zjp.collection

import androidx.compose.runtime.Composable
import com.zjp.collection.collection.*
import com.zjp.collection.collection.animation.AnimatedVisibilityDemo
import com.zjp.collection.collection.animation.ExitAndInAnimation
import com.zjp.collection.collection.animation.animateAsState
import com.zjp.collection.collection.animation.customAnimation
import com.zjp.collection.collection.animation.startAnimationImmediately

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
        20 -> PathDashPathEffectSample()
        21 -> AnimatedVisibilityDemo()
        22 -> startAnimationImmediately()
        23 -> ExitAndInAnimation()
        24 -> customAnimation()
        25 -> animateAsState()
    }
}