package pe.limates.unmsm.ui.home.grades;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import pe.limates.unmsm.R;
import pe.limates.unmsm.model.Grade;
import pe.limates.unmsm.ui.home.grades.adapters.GradesAdapter;
import pe.limates.unmsm.ui.home.grades.fragments.calculator.CalculatorFragment;

public class GradesFragment extends Fragment implements GradesContractor.View{

    private GradesContractor.Presenter presenter;
    private Context mContext;
    private final String TAG = GradesFragment.class.getSimpleName();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        View rootView = inflater.inflate(R.layout.fragment_grades, container, false);
        mContext = getActivity();
        //initilize presenter
        if (presenter == null) {
            presenter = new GradesPresenter(mContext);
        }
        final ArrayList<Grade> grades = new ArrayList<Grade>();
        grades.add(new Grade(R.string.course1, R.string.grade1));
        grades.add(new Grade(R.string.course2, R.string.grade2));
        grades.add(new Grade(R.string.course3, R.string.grade3));
        grades.add(new Grade(R.string.course4, R.string.grade4));
        grades.add(new Grade(R.string.course5, R.string.grade5));

        // Create the adapter to convert the array to views
        GradesAdapter adapter = new GradesAdapter(mContext, grades);
        // Attach the adapter to a ListView
        ListView cardListView = rootView.findViewById(R.id.list_grades);

        cardListView.setAdapter(adapter);

        cardListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {
                CalculatorFragment nextFrag= new CalculatorFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.left_in, 0, R.anim.left_in_back, R.anim.right_out_back);
                ft.replace(R.id.content_layout, nextFrag, "Calculator").
                        addToBackStack(null)
                        .commit();
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().onViewAttached(GradesFragment.this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPresenter().onViewDettached();
    }


    private GradesContractor.Presenter getPresenter(){
        return presenter;
    }
}
