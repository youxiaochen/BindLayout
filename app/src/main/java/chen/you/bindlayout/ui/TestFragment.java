package chen.you.bindlayout.ui;

import android.view.View;

import butterknife.OnClick;
import chen.you.ant.layoutres.BindLayout;
import chen.you.bindlayout.R;
import chen.you.bindlayout.base.BaseFragment;

/**
 * Created by Max on 2020/2/2.
 */

@BindLayout(layout = R.layout.fragment_test)
public class TestFragment extends BaseFragment {

    public static TestFragment newInstance() {
        return new TestFragment();
    }

    @OnClick(R.id.bt)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt:
                new TestDialog().show(getChildFragmentManager(), "TestDialog");
                break;
        }
    }

}
