package hudlmo.models;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * Created by chashika on 7/26/17.
 */

public class AdapterImages extends BaseAdapter {
    private int[] mImageId;
    Context context;

    public AdapterImages(int[] mImageId, Context context) {
        this.mImageId = mImageId;
        this.context = context;
    }


    @Override
    public int getCount() {
        return mImageId.length;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if(convertView==null){
            imageView = new ImageView(context);
            imageView.setLayoutParams(new ListView.LayoutParams(300,300));

        }
        else{
            imageView =(ImageView) convertView;
        }
        imageView.setImageResource(mImageId[position]);
        return  imageView;

    }
}
