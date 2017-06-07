package appname.worksdelight.appname;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * Created by vikas on 22-12-2016.
 */

public class ShareAdapter extends BaseAdapter {
    Object[] items;
    private LayoutInflater mInflater;
    Context context;
    DatabaseHandler db;
    Global global;
    Uri imgBitmapUri;
    String type;

    public ShareAdapter(Context context, Object[] items) {
        this.mInflater = LayoutInflater.from(context);
        this.items = items;
        this.context = context;
        db = new DatabaseHandler(context);
        global = (Global) context.getApplicationContext();
    }

    public int getCount() {
        return items.length;
    }

    public Object getItem(int position) {
        return items[position];
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.singleitem, null);
            holder = new ViewHolder();
            holder.logo = (ImageView) convertView.findViewById(R.id.imageView1);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.logo
                .setImageDrawable(((ResolveInfo) items[position]).activityInfo.applicationInfo
                        .loadIcon(context.getPackageManager()));
        holder.logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String package_name = ((ResolveInfo) items[position]).activityInfo.applicationInfo.packageName;
                Log.e("name", package_name);

                TypedValue value = new TypedValue();
                context.getResources().getValue(global.getImg(), value, true);
                String resname = value.string.toString();

                if (resname.contains(".gif")) {
                    // Bitmap bm = BitmapFactory.decodeResource(context.getResources(), global.getImg());

                    imgBitmapUri = Uri.fromFile(global.getF());

                    type = "image/gif";
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setPackage(package_name);
                    i.setType(type);
                    i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    //i.putExtra(android.content.Intent.EXTRA_SUBJECT,
                    //   "Install this app and used refer code=1234567890");
                    i.putExtra(Intent.EXTRA_STREAM, imgBitmapUri);


                    context.startActivity(Intent.createChooser(i,
                            "Share"));

                } else {
                    String imgBitmapPath = MediaStore.Images.Media.insertImage(context.getContentResolver(), global.getBmp(), "title", null);
                    imgBitmapUri = Uri.parse(imgBitmapPath);
                    type = "image/png";
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setPackage(package_name);
                    i.setType(type);
                    i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    //i.putExtra(android.content.Intent.EXTRA_SUBJECT,
                    //   "Install this app and used refer code=1234567890");
                    i.putExtra(Intent.EXTRA_STREAM, imgBitmapUri);


                    context.startActivity(Intent.createChooser(i,
                            "Share"));
                }

                //  Uri imageUri = Uri.parse("android.resource://" + getPackageName() + "/drawable/" + "noti_icon");


                if (db.hasObject(String.valueOf(global.getImg()))) {

                    db.deleteContact(new ImagesModal(global.getImg()));
                    db.addImages1(new ImagesModal(global.getImg()));
                } else {
                    db.addImages1(new ImagesModal(global.getImg()));
                    Log.e("count", String.valueOf(db.getContactsCount()));
                }


            }
        });

        return convertView;
    }

    static class ViewHolder {


        ImageView logo;
    }

    public static final Uri getUriToResource(@NonNull Context context,
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
}
