package com.example.iitbhustudentsapp.adapters;

import android.content.Context;
import android.content.res.Resources;

import com.example.iitbhustudentsapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CouncilListDataPump {
    public static HashMap<String, List<String>> getData(Context context) {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();
        Resources res = context.getResources();

        List<String> cultural = Arrays.asList(res.getStringArray(R.array.cultural_council));
        List<String> sntc = Arrays.asList(res.getStringArray(R.array.science_and_technology_council));
        List<String> fmc = Arrays.asList(res.getStringArray(R.array.film_and_media_council));
        List<String> sportsCouncil = Arrays.asList(res.getStringArray(R.array.games_and_sports_council));
        List<String> ssc = Arrays.asList(res.getStringArray(R.array.social_service_council));


        expandableListDetail.put("Social Service Council", ssc);
        expandableListDetail.put("Science and Technology Council", sntc);
        expandableListDetail.put("Games and Sports Council", sportsCouncil);
        expandableListDetail.put("Film and Media Council", fmc);
        expandableListDetail.put("Cultural Council", cultural);

        return expandableListDetail;
    }
}
