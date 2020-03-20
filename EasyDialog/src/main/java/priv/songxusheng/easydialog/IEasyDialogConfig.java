package priv.songxusheng.easydialog;

import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;

public interface IEasyDialogConfig {
    void onBindDialog(View vRoot, AlertDialog dialog);
    void onConfigDialog(WindowManager.LayoutParams params);
    boolean onHandleMessage(Message msg);
}
