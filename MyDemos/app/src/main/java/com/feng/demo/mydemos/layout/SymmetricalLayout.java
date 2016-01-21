package com.feng.demo.mydemos.layout;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import static com.feng.demo.utils.MyLogUtils.JLog;

/**
 * Created by lenovo on 2016/1/13.
 */
public class SymmetricalLayout extends ViewGroup {
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    protected int mPaddingLeft = 0;
    protected int mPaddingRight = 0;
    protected int mPaddingTop = 0;
    protected int mPaddingBottom = 0;


    private int mOrientation = HORIZONTAL;
    private int mGravity = Gravity.START | Gravity.TOP;
    private int mTotalLength;

    public SymmetricalLayout(Context context) {
        super(context);
        JLog("SymmetricalLayout");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        JLog("onDraw");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        JLog("onLayout " + l + " " + t + " " + r + " " + b);
        if (mOrientation == VERTICAL) {
            layoutVertical(l, t, r, b);
        } else {
            layoutHorizontal(l, t, r, b);
        }
    }

    void layoutHorizontal(int left, int top, int right, int bottom) {
        JLog("layoutHorizontal");
        final boolean isLayoutRtl = isLayoutRtl();
        final int paddingTop = mPaddingTop;
        int childTop;
        //int childLeft;
        final int height = bottom - top;
        int childBottom = height - mPaddingBottom;
        int childSpace = height - paddingTop - mPaddingBottom;
        final int count = getVirtualChildCount();
        //final int layoutDirection = getLayoutDirection();
        //childLeft = mPaddingLeft;
        int start = 0;
        int dir = 1;
        JLog("count=" + count);
        childTop = mPaddingTop;
        if (count % 2 == 0) {
            final int center = (int) (left + right) / 2;
            int leftOdd = center;
            int leftEven = center;
            for (int i = 0; i < count; i++) {
                int childIndex = start + dir * i;
                final View child = getVirtualChildAt(childIndex);
                final int childWidth = child.getMeasuredWidth();
                final int childHeight = child.getMeasuredHeight();
                if (i % 2 != 0) {
                    int childLeft = leftOdd - childWidth;
                    leftOdd = childLeft;
                    setChildFrame(child, childLeft + getLocationOffset(child), childTop,
                            childWidth, childHeight);
                } else {
                    int childLeft = leftEven;
                    leftEven += childWidth;
                    setChildFrame(child, childLeft + getLocationOffset(child), childTop,
                            childWidth, childHeight);
                }
            }
        } else {
            final int center = (int) (left + right) / 2;
            int leftOdd = 0;
            int leftEven = 0;
            for (int i = 0; i < count; i++) {
                int childIndex = start + dir * i;
                final View child = getVirtualChildAt(childIndex);
                final int childWidth = child.getMeasuredWidth();
                final int childHeight = child.getMeasuredHeight();
                if (i == 0) {
                    int childLeft = center - (childWidth / 2);
                    int childRight = center + (childWidth / 2);
                    leftOdd = childLeft;
                    leftEven = childRight;
                    setChildFrame(child, childLeft + getLocationOffset(child), childTop,
                            childWidth, childHeight);
                } else if (i % 2 != 0) {
                    int childLeft = leftEven;
                    leftEven = childLeft + childWidth;
                    setChildFrame(child, childLeft + getLocationOffset(child), childTop,
                            childWidth, childHeight);
                } else {
                    int childLeft = leftOdd - childWidth;
                    leftOdd = childLeft;
                    setChildFrame(child, childLeft + getLocationOffset(child), childTop,
                            childWidth, childHeight);
                }

            }
        }
    }

    private void setChildFrame(View child, int left, int top, int width, int height) {
        child.layout(left, top, left + width, top + height);
    }

    int getLocationOffset(View child) {
        return 0;
    }

    private boolean isLayoutRtl() {
        return false;
    }

    void layoutVertical(int l, int t, int r, int b) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        JLog("onMeasure " + widthMeasureSpec + " " + heightMeasureSpec);
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mOrientation == VERTICAL) {
            measureVertical(widthMeasureSpec, heightMeasureSpec);
        } else {
            measureHorizontal(widthMeasureSpec, heightMeasureSpec);
        }
    }

    private void measureVertical(int widthMeasureSpec, int heightMeasureSpec) {

    }

    private void measureHorizontal(int widthMeasureSpec, int heightMeasureSpec) {
        JLog("measureHorizontal");
        mTotalLength = 0;
        int maxHeight = 0;
        final int count = getVirtualChildCount();
        int childState = 0;
        boolean allFillParent = true;
        int alternativeMaxHeight = 0;

        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        final boolean isExactly = widthMode == MeasureSpec.EXACTLY;
        boolean matchHeight = false;
        boolean skippedMeasure = false;

        for (int i = 0; i < count; ++i) {
            final View child = getVirtualChildAt(i);
            if (child == null) {
                mTotalLength += measureNullChild(i);
                continue;
            }
            if (child.getVisibility() == GONE) {
                i += getChildrenSkipCount(child, i);
                continue;
            }
            final SymmetricalLayout.LayoutParams lp = (SymmetricalLayout.LayoutParams)
                    child.getLayoutParams();
            int oldWidth = Integer.MIN_VALUE;
            measureChildBeforeLayout(child, i, widthMeasureSpec,
                    mTotalLength,
                    heightMeasureSpec, 0);
            if (oldWidth != Integer.MIN_VALUE) {
                lp.width = oldWidth;
            }
            final int childWidth = child.getMeasuredWidth();
            if (isExactly) {
                mTotalLength += childWidth + lp.leftMargin + lp.rightMargin +
                        getNextLocationOffset(child);
            } else {
                final int totalLength = mTotalLength;
                mTotalLength = Math.max(totalLength, totalLength + childWidth + lp.leftMargin +
                        lp.rightMargin + getNextLocationOffset(child));
            }

            boolean matchHeightLocally = false;
            if (heightMode != MeasureSpec.EXACTLY && lp.height == LayoutParams.MATCH_PARENT) {
                // The height of the linear layout will scale, and at least one
                // child said it wanted to match our height. Set a flag indicating that
                // we need to remeasure at least that view when we know our height.
                matchHeight = true;
                matchHeightLocally = true;
            }
            final int margin = lp.topMargin + lp.bottomMargin;
            final int childHeight = child.getMeasuredHeight() + margin;
            childState = combineMeasuredStates(childState, child.getMeasuredState());
            maxHeight = Math.max(maxHeight, childHeight);
            allFillParent = allFillParent && lp.height == LayoutParams.MATCH_PARENT;

            i += getChildrenSkipCount(child, i);
        }

        mTotalLength += mPaddingLeft + mPaddingRight;
        int widthSize = mTotalLength;
        widthSize = Math.max(widthSize, getSuggestedMinimumWidth());
        int widthSizeAndState = resolveSizeAndState(widthSize, widthMeasureSpec, 0);
        widthSize = widthSizeAndState & MEASURED_SIZE_MASK;
        if (!allFillParent && heightMode != MeasureSpec.EXACTLY) {
            maxHeight = alternativeMaxHeight;
        }

        maxHeight += mPaddingTop + mPaddingBottom;
        maxHeight = Math.max(maxHeight, getSuggestedMinimumHeight());
        setMeasuredDimension(widthSizeAndState | (childState & MEASURED_STATE_MASK),
                resolveSizeAndState(maxHeight, heightMeasureSpec,
                        (childState << MEASURED_HEIGHT_STATE_SHIFT)));
    }

    int getNextLocationOffset(View child) {
        return 0;
    }

    void measureChildBeforeLayout(View child, int childIndex,
                                  int widthMeasureSpec, int totalWidth, int heightMeasureSpec,
                                  int totalHeight) {
        measureChildWithMargins(child, widthMeasureSpec, totalWidth,
                heightMeasureSpec, totalHeight);
    }

    int getChildrenSkipCount(View child, int index) {
        return 0;
    }

    int measureNullChild(int childIndex) {
        return 0;
    }

    View getVirtualChildAt(int index) {
        return getChildAt(index);
    }

    private int getVirtualChildCount() {
        return getChildCount();
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        if (mOrientation == HORIZONTAL) {
            return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        } else if (mOrientation == VERTICAL) {
            return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        }
        return null;
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public int gravity = -1;

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams p) {
            super(p);
        }
    }
}
