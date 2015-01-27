package mines.ales.agenda.emagenda.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import mines.ales.agenda.emagenda.R;


public class AdapterNavDrawerList extends BaseAdapter {


    private Context context;
    private List<NavDrawerItem> navDrawerItems;

    public AdapterNavDrawerList(Context context,
                                List<NavDrawerItem> navDrawerItems) {
        this.context = context;
        this.navDrawerItems = navDrawerItems;
    }

    @Override
    public int getCount() {
        if (navDrawerItems == null) return 0;
        return navDrawerItems.size();
    }

    @Override
    public Object getItem(int position) {
        return navDrawerItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NavDrawerItem navItem = navDrawerItems.get(position);
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_drawer_item, parent, false);
        }
        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
        TextView txtCount = (TextView) convertView.findViewById(R.id.counter);
        if (navItem.getIcon() != NavDrawerItem.NO_ICON) {
            imgIcon.setImageResource(navItem.getIcon());
        } else {
            imgIcon.setVisibility(View.INVISIBLE);
        }
        txtTitle.setText(navItem.getTitle());
        if (navItem.getCounterVisibility()) {
            txtCount.setText(navItem.getCount());
        } else {
            txtCount.setVisibility(View.GONE);
        }
        return convertView;
    }
}