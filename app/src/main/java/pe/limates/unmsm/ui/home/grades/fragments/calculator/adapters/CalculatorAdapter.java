package pe.limates.unmsm.ui.home.grades.fragments.calculator.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.nio.DoubleBuffer;
import java.util.ArrayList;

import pe.limates.unmsm.R;
import pe.limates.unmsm.model.Calculator;
import pe.limates.unmsm.util.InputFilterMinMax;
import pe.limates.unmsm.util.retrofit.RetrofitBuilder;


public class CalculatorAdapter extends RecyclerView.Adapter<CalculatorAdapter.CustomViewHolder> {

    private final String TAG = CalculatorAdapter.this.getClass().getSimpleName();
    private Context mContext;
    private View view;
    private ArrayList<Calculator> arrayList;
    private RetrofitBuilder connection;


    public CalculatorAdapter(Context mContext, ArrayList<Calculator> array) {
        this.mContext = mContext;
        this.arrayList = array;
        connection = new RetrofitBuilder(this.mContext, TAG, this.mContext.getString(R.string.BASE_URL));
    }

    @Override
    public CalculatorAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mContext).inflate(R.layout.item_weight_grade, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CalculatorAdapter.CustomViewHolder holder, final int position) {
        try {
            final Calculator currentCalculatorItem = arrayList.get(position);

            holder.nameView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    try {
                        currentCalculatorItem.setItemDesc(editable.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            holder.weightView.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
            holder.weightView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    try {
                        currentCalculatorItem.setItemWeight(Double.parseDouble(editable.toString()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            holder.gradeView.setFilters(new InputFilter[]{new InputFilterMinMax("0.0", "20.0")});
            holder.gradeView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    try {
                        currentCalculatorItem.setItemGrade(Double.parseDouble(editable.toString()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            holder.deleteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    arrayList.remove(position);
                    notifyDataSetChanged();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        private EditText nameView, weightView, gradeView;
        private Button deleteItem;

        private CustomViewHolder(View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.item_description);
            weightView = itemView.findViewById(R.id.item_weight);
            gradeView = itemView.findViewById(R.id.item_grade);
            deleteItem = itemView.findViewById(R.id.delete_item);
        }

    }
}
