# EasyDialog

https://blog.csdn.net/best335/article/details/104839549

还在为使用Dialog而烦恼吗？
快使用更灵活，更简单的EasyDialog创建Dialog吧~
# 使用
新建Android Studio项目
修改build.gradle
```java
//刚发布可能会有延迟才能下载到（预计16:00之前一定可以下载到）
//support SDK
dependencies {
	...
	api "com.github.isong0623:EasyDialog:1.0-support"
	...
}
//AndroidX SDK
dependencies {
	...
	api "com.github.isong0623:EasyDialog:1.0-androidx"
	...
}
```
在MainActivity的onCreate增加如下代码
```java
 		EasyDialog dialog = new EasyDialog(this)
                .setDialogConfig(new IEasyDialogConfig() {//创建一个弹窗配置信息
                    @Override
                    public void onBindDialog(View vRoot, AlertDialog dialog) {
                        dialog.getWindow().setBackgroundDrawableResource(R.drawable.ic_launcher_background);
                    	TextView tv = vRoot.findViewById(R.id.textview);//这个vRoot就是对话框的根View，用它可以找到所有的子控件。
                    }

                    @Override
                    public void onConfigDialog(WindowManager.LayoutParams params) {
                        params.height = EasyDialog.dp2px(MainActivity.this,100f);//你可以设置固定dp大小
                        params.width =  EasyDialog.WIDTH/4*3;//设置固定比例
                        params.horizontalMargin = 0.1f;//水平左侧的margin比例（基于Gravity）
                        params.verticalMargin = 0.1f;//垂直顶部的margin比例（基于Gravity）
                        params.gravity = Gravity.CENTER;
                    }

                    @Override
                    public boolean onHandleMessage(Message msg) {
                        switch (msg.what){
                            case 0:
                                return true;
                        }
                        return false;
                    }
                })
                .setRootView(R.layout.activity_main)
                .setAllowDismissWhenTouchOutside(true)
                .setAllowDismissWhenBackPressed(true)//禁用返回键
                .showDialog();

        dialog.handler.obtainMessage(0).sendToTarget();//一些消息可以在其他部分发送
```

# 效果
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200313135125950.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2Jlc3QzMzU=,size_16,color_FFFFFF,t_70)
