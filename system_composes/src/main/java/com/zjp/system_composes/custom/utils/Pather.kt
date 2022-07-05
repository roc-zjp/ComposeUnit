package com.zjp.system_composes.custom.utils


import androidx.compose.ui.graphics.Path
import kotlin.math.cos
import kotlin.math.sin


fun nStarPath(num: Int, R: Double, r: Double, dx: Int = 0, dy: Int = 0): Path {
    var path = Path()
    val perRad: Double = 2 * Math.PI / num //每份的角度

    val radA = perRad / 2 / 2 //a角

    val radB: Double = 2 * Math.PI / (num - 1) / 2 - radA / 2 + radA //起始b角

    path.moveTo((cos(radA) * R + dx).toFloat(), (-sin(radA) * R + dy).toFloat()) //移动到起点

    for (i in 0 until num) { //循环生成点，路径连至
        path.lineTo(
            (cos(radA + perRad * i) * R + dx).toFloat(),
            (-sin(radA + perRad * i) * R + dy).toFloat()
        )
        path.lineTo(
            (cos(radB + perRad * i) * r + dx).toFloat(),
            (-sin(radB + perRad * i) * r + dy).toFloat()
        )
    }
    path.close()
    return path
}