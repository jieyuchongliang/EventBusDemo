package retrofitdemo.fujisoft.com.eventbusdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {
    private TextView tv_message;
    private Button  bt_message,bt_nian,bt_get_nian_message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_message = (TextView) findViewById(R.id.tv_message);
        bt_message = (Button) findViewById(R.id.bt_message);
        bt_nian = (Button) findViewById(R.id.btn_nian);
        bt_get_nian_message = (Button) findViewById(R.id.btn_get_nian_message);
        //测试非粘性事件的点击按扭事件（注册EventBus与跳转事件同步）
        bt_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SecondActivity.class));
                //注册事件
                if (!EventBus.getDefault().isRegistered(MainActivity.this)){
                    EventBus.getDefault().register(MainActivity.this);
                }
            }
        });

        //测试粘性事件的点击按钮事件（注册EventBus与跳转事件不同步）
        bt_nian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SecondActivity.class));
            }
        });
        //当某处发送了粘性事件，点击此按钮就能接收到。
        bt_get_nian_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //注册事件
                if (!EventBus.getDefault().isRegistered(MainActivity.this)){
                    EventBus.getDefault().register(MainActivity.this);
                }
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册事件
        EventBus.getDefault().unregister(this);
    }

    /**
     * 非粘性事件走此方法
     * @param messageEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventBus(MessageEvent<Person> messageEvent){
        tv_message.setText(messageEvent.getMessage().age);
    }

    /**
     * 粘性事件走此方法
     * @param messageEvent
     */
    @Subscribe(sticky = true)
    public void nianEventBus(MessageEvent<Person> messageEvent){
        tv_message.setText(messageEvent.getMessage().name);
    }

}
