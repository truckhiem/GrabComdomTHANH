package com.android.dfr.grabcondom;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Created by MinhThanh on 10/27/16.
 */

public class CustomListHIV extends ArrayAdapter<String> {


    private final Activity context;
    private final String[] array;
    private final Integer[] imageId;
    public CustomListHIV(Activity context,
                      String[] array, Integer[] imageId) {
        super(context, R.layout.item_hivtest, array);
        this.context = context;
        this.array = array;
        this.imageId = imageId;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.item_hivtest, null, true);
//        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

//        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
//        txtTitle.setText(array[position]);

//        imageView.setImageResource(imageId[position]);
        return rowView;
    }
}
