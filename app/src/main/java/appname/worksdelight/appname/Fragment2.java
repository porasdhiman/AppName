package appname.worksdelight.appname;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by vikas on 21-12-2016.
 */

public class Fragment2 extends Fragment {
    int[] image1 = {R.drawable.a_1, R.drawable.a_2, R.drawable.a_3, R.drawable.a_4, R.drawable.a_5, R.drawable.a_6, R.drawable.a_7};
    GridView grid_one;
    Global global;
    DatabaseHandler db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_activity2, container, false);
        global = (Global) getActivity().getApplicationContext();
        db = new DatabaseHandler(getActivity());
        grid_one = (GridView) rootView.findViewById(R.id.grid_one);
        grid_one.setAdapter(new ImageAdapter(getActivity(), image1));
        grid_one.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                /*Intent intent=new Intent(getActivity(),FullImageShare.class);
                startActivity(intent);*/

            }
        });
        return rootView;
    }
    public class ImageAdapter extends BaseAdapter {
        Context c;
        LayoutInflater inflate;
        int[] img;
        Holder holder;

        ImageAdapter(Context c, int[] img) {
            Log.e("image name",img.toString());
            this.c = c;
            this.img = img;
            inflate = LayoutInflater.from(c);
        }

        @Override
        public int getCount() {
            return img.length;
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
        public View getView(final int i, View view, ViewGroup viewGroup) {
            holder = new Holder();
            if (view == null) {


                view = inflate.inflate(R.layout.list_item_layout, null);
                holder.stic_img = (ImageView) view.findViewById(R.id.stic_img);


                holder.img = (GifImageView) view.findViewById(R.id.img);
                view.setTag(holder);
                holder.stic_img.setTag(holder);


            } else {
                holder = (Holder) view.getTag();
            }

            TypedValue value = new TypedValue();
            c.getResources().getValue(img[i], value, true);
            String resname = value.string.toString();

            if(resname.contains(".gif")){
                holder.stic_img.setVisibility(View.GONE);
                holder.img.setVisibility(View.VISIBLE);
                try {
                    GifDrawable gifFromResource = new GifDrawable(c.getResources(), img[i] );
                    holder.img.setImageDrawable(gifFromResource);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                holder.stic_img.setVisibility(View.VISIBLE);
                holder.img.setVisibility(View.GONE);
                holder.stic_img.setImageResource(img[i]);

            }
            holder.stic_img.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    holder=(Holder)view.getTag();
               /* Uri imageUri = null;
                try {
                    imageUri = Uri.parse(MediaStore.Images.Media.insertImage(c.getContentResolver(),
                            BitmapFactory.decodeResource(c.getResources(), img[i]), null, null));
                } catch (NullPointerException e) {
                }
                Bitmap bit = null;
                try {
                    bit=MediaStore.Images.Media.getBitmap(c.getContentResolver(), imageUri);

                } catch (IOException e) {
                    e.printStackTrace();
                }
*/
                    holder.stic_img.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    Bitmap b = Bitmap.createBitmap(holder.stic_img.getMeasuredWidth(), holder.stic_img.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
                    Canvas can = new Canvas(b);
                    holder.stic_img.layout(0, 0, holder.stic_img.getMeasuredWidth(), holder.stic_img.getMeasuredHeight());

                    holder.stic_img.draw(can);
                    // Log.e("bitmap",b.toString());
                    String imgBitmapPath = MediaStore.Images.Media.insertImage(c.getContentResolver(), b, "title", null);

                    Uri imgBitmapUri = Uri.parse(imgBitmapPath);

                    Intent j = new Intent(Intent.ACTION_SEND);

                    j.setType("image/png");
                    j.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    //i.putExtra(android.content.Intent.EXTRA_SUBJECT,
                    //   "Install this app and used refer code=1234567890");
                    j.putExtra(Intent.EXTRA_STREAM, imgBitmapUri);


                    c.startActivity(Intent.createChooser(j,
                            "Share"));
                    if (db.hasObject(String.valueOf(img[i]))) {

                        db.deleteContact(new ImagesModal(img[i]));
                        db.addImages1(new ImagesModal(img[i]));
                    } else {
                        db.addImages1(new ImagesModal(img[i]));
                        Log.e("count", String.valueOf(db.getContactsCount()));
                    }
                    notifyDataSetChanged();
                    grid_one.setAdapter(new ImageAdapter(getActivity(), image1));

                }
            });
            return view;
        }

        public class Holder {
            ImageView stic_img;

            GifImageView img;
        }
        public  final Uri getUriToResource(@NonNull Context context,
                                                 @AnyRes int resId)
                throws Resources.NotFoundException {
            /** Return a Resources instance for your application's package. */
            Resources res = context.getResources();
            /**
             * Creates a Uri which parses the given encoded URI string.
             * @param uriString an RFC 2396-compliant, encoded URI
             * @throws NullPointerException if uriString is null
             * @return Uri for this given uri string
             */
            Uri resUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                    "://" + res.getResourcePackageName(resId)
                    + '/' + res.getResourceTypeName(resId)
                    + '/' + res.getResourceEntryName(resId)
                    + '/' + res.getResourceName(resId));
            /** return uri */
            return resUri;
        }
        public  Bitmap loadBitmapFromView(View v) {
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
    }
}
