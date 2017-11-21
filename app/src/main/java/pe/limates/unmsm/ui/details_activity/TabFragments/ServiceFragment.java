package pe.limates.unmsm.ui.details_activity.TabFragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import pe.limates.unmsm.R;
import pe.limates.unmsm.model.Place;

/**
 * Created by josan on 20/10/2017.
 */

public class ServiceFragment extends Fragment {

    private TextView description;
    private Place mPlace;
    //private ImageView call;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service, container, false);
        mPlace = (Place) getArguments().getSerializable("place");
        description = view.findViewById(R.id.details_service_description);
        //call = view.findViewById(R.id.details_phone_ic);
        try {
            description.setText(mPlace.getServices());
            /*call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent iCall = new Intent(Intent.ACTION_DIAL);
                    iCall.setData(Uri.parse("tel:958436713"));
                    startActivity(iCall);
                }
            });*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

}