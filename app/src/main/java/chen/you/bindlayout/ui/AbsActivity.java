package chen.you.bindlayout.ui;

import android.widget.TextView;

import butterknife.BindView;
import chen.you.ant.layoutres.BindLayout;
import chen.you.bindlayout.R;
import chen.you.bindlayout.base.ToolbarActivity;

/**
 * Created by Max on 2020/2/2.
 */
@BindLayout(layout = R.layout.activity_abs, actionBar = R.layout.actionbar_main)
public abstract class AbsActivity extends ToolbarActivity {

    @BindView(R.id.tv)
    TextView tv;


}
