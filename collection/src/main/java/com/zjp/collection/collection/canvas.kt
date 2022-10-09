package com.zjp.collection.collection

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathDashPathEffect
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas


@Composable
fun PathDashPathEffectSample() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val mPaint = Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.style = Paint.Style.STROKE;
        mPaint.strokeWidth = 5f;
        mPaint.color = Color.RED;

        val mPaint1 = Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint1.style = Paint.Style.STROKE;
        mPaint1.strokeWidth = 5f;
        mPaint1.color = Color.RED;

        val mPaint2 = Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint2.style = Paint.Style.STROKE;
        mPaint2.strokeWidth = 5f;
        mPaint2.color = Color.RED;


        val mPath = Path()
        mPath.moveTo(0f, 300f)
        mPath.lineTo(200f, 300f)
        mPath.lineTo(400f, 100f)
        mPath.lineTo(600f, 400f)

        val mPath1 = Path()
        mPath1.moveTo(0f, 350f)
        mPath1.lineTo(200f, 350f)
        mPath1.lineTo(400f, 150f)
        mPath1.lineTo(600f, 450f)

        val mPath2 = Path()
        mPath2.moveTo(0f, 400f)
        mPath2.lineTo(200f, 400f)
        mPath2.lineTo(400f, 200f)
        mPath2.lineTo(600f, 500f)

        //第一种间隔，小圆点

        //第一种间隔，小圆点
        val pPath = Path()
        pPath.addCircle(0f, 0f, 5f, Path.Direction.CW)

        //第二种间隔，小梯形

        //第二种间隔，小梯形
        val pPath2 = Path()
        pPath2.lineTo(0f, 5f)
        pPath2.rLineTo(10f, 0f)
        pPath2.rLineTo(-5f, -5f)

        val mPathEffect = PathDashPathEffect(pPath, 40f, 0f, PathDashPathEffect.Style.ROTATE)
        val mPathEffect1 = PathDashPathEffect(pPath, 20f, 0f, PathDashPathEffect.Style.TRANSLATE)
        val mPathEffect2 = PathDashPathEffect(pPath2, 20f, 6f, PathDashPathEffect.Style.ROTATE)

        mPaint.pathEffect = mPathEffect
        mPaint1.pathEffect = mPathEffect1
        mPaint2.pathEffect = mPathEffect2

        drawIntoCanvas {
            it.nativeCanvas.drawPath(mPath, mPaint)
            it.nativeCanvas.drawPath(mPath1, mPaint1)
            it.nativeCanvas.drawPath(mPath2, mPaint2)
        }
    }
}