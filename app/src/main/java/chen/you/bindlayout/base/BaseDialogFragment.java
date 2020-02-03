package chen.you.bindlayout.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import chen.you.ant.layoutres.LayoutResIds;
import chen.you.bindlayout.R;

/**
 * Created by You on 2017/1/5.
 *  建议使用此Fragment
 */

public abstract class BaseDialogFragment extends DialogFragment {
    /**
     * ...这里只展示BindLayout的用法, 其他封装不包括
     */
    private Unbinder unbinder;

    /**
     * 样式
     * @return
     */
    @Override
    public int getTheme() {
        return R.style.baseDialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = getLayoutInflater().inflate(getLayoutResId(), container, false);
        unbinder = ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onDestroyView() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroyView();
    }

    /**
     * fragment布局内容资源id
     */
    protected int getLayoutResId() {
        return LayoutResIds.layoutId(this.getClass());
    }
}
