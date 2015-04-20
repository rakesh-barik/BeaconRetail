package com.tavant.beaconretail.net;

import com.tavant.beaconretail.model.Offer;
import com.tavant.beaconretail.model.ProductManager;
import com.tavant.beaconretail.model.Section;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rakesh.barik on 20-04-2015.
 */
public class SectionJsonParser {


    private static final String SECTION_ID                  = "sectionId";
    private static final String SECTION_NAME                = "sectionName";
    private static final String SECTION_DESCRIPTION         = "sectionDescription";
    private static final String BEACON_NAME                 = "beaconName";

    private List<Section> sectionList;


    public
    SectionJsonParser(JSONArray response){
        sectionList = new ArrayList<>();
        parseJsonArray(response);
    }

    private void parseJsonArray(JSONArray response){
        for(int i = 0; i< response.length();i++){

            try {
                JSONObject jsonObject = response.getJSONObject(i);
                Section section = new Section();
                section.setSectionId(jsonObject.getInt(SECTION_ID));
                section.setSectionName(jsonObject.getString(SECTION_NAME));
                section.setSectionDescription(jsonObject.getString(SECTION_DESCRIPTION));
                section.setBeaconName(jsonObject.getString(BEACON_NAME));
                sectionList.add(section);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        ProductManager.getInstance().setSections(sectionList);
    }
}
