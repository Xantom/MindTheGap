package ca.ubc.cs.cpsc210.mindthegap.TfL;

import ca.ubc.cs.cpsc210.mindthegap.model.Line;
import ca.ubc.cs.cpsc210.mindthegap.model.Station;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Wrapper for TfL Arrival Data Provider
 */
public class TfLHttpArrivalDataProvider extends AbstractHttpDataProvider {
    private Station stn;

    public TfLHttpArrivalDataProvider(Station stn) {
        super();
        this.stn = stn;
    }

    @Override
    /**
     * Produces URL used to query TfL web service for expected arrivals at
     * station specified in call to constructor.
     *
     * @returns URL to query TfL web service for arrival data
     */
    protected URL getURL() throws MalformedURLException {
        // TODO Phase 2 Task 7

        String lineids = "";

        for (Line line: stn.getLines()) {
            lineids = lineids + line.getId() + ",";

        }

        String request = "https://api.tfl.gov.uk/Line/" + lineids + "/Arrivals?stopPointId=" + stn.getID();
        return new URL(request);





        /*
        for(Line line : stn.getLines()) {

            String request = "https://api.tfl.gov.uk/Line/" + line.getId() + "/Arrivals?stopPointId=" + stn.getID();
            return new URL(request);

        }
        return null;

    }
    */
    }
}

