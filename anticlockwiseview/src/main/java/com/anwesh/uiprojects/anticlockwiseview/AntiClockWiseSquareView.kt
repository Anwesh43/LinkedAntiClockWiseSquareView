package com.anwesh.uiprojects.anticlockwiseview

/**
 * Created by anweshmishra on 28/04/19.
 */

import android.view.View
import android.view.MotionEvent
import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Color

val nodes : Int = 5
val lines : Int = 3
val scGap : Float = 0.05f
val scDiv : Double = 0.05
val strokeFactor : Int = 90
val sizeFactor : Float = 2.9f
val foreColor : Int = Color.parseColor("#673AB7")
val backColor : Int = Color.parseColor("#BDBDBD")

fun Int.inverse() : Float = 1f / this
fun Float.scaleFactor() : Float = Math.floor(this / scDiv).toFloat()
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n
fun Float.mirrorValue(a : Int, b : Int) : Float {
    val k : Float = scaleFactor()
    return (1 - k) * a.inverse() + k * b.inverse()
}
fun Float.updateValue(dir : Float, a : Int, b : Int) : Float = mirrorValue(a, b) * dir * scGap

fun Canvas.drawAnticlockWiseLine(x : Float, y : Float, rot : Float, sc : Float, paint : Paint) {
    save()
    translate(-x, y)
    rotate(-rot * sc)
    drawLine(0f, 0f, 2 * x, 0f, paint)
    restore()
}

fun Canvas.drawACWSNode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    val gap : Float = h / (nodes + 1)
    val size : Float = gap / sizeFactor
    val deg : Float = 360f / (lines + 1)
    val rotDeg : Float = 180f - deg
    val x : Float = size * Math.cos((rotDeg / 2) * Math.PI / 180).toFloat()
    val y : Float = size * Math.sin((rotDeg / 2) * Math.PI / 180).toFloat()
    var totalDeg : Float = 0f
    val sc1 : Float = scale.divideScale(0, 2)
    val sc2 : Float = scale.divideScale(1, 2)
    paint.color = foreColor
    paint.strokeWidth = Math.min(w, h) / strokeFactor
    paint.strokeCap = Paint.Cap.ROUND
    save()
    translate(w / 2, gap * (i + 1))
    rotate(90f * sc2)
    drawLine(-x, y, 2 * x, y, paint)
    for (j in 0..(lines - 1)) {
        val sc : Float = sc1.divideScale(j, lines)
        totalDeg += deg * Math.floor(sc.toDouble()).toFloat()
        save()
        rotate(totalDeg)
        drawAnticlockWiseLine(-x, y, rotDeg, sc, paint)
        restore()
    }
    restore()
}