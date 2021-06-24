package com.example.meetyou.myapplication.view

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Point
import android.os.Build
import android.os.Vibrator
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.OverScroller
import com.example.meetyou.myapplication.R
import com.example.meetyou.myapplication.utils.Utils
import java.lang.Math.abs

class RecycleScrollLayout : LinearLayout {

    private val PERMISSION_REQUEST_CODE = 9999
    private var dp28 = 0
    private var dp50 = 0
    private var dp40 = 0
    private var dp16 = 0
    private lateinit var overScroll: OverScroller
    private var recyclerView: RecyclerView? = null
    private var lastX = 0f
    private var lastY = 0f
    private var mStart = 0
    private var mEnd = 0
    private var startX = 0
    private var startY = 0
    private var currentX = 0
    private var currentY = 0
    private var mNeedVibrator = true
    private var mVibrator: Vibrator? = null
    private var layoutMore: LinearLayout? = null
    private var lookMoreBgView: LookMoreBgView? = null
    private var framLayout: FrameLayout? = null

    private var disX = 0

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        dp16 = 16*3
        dp28 = 28*3
        dp50 = 42*3
        dp40 = 40*3
        overScroll = OverScroller(context)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount > 1 && getChildAt(0) is RecyclerView) {
            recyclerView = getChildAt(0) as RecyclerView
            val lp = recyclerView?.layoutParams
            lp?.width = Utils.getScreenWidth(context) - dp16
            recyclerView?.layoutParams = lp
        }
        if (childCount > 1) {
            val view = getChildAt(1)
            framLayout = view as FrameLayout
            lookMoreBgView = view.findViewById(R.id.lookMoreBgView)
            layoutMore = view.findViewById(R.id.layout_lookmore)
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val x = ev.x
        val y = ev.y
        when (ev.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                lastX = x
                lastY = y
                startX = x.toInt()
                startY = y.toInt()
                currentX = x.toInt()
                currentY = y.toInt()
                mStart = scrollX
                disX = 0
            }
            MotionEvent.ACTION_MOVE -> {
                if (recyclerView != null && lastX - x > 0 && abs(lastX - x) > abs(lastY - y) && !isCanScrollRight(recyclerView)) {
                    // 判断是否是滑动到最右边了，如果滑动到最后边就处理，否则就不处理
                    return true
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    fun isCanScrollRight(recyclerView: RecyclerView?): Boolean {
        return recyclerView?.canScrollHorizontally(1) ?: false
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x.toInt()
        val y = event.y.toInt()
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                startX = x
                startY = y
                currentX = x
                currentY = y
                mStart = scrollX
                disX = 0
                if (!overScroll.isFinished) {
                    overScroll.abortAnimation()
                }
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = abs(x - startX)
                val dy = abs(y - startY)
                if (dx > dy && parent != null) {
                    parent.requestDisallowInterceptTouchEvent(true)
                }
                disX += dx

                // 触发一次震动
                if (scrollX - mStart >= dp40) {
                    showVibrator()
                    if (mNeedVibrator) listener?.onDot()
                    mNeedVibrator = false
                } else {
                    mNeedVibrator = true
                }

                if (scrollX - mStart <= dp50
//                        && scrollX-mStart>=0 && !(scrollX-mStart == 0 && x>currentX)
                ) {
                    val dis = dp50 - (scrollX - mStart)
                    var realBy = currentX - x
                    if (realBy > dis) {
                        realBy = dis
                    }
                    scrollBy(realBy, 0)
                }

//                if(scrollX-mStart>=dp50 && x>currentX){
//                    scrollBy(currentX-x,0)
//                }
                resetLookMoreWidth(scrollX - mStart)

                currentX = x
                currentY = y

            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_OUTSIDE -> {
                currentX = x
                currentY = y
                mEnd = scrollX
                val dScrollX = mEnd - mStart
                if (Math.abs(dScrollX) >= dp40) {
                    listener?.onEnter()
                }
                overScroll.startScroll(mEnd, 0, -dScrollX, 0, 500)
                postInvalidate()
            }
        }
        return true
    }

    private fun resetLookMoreWidth(width: Int) {
        var w = width
        if (w <= dp28) {
            w = dp28
        }

        val layoutLP = framLayout?.layoutParams
        layoutLP?.width = w
        framLayout?.layoutParams = layoutLP

        if (w > dp40) {
//            w = dp40
//            return
        }

        val lp = lookMoreBgView?.layoutParams as FrameLayout.LayoutParams
        lp?.width = w
        lp?.gravity = right
        lookMoreBgView?.layoutParams = lp

        val tvLP = layoutMore?.layoutParams
        tvLP?.width = w
        layoutMore?.layoutParams = tvLP
    }

    override fun computeScroll() {
        super.computeScroll()
        if (overScroll.computeScrollOffset()) {
            scrollTo(overScroll.currX, 0)
            postInvalidate()
        }
    }

    private var listener: OnEnterAllListener? = null
    fun setOnEnterAllListener(listener: OnEnterAllListener?) {
        this.listener = listener
    }

    interface OnEnterAllListener {
        fun onEnter()
        fun onDot()
    }

    private fun showVibrator() {
        if (!(context != null && context is Activity)) {
            return
        }
        if (!mNeedVibrator) {
            return
        }
        if (checkPersmission(context, Manifest.permission.VIBRATE)) {
            if (mVibrator == null) {
                mVibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            }
            mVibrator?.vibrate(200)
        } else {
            requestPermissions(context as Activity, arrayOf(Manifest.permission.VIBRATE), PERMISSION_REQUEST_CODE)
        }
    }

    fun checkPersmission(context: Context?, permission: String?): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }
        return if (null != context && context.checkSelfPermission(permission!!) != PackageManager.PERMISSION_GRANTED) {
            false
        } else true
    }

    /**
     * 申请权限
     * @param activity
     * @param permissions
     * @param requestCode
     */
    fun requestPermissions(activity: Activity?, permissions: Array<String?>?, requestCode: Int) {
        // 先检查是否已经授权
        permissions?.let {
            if (!checkPermissionsGroup(activity, permissions)) {
                ActivityCompat.requestPermissions(activity!!, permissions!!, requestCode)
            }
        }
    }

    /**
     * 检查多个权限
     * @param context
     * @param permissions
     * @return
     */
    fun checkPermissionsGroup(context: Context?, permissions: Array<String?>): Boolean {
        var result = false
        for (permission in permissions) {
            result = checkPersmission(context, permission)
            if (!result) return false
        }
        return result
    }
}