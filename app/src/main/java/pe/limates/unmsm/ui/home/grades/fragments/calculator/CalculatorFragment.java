package pe.limates.unmsm.ui.home.grades.fragments.calculator;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import pe.limates.unmsm.R;
import pe.limates.unmsm.model.Calculator;
import pe.limates.unmsm.ui.home.grades.fragments.calculator.adapters.CalculatorAdapter;

public class CalculatorFragment extends Fragment implements CalculatorContractor.View {

    private CalculatorContractor.Presenter presenter;
    private Context mContext;
    private TextView promTextView;
    private final String TAG = CalculatorFragment.class.getSimpleName();
    private ArrayList<Calculator> calculators;
    private Button calculateButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        View rootView = inflater.inflate(R.layout.fragment_calculator, container, false);
        mContext = getActivity();
        //initilize presenter
        if (presenter == null) {
            presenter = new CalculatorPresenter(mContext);
        }

        promTextView = rootView.findViewById(R.id.final_prom);
        calculateButton = rootView.findViewById(R.id.button_calculate);

        calculators = new ArrayList<>();
        calculators.add(new Calculator(R.string.prom_lab, R.string.w2));
        calculators.add(new Calculator(R.string.prom_prac, R.string.w2));
        calculators.add(new Calculator(R.string.ep, R.string.w1));
        calculators.add(new Calculator(R.string.ef, R.string.w1));

        CalculatorAdapter adapter = new CalculatorAdapter(mContext, calculators);
        ListView listView = rootView.findViewById(R.id.list_weight_grades);
        listView.setAdapter(adapter);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double finalProm = 0.0;
                for (int i = 0; i < calculators.size(); i++) {
                    if (calculators.get(i).getItemGrade() != null) {
                        finalProm = finalProm + calculators.get(i).getItemGrade()
                                * Integer.parseInt(getString(calculators.get(i).getItemWeightId())) / 100;
                    }
                }
                promTextView.setText(String.format("%.2f", finalProm));

            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().onViewAttached(CalculatorFragment.this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPresenter().onViewDettached();
    }

    private CalculatorContractor.Presenter getPresenter() {
        return presenter;
    }
}
