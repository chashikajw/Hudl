package layout;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import hudlmo.interfaces.loginpage.R;

/**
 * Created by admin on 12/3/2017.
 */

public class GroupAdapter extends BaseAdapter {

    private List<Meeting> grouplist;
    private Context mContext;

    //constrctor


    public GroupAdapter(List<Meeting> grouplist, Context mContext) {
        this.grouplist = grouplist;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return grouplist.size();
    }

    @Override
    public Object getItem(int position) {
        return grouplist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.fragment_group_textlist,null);
        TextView groupname = (TextView)v.findViewById(R.id.groupname);
        TextView groupdetails = (TextView)v.findViewById(R.id.groupdetails);
        groupname.setText(grouplist.get(position).getGroupname());
        groupdetails.setText(grouplist.get(position).getGroupdtails());

        return v;

    }
}
