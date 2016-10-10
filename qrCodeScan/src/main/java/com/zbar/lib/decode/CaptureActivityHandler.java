package com.zbar.lib.decode;

import android.os.Handler;
import android.os.Message;

import com.zbar.lib.CaptureActivity;
import com.zbar.lib.Contrants;
import com.zbar.lib.camera.CameraManager;

/**
 * 作者: 陈涛(1076559197@qq.com)
 * 
 * 时间: 2014年5月9日 下午12:23:32
 *
 * 版本: V_1.0.0
 *
 * 描述: 扫描消息转发
 */
public final class CaptureActivityHandler extends Handler {

	DecodeThread decodeThread = null;
	CaptureActivity activity = null;
	private State state;

	private enum State {
		PREVIEW, SUCCESS, DONE
	}

	public CaptureActivityHandler(CaptureActivity activity) {
		this.activity = activity;
		decodeThread = new DecodeThread(activity);
		decodeThread.start();
		state = State.SUCCESS;
		CameraManager.get().startPreview();
		restartPreviewAndDecode();
	}

	@Override
	public void handleMessage(Message message) {

		switch (message.what) {
			case Contrants.auto_focus:
			if (state == State.PREVIEW) {
				CameraManager.get().requestAutoFocus(this, Contrants.auto_focus);
			}
			break;
		case Contrants.restart_preview:
			restartPreviewAndDecode();
			break;
		case Contrants.decode_succeeded:
			state = State.SUCCESS;
			activity.handleDecode((String) message.obj);// 解析成功，回调
			break;

		case Contrants.decode_failed:
			state = State.PREVIEW;
			CameraManager.get().requestPreviewFrame(decodeThread.getHandler(),
					Contrants.decode);
			break;
		}

	}

	public void quitSynchronously() {
		state = State.DONE;
		CameraManager.get().stopPreview();
		removeMessages(Contrants.decode_succeeded);
		removeMessages(Contrants.decode_failed);
		removeMessages(Contrants.decode);
		removeMessages(Contrants.auto_focus);
	}

	private void restartPreviewAndDecode() {
		if (state == State.SUCCESS) {
			state = State.PREVIEW;
			CameraManager.get().requestPreviewFrame(decodeThread.getHandler(),
					Contrants.decode);
			CameraManager.get().requestAutoFocus(this, Contrants.auto_focus);
		}
	}

}
