package pe.limates.unmsm.ui.details_activity.TabFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.HashMap;

import pe.limates.unmsm.R;
import pe.limates.unmsm.model.Image;
import pe.limates.unmsm.model.Place;

/**
 * Created by josan on 20/10/2017.
 */

public class DescriptionFragment extends Fragment {
    private SliderLayout mSlider;
    private Context mContext;
    private Place mPlace;
    private TextView description, address, phone;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_description,container,false);
        mContext = getActivity();
        mPlace = (Place) getArguments().getSerializable("place");
        if(mPlace != null) initializeViews(view);
        return view;
    }
    private void initializeViews(View view){
        try{
            mSlider = view.findViewById(R.id.product_slider);
            description = view.findViewById(R.id.details_description_textview);
            address = view.findViewById(R.id.details_location_textview);
            phone = view.findViewById(R.id.details_phone_textview);

            description.setText(mPlace.getDescription());
            address.setText(mPlace.getAddress());
            phone.setText(mPlace.getPhone());

            for (Image image: mPlace.getImages()) {
                TextSliderView textSliderView = new TextSliderView(mContext);
                // initialize a SliderLayout
                textSliderView
                        .description(image.getName())
                        .image(image.getUrl())
                        .setScaleType(BaseSliderView.ScaleType.CenterCrop);

                //add your extra information
                textSliderView.bundle(new Bundle());
                textSliderView.getBundle()
                        .putString("extra",image.getName());

                mSlider.addSlider(textSliderView);
            }
            mSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
            mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            mSlider.setCustomAnimation(new DescriptionAnimation());
            mSlider.setDuration(4000);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
