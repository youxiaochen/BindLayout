package chen.you.bindlayout.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import chen.you.ant.layoutres.LayoutResIds;

/**
 * Created by Max on 2020/2/2.
 */

public abstract class BaseActivity extends AppCompatActivity {

    /**
     * ...这里只展示BindLayout的用法, 其他封装不包括
     */
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutResId());
        unbinder = ButterKnife.bind(this);
        //......

    }

    @Override
    protected void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroy();
    }


    /**
     * activity布局内容资源id
     */
    protected int getLayoutResId() {
        return LayoutResIds.layoutId(this.getClass());
    }

}
