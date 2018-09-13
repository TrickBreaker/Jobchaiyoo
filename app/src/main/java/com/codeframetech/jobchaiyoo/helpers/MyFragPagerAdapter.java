package  com.codeframetech.jobchaiyoo.helpers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by Digition on 7/13/2017.
 */
public class MyFragPagerAdapter extends FragmentStatePagerAdapter {
    ArrayList<Fragment> pages = new ArrayList<>();

    public MyFragPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return pages.get(position);

    }

    @Override
    public int getCount() {
        return pages.size();
    }
    //ADD A PAGE
    public void addPage(Fragment f){
        pages.add(f);
    }

    @Override
    public  int getItemPosition(Object object){
        return POSITION_NONE;
    }
    //SET TITLE FOR TAB

    @Override
    public CharSequence getPageTitle(int position) {
        return pages.get(position).toString();
    }
}
