package com.example.fedunistudentattendance30395619.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.fedunistudentattendance30395619.bean.Classbean;

import java.util.List;
import com.example.fedunistudentattendance30395619.R;
public class ClassListAdapter extends BaseAdapter {
    private List<Classbean> classbeans;
    private Context context;

    public ClassListAdapter(Context context, List<Classbean> classbeans) {
        this.classbeans = classbeans;
        this.context = context;
    }

    @Override
    public int getCount() {
        return classbeans.size();
    }

    @Override
    public Object getItem(int position) {
        return classbeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.item,null);
        TextView title = view.findViewById(R.id.textView);
        TextView time = view.findViewById(R.id.textView1);
        TextView time2 = view.findViewById(R.id.textView2);
        title.setText( classbeans.get(position).getTitle());
        time.setText("end_time："+ classbeans.get(position).getEnd_date());
        time2.setText("start_time："+ classbeans.get(position).getStart_date());
        return view;
    }
}
