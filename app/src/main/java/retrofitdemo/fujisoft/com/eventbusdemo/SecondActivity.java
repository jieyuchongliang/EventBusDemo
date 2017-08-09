package retrofitdemo.fujisoft.com.eventbusdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;

public class SecondActivity extends AppCompatActivity {
    private Button button,btnNian;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        button = (Button) findViewById(R.id.btn_send_event_bus_message);
        btnNian = (Button) findViewById(R.id.btn_send_nian_message);
        //发送非粘性事件，此事件只有提前注册了EventBus，才能接受到信息，否则无法接受到
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MessageEvent<Person>(new Person("非粘性事件","1岁")));
            }
        });

        //发送粘性事件
        btnNian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(new MessageEvent<Person>(new Person("粘性事件","2岁")));
            }
        });
    }
}
