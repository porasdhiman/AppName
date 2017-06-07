package appname.worksdelight.appname;

import android.app.Application;
import android.graphics.Bitmap;

import java.io.File;

/**
 * Created by vikas on 21-12-2016.
 */

public class Global extends Application {
    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    int img;

    public Bitmap getBmp() {
        return bmp;
    }

    public void setBmp(Bitmap bmp) {
        this.bmp = bmp;
    }

    Bitmap bmp;

    public File getF() {
        return f;
    }

    public void setF(File f) {
        this.f = f;
    }

    File f;
    @Override
    public void onCreate() {
        super.onCreate();

        Font.overrideFont(getApplicationContext(), "SERIF", "fonts/Android Insomnia Regular.ttf");

    }
}
