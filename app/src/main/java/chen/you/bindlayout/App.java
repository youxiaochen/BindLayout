package chen.you.bindlayout;

import android.app.Application;

import chen.you.ant.layoutres.LayoutResIds;

/**
 * Created by Max on 2018/1/3.
 */


public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LayoutResIds.bind();
    }

}
