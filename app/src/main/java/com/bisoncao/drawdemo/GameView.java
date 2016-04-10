package com.bisoncao.drawdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Bison Cao
 * @created 17:50 04/06/2016
 */
public class GameView extends View {

    public static Block block;
    private Thread curThread;

    public Handler handler;
    //定义方向
    public static int dir = -1;
    //上下左右
    public static final int DIRUP = 1;
    public static final int DIRDOWN = 2;
    public static final int DIRLEFT = 3;
    public static final int DIRRIGHT = 4;
    public static final int REFRESH = 5;

    public GameView(Context context) {
        this(context, null, 0);
    }

    public GameView(Context context, AttributeSet attrs,
                    int defStyle) {
        super(context, attrs, defStyle);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context);
        //创建俄罗斯方块对象
        this.block = new Block(this);

        handler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case DIRLEFT:
                        GameView.block.moveLeft();
                        break;
                    case DIRRIGHT:
                        GameView.block.moveRight();
                        break;
                    case REFRESH:
                        invalidate();
                        break;

                    default:
                        GameView.block.downLoad();
                        break;
                }

            }
        };

    }

    public void start() {
        //创建线程
        createThread();
    }

    private boolean hasCancel;

    /**
     * 保证仅有一个线程在运行
     * 如果已经有一个线程在运行，则先中断该线程，再创建新线程
     * 如果已经取消，则不创建新线程
     */
    private synchronized void createThread() {
        if (curThread == null) {
            System.out.println("bc: curThread == null, creating new thread");
            curThread = new Thread(new Runnable() {

                @Override
                public void run() {
                    System.out.println("bc: " + Thread.currentThread().getName() +
                            " created");
                    while (!Thread.interrupted()) {
                        try {
                            System.out.println("bc: 子线程名称：：：" + Thread.currentThread().getName());
                            //block.downLoad();
                            Thread.sleep(1000);
                            handler.sendEmptyMessage(dir);
                        } catch (InterruptedException ie) {
                            System.out.println("bc: interrupted");
                            curThread.interrupt(); // 中断当前线程
                            curThread = null;
                            if (!hasCancel) {
                                block.reset(); // 回到原点
                                handler.sendEmptyMessage(REFRESH); // 立即重绘
                                createThread(); // 创建一个新线程
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("bc: " + Thread.currentThread().getName() +
                            " exit");

                }

            });
            curThread.start();
        } else {
            System.out.println("bc: curThread != null & interrupting it");
            curThread.interrupt();
        }
    }

    public synchronized void stop() {
        if (curThread != null) {
            hasCancel = true;
            curThread.interrupt();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //设置了画布的颜色
        canvas.drawARGB(255, 0, 0, 255);
        //设置一个画笔
        Paint paint = new Paint();
        paint.setARGB(255, 255, 0, 0);
        canvas.drawCircle(block.getX(), block.getY(), 10, paint);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        System.out.println("bc: onDetachedFromWindow()");
        stop();
    }
}
