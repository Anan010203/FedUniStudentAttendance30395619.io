package com.example.fedunistudentattendance30395619.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fedunistudentattendance30395619.R;
import com.example.fedunistudentattendance30395619.bean.Classbean;
import com.example.fedunistudentattendance30395619.bean.StudentBean;
import com.example.fedunistudentattendance30395619.dao.StuDao;

import java.util.List;

public class StuListAdapter extends BaseAdapter {
    private List<StudentBean> studentBeans;

    private String state = "1";
    private Context context;
    private StuDao stuDao;

    public StuListAdapter(Context context, List<StudentBean> studentBeans,String state) {
        this.studentBeans = studentBeans;
        this.context = context;
        this.state =state;
    }

    @Override
    public int getCount() {
        return studentBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return studentBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.stu_item,null);

        TextView tv_id = view.findViewById(R.id.tv_id);
        TextView tv_name = view.findViewById(R.id.tv_name);
        CheckBox cb_isattend =view.findViewById(R.id.cb_isattend);

        tv_id.setText( studentBeans.get(position).getStu_id());
        tv_name.setText(studentBeans.get(position).getStu_name());
        if (studentBeans.get(position).getMsg().contains(state)){
            cb_isattend.setChecked(true);
        }
        cb_isattend.setOnCheckedChangeListener((compoundButton, b) -> {
            stuDao = new StuDao(context);
            StudentBean studentBean = studentBeans.get(position);
            String msg_old = studentBean.getMsg();
            if (b){
                msg_old = msg_old+state;
            }else {
                char firstChar = state.charAt(0);
                msg_old = removeChar(firstChar,msg_old);
            }
            studentBean.setMsg(msg_old);
            stuDao.update(studentBean);

        });
        return view;
    }
    public static String removeChar(char toRemove, String input) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            if (currentChar != toRemove) {
                result.append(currentChar);
            }
        }

        return result.toString();
    }
}
