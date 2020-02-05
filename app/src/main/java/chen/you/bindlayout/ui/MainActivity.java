package chen.you.bindlayout.ui;


import android.support.annotation.LayoutRes;
import android.view.View;

import butterknife.OnClick;
import butterknife.internal.Utils;
import chen.you.ant.layoutres.BindLayout;
import chen.you.ant.layoutres.LayoutResIds;
import chen.you.bindlayout.R;
import chen.you.bindlayout.base.ToolbarActivity;

@BindLayout(layout = R.layout.activity_main, actionBar = R.layout.actionbar_main)
public class MainActivity extends ToolbarActivity {

    @Override
    protected void initData() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl, TestFragment.newInstance()).commitAllowingStateLoss();
    }

    @OnClick(R.id.bt1)
    public void onClick(View v) {
        //Layout
        //LayoutResIds
        SecondActivity.lanuch(this);


    }

}
