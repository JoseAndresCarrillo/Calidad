package pe.limates.unmsm.ui.home.grades.fragments.calculator.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pe.limates.unmsm.R;
import pe.limates.unmsm.model.Calculator;
import pe.limates.unmsm.util.InputFilterMinMax;

/**
 * Created by cristinacaballerohervias on 29/09/17.
 */

public class CalculatorAdapter extends ArrayAdapter<Calculator> {

    private EditText gradeTextView;

    public CalculatorAdapter(Context context, ArrayList<Calculator> calculators) {
        super(context, 0, calculators);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View gradesListView = convertView;
        if (gradesListView == null) {
            gradesListView = LayoutInflater.from(getContext()).inflate(R.layout.item_weight_grade, parent, false);
        }

        final Calculator currentCalculatorItem = getItem(position);

        TextView descTextView = gradesListView.findViewById(R.id.item_description);
        descTextView.setText(currentCalculatorItem.getItemDescId());

        String percentage = getContext().getResources().getString(currentCalculatorItem.getItemWeightId()) + getContext().getResources().getString(R.string.perc);
        TextView weightTextView = gradesListView.findViewById(R.id.item_weight);
        weightTextView.setText(percentage);
        gradeTextView = gradesListView.findViewById(R.id.item_grade);
        gradeTextView.setFilters(new InputFilter[]{new InputFilterMinMax("0.0", "20.0")});

        gradeTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                /*Log.v("gg", charSequence.toString());
                try {
                    Log.v("gg", charSequence.toString());
                    if (Double.parseDouble(charSequence.toString()) < 0.0 || Double.parseDouble(charSequence.toString()) > 20.0) {
                        Log.v("gg", "Ya valió we");
                        gradeTextView.setError("ggs");
                    }
                    currentCalculatorItem.setItemGrade(Double.parseDouble(charSequence.toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    if (Double.parseDouble(editable.toString()) < 0.0 || Double.parseDouble(editable.toString()) > 20.0) {
                        gradeTextView.setError("No válido");
                    }
                    currentCalculatorItem.setItemGrade(Double.parseDouble(editable.toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return gradesListView;

    }

}
