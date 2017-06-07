package appname.worksdelight.appname;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.lucasr.twowayview.TwoWayView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by vikas on 21-12-2016.
 */

public class Fragment1 extends Fragment {
    GridView grid_view;
    Global global;
    ArrayList<HashMap<String, String>> image1 = new ArrayList<>();
    DatabaseHandler db;
    ImagesModal imgModel;
    List<ImagesModal> model = new ArrayList<>();
    ArrayList<Integer> imgValue = new ArrayList<>();
    RecentAdapter apapt;
    RelativeLayout recent_layout;
    Dialog dialog2;
    TextView first_recent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_activity1, container, false);
        global = (Global) getActivity().getApplicationContext();
        db = new DatabaseHandler(getActivity());
        imgModel = new ImagesModal();
        recent_layout = (RelativeLayout) rootView.findViewById(R.id.recent_layout);
        grid_view = (GridView) rootView.findViewById(R.id.grid_view);
        first_recent=(TextView)rootView.findViewById(R.id.first_recent);
        Typeface face = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/Android Insomnia Regular.ttf");
        first_recent.setTypeface(face);
        if (db.getContactsCount() == 0) {
            recent_layout.setVisibility(View.VISIBLE);
        } else {
            recent_layout.setVisibility(View.GONE);
            if (image1.size() == 0) {
                model = db.getAllContacts();


                for (ImagesModal cn : model) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("Id", String.valueOf(cn.get_id()));
                    map.put("img_name", String.valueOf(cn.getImage()));

                    image1.add(map);

                }
                for (int k = 0; k < image1.size(); k++) {
                    imgValue.add(Integer.parseInt(image1.get(k).get("img_name")));
                }
                Collections.sort(imgValue, Collections.reverseOrder());
                apapt = new RecentAdapter(getActivity(), imgValue);
                grid_view.setAdapter(apapt);

            } else {
                if (db.getContactsCount() == image1.size()) {
                    Collections.sort(imgValue, Collections.reverseOrder());
                    grid_view.setAdapter(new RecentAdapter(getActivity(), imgValue));
                } else {
                    image1.clear();

                    apapt.clearData();
                    apapt.notifyDataSetChanged();
                    model = db.getAllContacts();


                    for (ImagesModal cn : model) {
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("Id", String.valueOf(cn.get_id()));
                        map.put("img_name", String.valueOf(cn.getImage()));

                        image1.add(map);

                    }
                    for (int k = 0; k < image1.size(); k++) {
                        imgValue.add(Integer.parseInt(image1.get(k).get("img_name")));
                    }
                    Collections.sort(imgValue, Collections.reverseOrder());
                    apapt = new RecentAdapter(getActivity(), imgValue);
                    grid_view.setAdapter(apapt);
                }
            }
        }

        grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              /*  global.setImg(imgValue.get(i));
               Intent intent = new Intent(getActivity(), FullImageShare.class);
                startActivity(intent);*/
                //dialogWindow();
            }
        });
        return rootView;
    }

    public void dialogWindow() {
        dialog2 = new Dialog(getActivity());
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);

        WindowManager.LayoutParams lp = dialog2.getWindow().getAttributes();
        lp.dimAmount=0.4f;
        dialog2.getWindow().setAttributes(lp);
        dialog2.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        dialog2.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);
        dialog2.setCanceledOnTouchOutside(false);
        dialog2.setCancelable(false);
        dialog2.setContentView(R.layout.full_image_layout);

        ImageView full_img;

        DatabaseHandler db;
        TwoWayView viewList;
        db = new DatabaseHandler(getActivity());
        full_img = (ImageView) dialog2.findViewById(R.id.full_img);

        full_img.setImageResource(global.getImg());
        viewList = (TwoWayView) dialog2.findViewById(R.id.viewList);
        Intent sendIntent = new Intent(android.content.Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        List activities = getActivity().getPackageManager().queryIntentActivities(sendIntent, 0);

        viewList.setAdapter(new ShareAdapter(getActivity(), activities.toArray()));

        // progress_dialog=ProgressDialog.show(LoginActivity.this,"","Loading...");
        dialog2.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (db.getContactsCount() == image1.size()) {
            grid_view.setAdapter(new RecentAdapter(getActivity(), imgValue));
        } else {
            image1.clear();

            apapt.clearData();
            apapt.notifyDataSetChanged();
            model = db.getAllContacts();


            for (ImagesModal cn : model) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("Id", String.valueOf(cn.get_id()));
                map.put("img_name", String.valueOf(cn.getImage()));

                image1.add(map);

            }
            for (int k = 0; k < image1.size(); k++) {
                imgValue.add(Integer.parseInt(image1.get(k).get("img_name")));
            }
            apapt = new RecentAdapter(getActivity(), imgValue);
            grid_view.setAdapter(apapt);
        }
    }
    public class RecentAdapter extends BaseAdapter {
        Context c;
        LayoutInflater inflate;
        ArrayList<Integer> img=new ArrayList<>();
        Holder holder;

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
                holder.stic_img.setTag(holder);

            } else {
                holder = (Holder) view.getTag();
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
                    notifyDataSetChanged();
                    grid_view.setAdapter(apapt);

                }
            });
            return view;
        }

        public class Holder {
            ImageView stic_img;
            GifImageView img;
        }
    }


}
