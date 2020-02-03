package chen.you.bindlayout.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import chen.you.ant.layoutres.LayoutResIds;
import chen.you.bindlayout.R;

/**
 * Created by Max on 2020/2/2.
 * ToolbarActivity布局中必须要设置相应的toolbar
 */

public abstract class ToolbarActivity extends AppCompatActivity {

    protected Toolbar mToolbar;
    /**
     * ...这里只展示BindLayout的用法, 其他封装不包括
     */
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutResId());
        mToolbar = findViewById(R.id.mToolbar);
        getLayoutInflater().inflate(getActionBarResId(), mToolbar);
        setSupportActionBar(mToolbar);

        unbinder = ButterKnife.bind(this);
        //......

        initData();
    }

    @Override
    protected void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroy();
    }

    protected void initData() {

    }


    /**
     * activity布局内容资源id
     */
    protected int getLayoutResId() {
        long c = System.currentTimeMillis();
        int id = LayoutResIds.layoutId(this.getClass());
        Log.i("youmvp", "LayourResIds layout time = " + (System.currentTimeMillis() - c));
        return id;
    }

    /**
     * ActionBar布局内容资源id
     */
    protected int getActionBarResId() {
        long c = System.currentTimeMillis();
        int id = LayoutResIds.actionbarId(this.getClass());
        Log.i("youmvp", "LayourResIds actionbarId time = " + (System.currentTimeMillis() - c));
        return id;
    }


}
