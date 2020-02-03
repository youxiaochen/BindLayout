package chen.you.bindlayout.base;

import android.util.Log;

/**
 * Created by Max on 2020/2/2.
 */

public final class LayoutId {

    public static int layoutId(Class<?> clazz) {
        if (clazz == null) return 0;
        Log.i("youmvp", "clazz " + clazz.getName());
        if (clazz.isAnnotationPresent(BindLayout.class)) {
            BindLayout bindLayout = clazz.getAnnotation(BindLayout.class);
            return bindLayout.layoutResId();
        }
        return layoutId(clazz.getSuperclass());
    }

    public static int actionbarId(Class<?> clazz) {
        if (clazz == null) return 0;
        if (clazz.isAnnotationPresent(BindLayout.class)) {
            BindLayout bindLayout = clazz.getAnnotation(BindLayout.class);
            return bindLayout.actionBarResId();
        }
        return actionbarId(clazz.getSuperclass());
    }
}
