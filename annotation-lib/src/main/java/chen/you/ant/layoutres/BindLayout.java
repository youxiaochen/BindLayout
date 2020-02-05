package chen.you.ant.layoutres;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by You on 2018/2/1.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface BindLayout {

    /**
     * 内容资源布局
     * @return
     */
    int layout() default 0;

    /**
     * actionBar资源布局,或用于其他资源布局
     * @return
     */
    int actionBar() default 0;

}
