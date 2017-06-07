package appname.worksdelight.appname;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.io.IOException;
import java.util.ArrayList;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by vikas on 22-12-2016.
 */

public class RecentAdapter extends BaseAdapter {
    Context c;
    LayoutInflater inflate;
    ArrayList<Integer> img=new ArrayList<>();
    RecentAdapter.Holder holder;

    RecentAdapter(Context c,  ArrayList<Integer> img) {
        this.c = c;
        this.img = img;
        inflate = LayoutInflater.from(c);
    }
    public void clearData() {
        // clear the data
        img.clear();
    }
    @Override
    public int getCount() {
        return img.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        holder = new RecentAdapter.Holder();
        if (view == null) {


            view = inflate.inflate(R.layout.list_item_layout, null);
            holder.stic_img = (ImageView) view.findViewById(R.id.stic_img);

            holder.img = (GifImageView) view.findViewById(R.id.img);
            view.setTag(holder);


        } else {
            holder = (RecentAdapter.Holder) view.getTag();
        }
        TypedValue value = new TypedValue();
        c.getResources().getValue(img.get(i), value, true);
        String resname = value.string.toString();

        if(resname.contains(".gif")){
            holder.stic_img.setVisibility(View.GONE);
            holder.img.setVisibility(View.VISIBLE);
            try {
                GifDrawable gifFromResource = new GifDrawable(c.getResources(), img.get(i) );
                holder.img.setImageDrawable(gifFromResource);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            holder.stic_img.setVisibility(View.VISIBLE);
            holder.img.setVisibility(View.GONE);
            holder.stic_img.setImageResource(img.get(i));

        }

        return view;
    }

    public class Holder {
        ImageView stic_img;
        GifImageView img;
    }
}
