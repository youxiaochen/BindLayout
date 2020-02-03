package chen.you.bindlayout.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import chen.you.ant.layoutres.BindLayout;
import chen.you.bindlayout.R;
import chen.you.bindlayout.base.BaseDialogFragment;

/**
 * Created by Max on 2020/2/2.
 */

@BindLayout(layoutResId = R.layout.dialog_test)
public class TestDialog extends BaseDialogFragment {

    @BindView(R.id.tv)
    TextView tv;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tv.setText("this is test Dialog");
    }
}
