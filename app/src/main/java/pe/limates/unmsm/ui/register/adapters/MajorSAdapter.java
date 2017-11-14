package pe.limates.unmsm.ui.register.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import pe.limates.unmsm.R;
import pe.limates.unmsm.model.Major;

public class MajorSAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Major> majors;

    public MajorSAdapter(Context mContext, ArrayList<Major> majors) {
        this.mContext = mContext;
        this.majors = majors;
    }

    @Override
    public int getCount() {
        return majors.size();
    }

    @Override
    public long getItemId(int i) {
        return majors.get(i).getId();
    }

    @Override
    public Major getItem(int i) {
        return majors.get(i);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View row = LayoutInflater.from(mContext).inflate(R.layout.spinner_row, null);
        TextView spinnerItem = row.findViewById(R.id.spinnerItem);
        spinnerItem.setText(majors.get(i).getName());
        return row;
    }
}
