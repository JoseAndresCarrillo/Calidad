package pe.limates.unmsm.ui.home.grades.fragments.calculator;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pe.limates.unmsm.R;
import pe.limates.unmsm.model.Calculator;
import pe.limates.unmsm.ui.home.grades.fragments.calculator.adapters.CalculatorAdapter;

public class CalculatorFragment extends Fragment implements CalculatorContractor.View {

    private CalculatorContractor.Presenter presenter;
    private Context mContext;
    private TextView promTextView;
    private final String TAG = CalculatorFragment.class.getSimpleName();
    private Button addItem, calculateButton;

    private ArrayList<Calculator> calculators;
    private RecyclerView mRecycler;
    private CalculatorAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.nav_grades);

        View rootView = inflater.inflate(R.layout.fragment_calculator, container, false);
        mContext = getActivity();
        if (presenter == null) {
            presenter = new CalculatorPresenter(mContext);
        }

        initializeViews(rootView);
        calculators = new ArrayList<>();

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculators.add(new Calculator());
                mAdapter.notifyDataSetChanged();
            }
        });

        mAdapter = new CalculatorAdapter(mContext, calculators);
        mRecycler.setAdapter(mAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        ((SimpleItemAnimator) mRecycler.getItemAnimator()).setSupportsChangeAnimations(false);
        mRecycler.setLayoutManager(mLayoutManager);
        mAdapter.notifyDataSetChanged();

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double finalProm = 0.0;
                Double weightSum = 0.0;
                for (int i = 0; i < calculators.size(); i++) {
                    if ((calculators.get(i).getItemGrade() != null) && (calculators.get(i).getItemWeight() != null)) {
                        finalProm = finalProm + calculators.get(i).getItemGrade() * calculators.get(i).getItemWeight();
                        weightSum = weightSum + calculators.get(i).getItemWeight();
                    }
                }
                finalProm = finalProm / weightSum;
                promTextView.setText(String.format("%.2f", finalProm));

            }
        });

        return rootView;
    }

    private void initializeViews(View view) {
        promTextView = view.findViewById(R.id.final_prom);
        calculateButton = view.findViewById(R.id.button_calculate);
        mRecycler = view.findViewById(R.id.list_weight_grades);
        addItem = view.findViewById(R.id.add_item);
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
