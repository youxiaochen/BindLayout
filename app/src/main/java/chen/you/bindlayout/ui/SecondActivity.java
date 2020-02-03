package chen.you.bindlayout.ui;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Max on 2020/2/2.
 */

public class SecondActivity extends BSActivity {

    public static void lanuch(Context context) {
        context.startActivity(new Intent(context, SecondActivity.class));
    }

    @Override
    protected void initData() {
        tv.setText("SecondActivity...");
    }
}
