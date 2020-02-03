package chen.you.bindlayout.ui;


import android.view.View;

import butterknife.OnClick;
import chen.you.ant.layoutres.BindLayout;
import chen.you.bindlayout.R;
import chen.you.bindlayout.base.ToolbarActivity;

@BindLayout(layoutResId = R.layout.activity_main, actionBarResId = R.layout.actionbar_main)
public class MainActivity extends ToolbarActivity {

    @Override
    protected void initData() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl, TestFragment.newInstance()).commitAllowingStateLoss();
    }

    @OnClick(R.id.bt1)
    public void onClick(View v) {
        SecondActivity.lanuch(this);
    }

}
