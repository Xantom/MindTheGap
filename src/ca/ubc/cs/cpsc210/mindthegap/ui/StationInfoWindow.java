package ca.ubc.cs.cpsc210.mindthegap.ui;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import ca.ubc.cs.cpsc210.mindthegap.R;
import ca.ubc.cs.cpsc210.mindthegap.model.Line;
import ca.ubc.cs.cpsc210.mindthegap.model.Station;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.bonuspack.overlays.MarkerInfoWindow;
import org.osmdroid.views.MapView;

/**
 * StationInfoWindow displayed when station is tapped
 */
class StationInfoWindow extends MarkerInfoWindow {
    private StationSelectionListener stnSelectionListener;
    private Station firstSelectedStation = null;
    private Station secondSelectedStation = null;
    CheckBox sameLineCheckBox = (CheckBox)mView.findViewById(R.id.sameLineCheckbox);

    /**
     * Constructor
     *
     * @param listener   listener to handle user selection of station
     * @param mapView    the map view on which this info window will be displayed
     */
    public StationInfoWindow(StationSelectionListener listener, MapView mapView) {
        super(R.layout.bonuspack_bubble, mapView);
        stnSelectionListener = listener;

        Button btn = (Button) (mView.findViewById(R.id.bubble_moreinfo));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Station selected = (Station) mMarkerRef.getRelatedObject();
                stnSelectionListener.onStationSelected(selected);
                StationInfoWindow.this.close();
            }
        });
    }

    @Override public void onOpen(Object item){
        super.onOpen(item);
        mView.findViewById(R.id.bubble_moreinfo).setVisibility(View.VISIBLE);
        Marker marker = (Marker) item;
        Station selected = (Station) marker.getRelatedObject();
        Log.d("Open item", "open item");


        if (firstSelectedStation != null && secondSelectedStation == null) {
            secondSelectedStation = selected;

            if (areOnSameLine(firstSelectedStation, secondSelectedStation)) {
                if (sameLineCheckBox.isChecked())
                Toast.makeText(
                        mView.getContext(),
                        "The two stations(" + firstSelectedStation.getName() + ","
                                + secondSelectedStation.getName()
                                + ") are on the same line.", Toast.LENGTH_LONG)
                        .show();
                Log.d("SameLine", "SameLine");
            } else {
                Log.d("SameLine", "NotSameLine");
                Toast.makeText(
                        mView.getContext(),
                        "The two stations(" + firstSelectedStation.getName() + ","
                                + secondSelectedStation.getName()
                                + ") are not on the same line.", Toast.LENGTH_LONG)
                        .show();
            }
            firstSelectedStation = null;
            secondSelectedStation = null;
        }
        if (firstSelectedStation == null) {
            firstSelectedStation = selected;
        }
    }

    private boolean areOnSameLine(Station firstStn, Station secondStn) {
        if (firstStn != null && secondStn != null) {
            for (Line next : firstStn.getLines()) {
                if (next.hasStation(secondStn)) {
                    return true;
                }
            }
        }
        return false;
    }

}


/*      <CheckBox
android:id="@+id/sameLineCheckbox"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Same Line?"
        android:checked="true"
        />

        CheckBox sameLineCheckBox = (CheckBox)mMapView.findViewById(R.id.sameLineCheckbox);
*/

