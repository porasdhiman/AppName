package appname.worksdelight.appname;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.lucasr.twowayview.TwoWayView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by vikas on 21-12-2016.
 */

public class FullImageShare extends Activity {
    ImageView full_img;
    Global global;
    DatabaseHandler db;
    TwoWayView viewList;
    ImageView cancel;
TextView share_txt;
    RelativeLayout full_view_layout;
    GifImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.full_image_layout);


        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.topbar));
        }*/
        global=(Global)getApplicationContext();
        db=new DatabaseHandler(this);
        full_view_layout=(RelativeLayout)findViewById(R.id.full_view_layout);
        share_txt=(TextView)findViewById(R.id.share_txt);
        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/Android Insomnia Regular.ttf");
        share_txt.setTypeface(face);
        full_img=(ImageView)findViewById(R.id.full_img);
        cancel=(ImageView)findViewById(R.id.cancel);
        img=(GifImageView)findViewById(R.id.img);


        TypedValue value = new TypedValue();
        getResources().getValue(global.getImg(), value, true);
        String resname = value.string.toString();

        if(resname.contains(".gif")){
            full_img.setVisibility(View.GONE);
            img.setVisibility(View.VISIBLE);
            try {
                GifDrawable gifFromResource = new GifDrawable(getResources(), global.getImg() );
                img.setImageDrawable(gifFromResource);
               // global.setBmp(loadBitmapFromView(img));
            } catch (IOException e) {
                e.printStackTrace();
            }
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File file = new File(extStorageDirectory, "Hqemoji.gif");

            try {
                byte[] readData = new byte[1024*500];


                InputStream fis = getResources().openRawResource(global.getImg());
                FileOutputStream fos = new FileOutputStream(file);
                int i = fis.read(readData);

                while (i != -1) {
                    fos.write(readData, 0, i);
                    i = fis.read(readData);
                }

                fos.close();
            } catch (IOException io) {
            }
            global.setF(file);
        }else{
            full_img.setVisibility(View.VISIBLE);
            img.setVisibility(View.GONE);
            full_img.setImageResource(global.getImg());
            global.setBmp(loadBitmapFromView(full_img));
        }





        viewList=(TwoWayView)findViewById(R.id.viewList);
        Intent sendIntent = new Intent(android.content.Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        List activities = FullImageShare.this.getPackageManager().queryIntentActivities(sendIntent, 0);

        viewList.setAdapter(new ShareAdapter(FullImageShare.this,activities.toArray()));
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
       // Bitmap image=((BitmapDrawable)full_img.getDrawable()).getBitmap();


    }
    public static Bitmap loadBitmapFromView(View v) {
        if (v.getMeasuredHeight() <= 0) {
            v.measure(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            Bitmap b = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(b);
            v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
            v.draw(c);
            return b;
        }
        return null;
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        finish();
    }
}


