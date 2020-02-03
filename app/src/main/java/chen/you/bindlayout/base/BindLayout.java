package chen.you.bindlayout.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Max on 2020/2/2.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BindLayout {




    /**
     * 内容资源布局
     * @return
     */
    int layoutResId() default 0;

    /**
     * actionBar资源布局,或用于其他资源布局
     * @return
     */
    int actionBarResId() default 0;

}
