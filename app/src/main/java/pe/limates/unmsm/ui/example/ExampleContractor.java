package pe.limates.unmsm.ui.example;


public class ExampleContractor {
    interface View {
        //methods here...
    }
    interface Presenter {
        //methods here...
        void onViewDettached();
        void onViewAttached(ExampleContractor.View view);
    }
}
