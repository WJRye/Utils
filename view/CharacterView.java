

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Author：王江 on 2016/6/30 17:45
 * Description: CharacterView主要是根据名称首字母的做快速查找，通常应用于联系人列表，好友列表等。
 */
public class CharacterView extends View {
    private String mCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    private int[] mCharacterYValues = null;

    public CharacterView(Context context) {
        super(context);
    }

    public CharacterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @TargetApi(21)
    public CharacterView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public CharacterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //设置默认的宽度
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.AT_MOST) {
            final int defaultWidth = 80;
            setMeasuredDimension(defaultWidth, heightSpecSize);
        }
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(2.0f);

        int paddingLeft = getPaddingLeft();
        //view实际宽度
        int width = getWidth() - paddingLeft - getPaddingRight();
        //绘制字符时的起始x坐标
        float startX = paddingLeft + (float) width / 2;

        int paddingTop = getPaddingTop();
        //view实际高
        int height = getHeight() - paddingTop - getPaddingBottom();
        int length = mCharacters.length();
        //每个字符所拥有的高度
        float characterHeight = (float) height / length;
        //绘制字符时的起始y坐标
        float startY = paddingTop + characterHeight / 2;
        //绘制的字符的界限
        Rect bounds = new Rect();
        mCharacterYValues = new int[length + 1];
        for (int i = 0; i < length; i++) {
            mPaint.getTextBounds(mCharacters, i, i + 1, bounds);
            float x = startX - (float) (bounds.left + bounds.right) / 2;
            float y = startY + i * characterHeight - (float) (bounds.top + bounds.bottom) / 2;
            canvas.drawText(mCharacters, i, i + 1, x, y, mPaint);
            //记录每个字符起始的y坐标值
            mCharacterYValues[i] = (int) (paddingTop + i * characterHeight);
        }
        //最后一个字符结束的y坐标值
        mCharacterYValues[length] = (int) (paddingTop + length * characterHeight);
    }

    /**
     * 设置字符的字体大小
     *
     * @param textSize 字体大小
     */
    public void setTextSize(float textSize) {
        mPaint.setTextSize(textSize);
    }

    /**
     * 设置画笔的颜色
     *
     * @param color 画笔颜色
     */
    public void setColor(int color) {
        mPaint.setColor(color);
    }

    /**
     * 插入新一个字符，与原来的字符表组成一个新的字符表
     *
     * @param c 字符
     */
    public void insertFirst(char c) {
        StringBuilder sb = new StringBuilder();
        sb.append(c);
        sb.append(mCharacters);
        mCharacters = null;
        mCharacters = sb.toString();
    }

    /**
     * 插入新一个字符，与原来的字符表组成一个新的字符表
     *
     * @param c 字符
     */
    public void insertLast(char c) {
        StringBuilder sb = new StringBuilder();
        sb.append(mCharacters);
        sb.append(c);
        mCharacters = null;
        mCharacters = sb.toString();
    }

    /**
     * 自定义字符表
     *
     * @param characters 字符串
     */
    public void setCharacter(String characters) {
        if (characters == null) return;
        mCharacters = null;
        mCharacters = characters;
    }

    /**
     * 设置CharacterView的触摸监听器
     *
     * @param listener CharacterView触摸监听器
     */
    public void setOnCharacterTouchListener(final OnCharacterTouchListener listener) {


        if (listener == null) {
            setOnTouchListener(null);
            return;
        }

        setOnTouchListener(new View.OnTouchListener() {
            char lastCharacter = ' ';

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int yDown = (int) motionEvent.getY();
                if (yDown > mCharacterYValues[0] && yDown < mCharacterYValues[mCharacterYValues.length - 1]) {
                    char c = findCharacter(yDown);
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            listener.onDown(view, c);
                            break;
                        case MotionEvent.ACTION_MOVE:
                            if (lastCharacter == ' ' || lastCharacter != c) {
                                listener.onMove(view, c);
                                lastCharacter = c;
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                            break;
                    }
                }
                return true;
            }
        });
    }

    /**
     * 根据坐标值找到对应的字符
     *
     * @param yValue y坐标值
     * @return 相应的字符
     */
    private char findCharacter(int yValue) {
        int low = 0, high = mCharacterYValues.length - 1;
        int lastMid = 0;
        while (low <= high) {
            int mid = (low + high) / 2;
            lastMid = mid;
            if (mCharacterYValues[mid] == yValue) {
                return mCharacters.charAt(mid);
            } else if (mCharacterYValues[mid] < yValue) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        //如果最后一次比较是大于目标值，则需要选择前一个下标
        if (mCharacterYValues[lastMid] > yValue) {
            lastMid -= 1;
        }
        return mCharacters.charAt(lastMid);
    }

    /**
     * 触摸CharacterView事件的监听器
     */
    public interface OnCharacterTouchListener {

        /**
         * 点击字符回调的方法
         *
         * @param view CharacterView
         * @param c    点击的字符
         */
        void onDown(View view, char c);

        /**
         * 在字符表上移动回调的方法
         *
         * @param view CharacterView
         * @param c    当前手指触摸的字符
         */
        void onMove(View view, char c);

    }
}
