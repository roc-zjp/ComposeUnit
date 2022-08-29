package com.zjp.compose_unit.common.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.zjp.compose_unit.route.HomeSections
import kotlin.math.sqrt

@Composable
fun UnitBottomAppBar(
    modifier: Modifier = Modifier,
    onTabChange: (HomeSections) -> Unit = {},
    onSearchClick: () -> Unit = {}
) {
    Box(modifier = modifier) {
        BottomAppBar(
            cutoutShape = CircleShape,
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier
                .height(56.dp)
                .clip(BottomAppBarCutShape()),
        ) {
            BottomAppBarLeft(onTabChange)
            Spacer(modifier = Modifier.weight(1f))
            BottomAppBarRight(onTabChange)
        }
        FloatingActionButton(
            onClick = { onSearchClick() },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-AppBarHeight / 2))
        ) {
            Icon(
                painter = painterResource(id = com.zjp.common.R.drawable.search),
                contentDescription = "search"
            )
        }
    }
}

@Composable
fun BottomAppBarLeft(onTabChange: (HomeSections) -> Unit) {
    Box(
        modifier = Modifier
            .clip(shapeTe)
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(
                    top = 3.dp, end = 3.dp
                )
                .clip(RoundedCornerShape(topEnd = 7.dp))
                .background(color = Color(0xff4699FB))
                .fillMaxHeight(),

            ) {
            TableRowItem(
                homeSection = HomeSections.COMPOSE,
                currentRoute = "",
                modifier = Modifier.width(itemWidth),
                onTabChange = onTabChange
            )
            TableRowItem(
                homeSection = HomeSections.COLLECTION,
                currentRoute = "",
                modifier = Modifier.width(itemWidth),
                onTabChange = onTabChange
            )
        }
    }
}


@Composable
fun BottomAppBarRight(onTabChange: (HomeSections) -> Unit) {
    Box(
        modifier = Modifier
            .clip(shapeTs)
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(top = 3.dp, start = 3.dp)
                .clip(RoundedCornerShape(topStart = 7.dp))
                .background(color = Color(0xff4699FB))
                .fillMaxHeight(),
        ) {
            TableRowItem(
                homeSection = HomeSections.PROFILE,
                currentRoute = "",
                modifier = Modifier.width(itemWidth),
                onTabChange = onTabChange
            )
            TableRowItem(
                homeSection = HomeSections.PROFILE,
                currentRoute = "",
                modifier = Modifier.width(itemWidth),
                onTabChange = onTabChange
            )
        }
    }

}


@Composable
fun TableRowItem(
    homeSection: HomeSections,
    currentRoute: String,
    modifier: Modifier = Modifier,
    onTabChange: (HomeSections) -> Unit
) {
    Row(
        modifier = modifier
            .padding(
                top = 10.dp, bottom = 10.dp
            )
            .clickable(
                onClick = {
                    onTabChange(homeSection)
                },
                indication = null,
                interactionSource = remember {
                    MutableInteractionSource()
                }
            )
            .padding(4.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = homeSection.icon),
            contentDescription = null
        )
        if (currentRoute == homeSection.route) {
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = homeSection.title.uppercase().substring(0..2))
        }
    }
}

val shapeTe = RoundedCornerShape(topEnd = 10.dp)
val shapeTs = RoundedCornerShape(topStart = 10.dp)
val itemWidth = 60.dp


class BottomAppBarCutShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val boundingRectangle = Path().apply {
            addRect(Rect(0f, 0f, size.width, size.height))
        }
        val path = Path().apply {
            addCutoutShape(size, layoutDirection, density)
            // Subtract this path from the bounding rectangle
            op(boundingRectangle, this, PathOperation.Difference)
        }

        return Outline.Generic(path)
    }

}

private fun Path.addCutoutShape(size: Size, layoutDirection: LayoutDirection, density: Density) {
    // The gap on all sides between the FAB and the cutout
    val cutoutOffset = with(density) { BottomAppBarCutoutOffset.toPx() }
    val fabSize = with(density) { Size(50.dp.toPx(), 50.dp.toPx()) }

    val cutoutSize = Size(
        width = fabSize.width + (cutoutOffset * 2),
        height = fabSize.height + (cutoutOffset * 2)
    )

    val cutoutStartX = size.width / 2 - cutoutSize.width / 2
    val cutoutEndX = cutoutStartX + cutoutSize.width

    val cutoutRadius = cutoutSize.height / 2f
    // Shift the cutout up by half its height, so only the bottom half of the cutout is actually
    // cut into the app bar
    val cutoutStartY = -cutoutRadius

    addOutline(CircleShape.createOutline(cutoutSize, layoutDirection, density))
    translate(Offset(cutoutStartX, cutoutStartY))
    val edgeRadius =
        with(density) { BottomAppBarRoundedEdgeRadius.toPx() }

    // TODO: consider exposing the custom cutout shape instead of just replacing circle shapes?
    addRoundedEdges(cutoutStartX, cutoutEndX, cutoutRadius, edgeRadius, 0f)

}

private fun Path.addRoundedEdges(
    cutoutStartPosition: Float,
    cutoutEndPosition: Float,
    cutoutRadius: Float,
    roundedEdgeRadius: Float,
    verticalOffset: Float
) {
    // Where the cutout intersects with the app bar, as if the cutout is not vertically aligned
    // with the app bar, the intersect will not be equal to the radius of the circle.
    val appBarInterceptOffset = calculateCutoutCircleYIntercept(cutoutRadius, verticalOffset)
    val appBarInterceptStartX = cutoutStartPosition + (cutoutRadius + appBarInterceptOffset)
    val appBarInterceptEndX = cutoutEndPosition - (cutoutRadius + appBarInterceptOffset)

    // How far the control point is away from the cutout intercept. We set this to be as small
    // as possible so that we have the most 'rounded' curve.
    val controlPointOffset = 1f

    // How far the control point is away from the center of the radius of the cutout
    val controlPointRadiusOffset = appBarInterceptOffset - controlPointOffset

    // The coordinates offset from the center of the radius of the cutout, where we should
    // draw the curve to
    val (curveInterceptXOffset, curveInterceptYOffset) = calculateRoundedEdgeIntercept(
        controlPointRadiusOffset,
        verticalOffset,
        cutoutRadius
    )

    // Convert the offset relative to the center of the cutout circle into an absolute
    // coordinate, by adding the radius of the shape to get a pure relative offset from the
    // leftmost edge, and then positioning it next to the cutout
    val curveInterceptStartX = cutoutStartPosition + (curveInterceptXOffset + cutoutRadius)
    val curveInterceptEndX = cutoutEndPosition - (curveInterceptXOffset + cutoutRadius)

    // Convert the curveInterceptYOffset which is relative to the center of the cutout, to an
    // absolute position
    val curveInterceptY = curveInterceptYOffset - verticalOffset

    // Where the rounded edge starts
    val roundedEdgeStartX = appBarInterceptStartX - roundedEdgeRadius
    val roundedEdgeEndX = appBarInterceptEndX + roundedEdgeRadius

    moveTo(roundedEdgeStartX, 0f)
    quadraticBezierTo(
        appBarInterceptStartX - controlPointOffset,
        0f,
        curveInterceptStartX,
        curveInterceptY
    )
    lineTo(curveInterceptEndX, curveInterceptY)
    quadraticBezierTo(appBarInterceptEndX + controlPointOffset, 0f, roundedEdgeEndX, 0f)
    close()
}

internal inline fun calculateCutoutCircleYIntercept(
    cutoutRadius: Float,
    verticalOffset: Float
): Float {
    return -sqrt(square(cutoutRadius) - square(verticalOffset))
}


internal fun calculateRoundedEdgeIntercept(
    controlPointX: Float,
    verticalOffset: Float,
    radius: Float
): Pair<Float, Float> {
    val a = controlPointX
    val b = verticalOffset
    val r = radius

    // expands to a2b2r2 + b4r2 - b2r4
    val discriminant = square(b) * square(r) * (square(a) + square(b) - square(r))
    val divisor = square(a) + square(b)
    // the '-b' part of the quadratic solution
    val bCoefficient = a * square(r)

    // Two solutions for the x coordinate relative to the midpoint of the circle
    val xSolutionA = (bCoefficient - sqrt(discriminant)) / divisor
    val xSolutionB = (bCoefficient + sqrt(discriminant)) / divisor

    // Get y coordinate from r2 = x2 + y2 -> y2 = r2 - x2
    val ySolutionA = sqrt(square(r) - square(xSolutionA))
    val ySolutionB = sqrt(square(r) - square(xSolutionB))

    // If the vertical offset is 0, the vertical center of the circle lines up with the top edge of
    // the bottom app bar, so both solutions are identical.
    // If the vertical offset is not 0, there are two distinct solutions: one that will meet in the
    // top half of the circle, and one that will meet in the bottom half of the circle. As the app
    // bar is always on the bottom edge of the circle, we are always interested in the bottom half
    // solution. To calculate which is which, it depends on whether the vertical offset is positive
    // or negative.
    val (xSolution, ySolution) = if (b > 0) {
        // When the offset is positive, the top edge of the app bar is below the center of the
        // circle. The largest solution will be the one closest to the bottom of the circle, so we
        // pick that.
        if (ySolutionA > ySolutionB) xSolutionA to ySolutionA else xSolutionB to ySolutionB
    } else {
        // When the offset is negative, the top edge of the app bar is above the center of the
        // circle. The smallest solution will be the one closest to the top of the circle, so we
        // pick that.
        if (ySolutionA < ySolutionB) xSolutionA to ySolutionA else xSolutionB to ySolutionB
    }

    // If the calculated x coordinate is further away from the origin than the control point, the
    // curve will fold back on itself. In this scenario, we actually join the circle above the
    // center, so invert the y coordinate.
    val adjustedYSolution = if (xSolution < controlPointX) -ySolution else ySolution
    return xSolution to adjustedYSolution
}

private inline fun square(x: Float) = x * x

// The gap on all sides between the FAB and the cutout
private val BottomAppBarCutoutOffset = 8.dp

// How far from the notch the rounded edges start
private val BottomAppBarRoundedEdgeRadius = 4.dp
private val AppBarHeight = 56.dp


@Preview
@Composable
fun BottomBarPreview() {
    UnitBottomAppBar()
}


//
//@Composable
//fun UnitBottomAppBar() {
//    ProvideWindowInsets() {
//        val navPaddingValues =
//            rememberInsetsPaddingValues(LocalWindowInsets.current.navigationBars)
//        BottomAppBar(
//            cutoutShape = CircleShape,
//            contentPadding = PaddingValues(0.dp),
//            modifier = Modifier
//                .padding(navPaddingValues)
//                .height(56.dp),
//        ) {
//            BottomAppBarLeft()
//            Spacer(modifier = Modifier.weight(1f))
//            BottomAppBarRight()
//        }
//    }
//}
//
//@Composable
//fun BottomAppBarLeft() {
//    Box(
//        modifier = Modifier
//            .clip(shapeTe)
//            .background(Color.White)
//    ) {
//        Row(
//            modifier = Modifier
//                .padding(
//                    top = 3.dp, end = 3.dp
//                )
//                .clip(RoundedCornerShape(topEnd = 7.dp))
//                .background(color = Color(0xff4699FB))
//                .fillMaxHeight(),
//
//            ) {
//            TableRowItem(
//                homeSection = HomeSections.COMPOSE,
//                currentRoute = "",
//                modifier = Modifier.width(itemWidth)
//            ) {
//
//            }
//            TableRowItem(
//                homeSection = HomeSections.COLLECTION,
//                currentRoute = "",
//                modifier = Modifier.width(itemWidth)
//            ) {
//
//            }
//        }
//    }
//}
//
//
//@Composable
//fun BottomAppBarRight() {
//    Box(
//        modifier = Modifier
//            .clip(shapeTs)
//            .background(Color.White)
//    ) {
//        Row(
//            modifier = Modifier
//                .padding(top = 3.dp, start = 3.dp)
//                .clip(RoundedCornerShape(topStart = 7.dp))
//                .background(color = Color(0xff4699FB))
//                .fillMaxHeight(),
//        ) {
//            TableRowItem(
//                homeSection = HomeSections.COMPOSE,
//                currentRoute = "",
//                modifier = Modifier.width(itemWidth)
//            ) {
//
//            }
//            TableRowItem(
//                homeSection = HomeSections.COLLECTION,
//                currentRoute = "",
//                modifier = Modifier.width(itemWidth)
//            ) {
//
//            }
//        }
//    }
//
//}
//
//@Composable
//fun TableRowItem(
//    homeSection: HomeSections,
//    currentRoute: String,
//    modifier: Modifier = Modifier,
//    onTabChange: (HomeSections) -> Unit
//) {
//    Row(
//        modifier = modifier
//            .padding(
//                top = 10.dp, bottom = 10.dp
//            )
//            .clickable(
//                onClick = {
//                    onTabChange(homeSection)
//                },
//                indication = null,
//                interactionSource = remember {
//                    MutableInteractionSource()
//                }
//            )
//            .padding(4.dp),
//        horizontalArrangement = Arrangement.Center,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Icon(
//            painter = painterResource(id = homeSection.icon),
//            contentDescription = null
//        )
//        if (currentRoute == homeSection.route) {
//            Spacer(modifier = Modifier.width(4.dp))
//            Text(text = homeSection.title.uppercase().substring(0..2))
//        }
//    }
//}
//
//val shapeTe = RoundedCornerShape(topEnd = 10.dp)
//val shapeTs = RoundedCornerShape(topStart = 10.dp)
//val itemWidth = 60.dp
