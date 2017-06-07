package appname.worksdelight.appname;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.astuetz.PagerSlidingTabStrip;


/**
 * Created by qq on 22.06.2015.
 */
public class MainAdapter extends FragmentPagerAdapter
        implements PagerSlidingTabStrip.CustomTabProvider {

    int[] tabs;

    public MainAdapter(FragmentManager fm, int[] tabs) {
        super(fm);
        this.tabs = tabs;
    }

    @Override
    public View getCustomTabView(ViewGroup viewGroup, int i) {
        RelativeLayout tabLayout = (RelativeLayout)
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tab_layout, null);

        ImageView tabTitle = (ImageView) tabLayout.findViewById(R.id.tab_img);
        tabTitle.setImageResource(tabs[i]);



        return tabLayout;
    }

    @Override
    public void tabSelected(View view) {

    }

    @Override
    public void tabUnselected(View view) {

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Fragment1();
            case 1:
                return new Fragment2();
            case 2:
                return new Fragment3();
            case 3:
                return new Fragment4();
            case 4:
                return new Fragment5();

        }
        return new Fragment1();
    }

    @Override
    public int getCount() {
        return tabs.length;
    }
}