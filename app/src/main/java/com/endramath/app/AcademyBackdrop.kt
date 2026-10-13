package com.endramath.app

import android.content.Context
import android.graphics.*
import android.view.View

/** Keeps the complete academy and dragon visible in portrait rather than center-cropping them away. */
class AcademyBackdrop(context: Context) : View(context) {
    private val scene = BitmapFactory.decodeResource(resources, R.drawable.academy_scene)
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
    private val fade = Paint()
    private val bounds = RectF()
    private var fadeStart = 0f
    init { importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_NO }
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        val drawnHeight = w.toFloat() * scene.height / scene.width
        bounds.set(0f, 0f, w.toFloat(), drawnHeight)
        fadeStart = drawnHeight * .8f
        fade.shader = if(h > drawnHeight) LinearGradient(0f, fadeStart, 0f, drawnHeight + h * .12f,
            Color.TRANSPARENT, Color.rgb(39, 17, 7), Shader.TileMode.CLAMP) else null
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.rgb(39, 17, 7))
        canvas.drawBitmap(scene, null, bounds, paint)
        if(fade.shader != null) canvas.drawRect(0f, fadeStart, width.toFloat(), height.toFloat(), fade)
    }
}
