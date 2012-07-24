package com.smithfox.game;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameActivity extends Activity {
	private final static String TAG="GA";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new Game1View(this));
	}

	class Game1View extends SurfaceView implements SurfaceHolder.Callback {
		private SurfaceHolder holder;
		private MyThread myThread;

		public Game1View(Context context) {
			super(context);
			holder = this.getHolder();
			holder.addCallback(this);
			myThread = new MyThread(holder);
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {


		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			Log.d(TAG, "surfaceCreated");
			myThread.isRun = true;
			myThread.start();
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			Log.d(TAG, "surfaceDestroyed");
			myThread.isRun = false;
		}

	}

	class MyThread extends Thread {
		private SurfaceHolder holder;
		public boolean isRun;

		public MyThread(SurfaceHolder holder) {
			this.holder = holder;
			isRun = true;
		}

		@Override
		public void run() {
			int count = 0;
			Log.d(TAG, "Thread start");
			while (isRun) {
				Canvas c = null;
				try {
					synchronized (holder) {
						//Log.d(TAG, "draw");
						c = holder.lockCanvas();
						c.drawColor(Color.BLACK);
						Paint p = new Paint();
						p.setColor(Color.WHITE);
						Rect r = new Rect(100, 50, 300, 250);
						c.drawRect(r, p);
						c.drawText("现在:" + (count++) + "秒", 100, 310, p);
						Thread.sleep(1000);
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				} finally {
					if (c != null) {
						holder.unlockCanvasAndPost(c);

					}
				}
			}
			Log.d(TAG, "Thread end");
		}
	}
}