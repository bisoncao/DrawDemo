package com.bisoncao.drawdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * @author Bison Cao
 * @created 17:50 04/06/2016
 */
public class MainActivity extends Activity {
    //声明按钮控件
    public Button btn_start;
    public Button btn_right;
    public Button btn_left;
    private GameView mGameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取按钮控件
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_left = (Button) findViewById(R.id.btn_left);
        btn_right = (Button) findViewById(R.id.btn_right);
        mGameView = GameView.block.gameView;

        btn_left.setEnabled(false);
        btn_right.setEnabled(false);

        btn_start.setOnClickListener(new MyOnClickListener());
        btn_left.setOnClickListener(new MyOnClickListener());
        btn_right.setOnClickListener(new MyOnClickListener());

        System.out.println("主线程：：：" + Thread.currentThread().getName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.btn_start:
                    mGameView.start();
                    btn_left.setEnabled(true);
                    btn_right.setEnabled(true);

                    break;
                case R.id.btn_left:
                    Toast.makeText(MainActivity.this, "向左移动", Toast.LENGTH_LONG).show();
                    GameView.block.moveLeft();
                    //GameView.dir=GameView.DIRLEFT;
                    break;
                case R.id.btn_right:
                    Toast.makeText(MainActivity.this, "向右移动", Toast.LENGTH_LONG).show();
                    GameView.block.moveRight();
                    //GameView.dir=GameView.DIRRIGHT;
                    break;

                default:
                    break;
            }

        }

    }
}

