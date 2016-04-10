package com.bisoncao.drawdemo;

/**
 * @author Bison Cao
 * @created 17:50 04/06/2016
 */
public class Block {

    private static final int INIT_X = 20;
    private static final int INIT_Y = 20;
    private int x = INIT_X, y = INIT_Y;

    public GameView gameView;

    public Block(GameView gameView) {
        this.gameView = gameView;
    }

    //向左移动
    public void moveLeft() {
        x -= 10;
        gameView.invalidate();
    }

    //向右移动
    public void moveRight() {
        x += 10;
        gameView.invalidate();
    }

    //下落方法
    public void downLoad() {
        y += 10;
        gameView.invalidate();//重新绘制
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void reset() {
        x = INIT_X;
        y = INIT_Y;
    }

}
