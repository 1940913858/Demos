package com.example.meetyou.myapplication.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class LookMoreBgView :View{

    private lateinit var mPaint:Paint
    private var mRectF = RectF()

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        mPaint = Paint()
        mPaint.style = Paint.Style.FILL
        mPaint.color = Color.parseColor("#FF0000")
        mPaint.isAntiAlias = true
        mPaint.strokeWidth = 6f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        // 绘制背景
        mRectF.set(0f,0f,width*2f,height.toFloat())
        canvas?.drawArc(mRectF,90f,180f,false,mPaint)
    }

}