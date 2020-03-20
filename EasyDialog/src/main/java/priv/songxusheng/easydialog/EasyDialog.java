package priv.songxusheng.easydialog;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

public class EasyDialog {
    protected AlertDialog dialog;
    protected View vMain;
    protected Context context;
    protected String TAG="EasyDialog";
    public static int HEIGHT=0,WIDTH=0;
    IEasyDialogConfig dialogConfig;
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            try{ if(!dialogConfig.onHandleMessage(msg)) super.handleMessage(msg);}
            catch (Exception e){ Log.e(TAG+".onHandleMessage", String.format("Error:%s \nMessage:%s ",e.getMessage(),msg.toString()));}
            finally { System.gc();}
        }
    };

    public EasyDialog(Context context) {
        this.context=context;
        dialog = new AlertDialog.Builder(context).create();
    }

    public EasyDialog(Context context, @StyleRes int theme) {
        this.context=context;
        dialog = new AlertDialog.Builder(context,theme).create();
    }

    public EasyDialog(AlertDialog dialog) {
        this.context=dialog.getContext();
        this.dialog = dialog;
    }

    public EasyDialog setRootView(@LayoutRes int id){
        vMain= LayoutInflater.from(context).inflate(id, null);
        HEIGHT=((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight();
        WIDTH=((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
        dialogConfig.onBindDialog(vMain,dialog);
        return this;
    }

    public EasyDialog setAllowDismissWhenTouchOutside(boolean isAllow){
        dialog.setCanceledOnTouchOutside(isAllow);
        return this;
    }

    public EasyDialog setAllowDismissWhenBackPressed(boolean isAllow){
        if(!isAllow){
            dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    return keyCode == KeyEvent.KEYCODE_BACK;
                }
            });
        }
        return this;
    }

    public EasyDialog showDialog(){
        dialog.show();
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
//        dialog.getWindow().setBackgroundDrawableResource(null);
//        dialog.setView(vMain);
//        dialog.setContentView(vMain);
//        dialog.getWindow().clearFlags(FLAG_DIM_BEHIND);//清除灰色背景
        dialog.getWindow().setContentView(vMain);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                hideSoftInputUtil();
            }
        });
        WindowManager.LayoutParams layoutParams=dialog.getWindow().getAttributes();
        dialogConfig.onConfigDialog(layoutParams);
        dialog.getWindow().setAttributes(layoutParams);
        return this;
    }

    public void dismissDialog(){
        hideSoftInputUtil();
        dialog.dismiss();
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public int px2dip(float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * dp 转 px(像素)
     *
     * @param dpValue dp 值
     * @return px 值
     */
    public int dp2px(final float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * dp 转 px
     *
     * @param dpValue dp 值
     * @return px 值
     */
    public static int dp2px(Context context, final float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void hideSoftInputUtil() {//隐藏软键盘
        try{((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(vMain.getWindowToken(), 0); }catch (Exception e){ Log.e(TAG+".HideInput",e.getMessage());}
    }

    public EasyDialog setDialogConfig(IEasyDialogConfig dialogConfig){
        this.dialogConfig = dialogConfig;
        return this;
    }
}