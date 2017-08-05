package gokhaton.com.no_more_blanket_kick.Fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.location.places.PlaceDetectionApi;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.GeolocationApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.LatLng;
import com.google.maps.model.TransitMode;
import com.google.maps.model.TransitRoutingPreference;
import com.google.maps.model.TravelMode;

import org.joda.time.DateTime;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import gokhaton.com.no_more_blanket_kick.DestinationActivity;
import gokhaton.com.no_more_blanket_kick.LocationRegisterActivity;
import gokhaton.com.no_more_blanket_kick.R;
import gokhaton.com.no_more_blanket_kick.adapter.DirectionRouteAdapter;
import gokhaton.com.no_more_blanket_kick.constant.ApiKey;
import gokhaton.com.no_more_blanket_kick.constant.Prefs;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Earl-PC on 2017. 8. 5..
 */

public class TransportFragment extends Fragment {
    ArrayList<DirectionsRoute> directionsRouteList;
    DirectionRouteAdapter directionRouteAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.transport_fragment,
                container, false);

        if (!checkHomeLocationRegistered()) {
            showLocationAlert();
        }
        LatLng currentLocation = getCurrentLocation();
        getLastTransport(currentLocation);

        ListView transportListView = (ListView) rootView.findViewById(R.id.transportListView);

        directionRouteAdapter = new DirectionRouteAdapter(directionsRouteList);

        transportListView.setAdapter(directionRouteAdapter);
        transportListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DirectionsRoute item = (DirectionsRoute) directionRouteAdapter.getItem(position);

                Intent intent = new Intent(getActivity(), DestinationActivity.class);
                //based on item add info to intent
                intent.putExtra("transport", (Serializable) item.legs[0]);

                startActivity(intent);
            }

        });


        return rootView;
    }

    public LatLng getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        PlaceDetectionApi placeDetectionApi;

        GeoApiContext geoApiContext = new GeoApiContext.Builder().apiKey(ApiKey.GoogleLocationAPI).build();
        try {
            LatLng location = GeolocationApi.newRequest(geoApiContext).HomeMobileCountryCode(450).HomeMobileNetworkCode(05).CreatePayload().await().location;
            return location;
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getLastTransport(LatLng origin){


        GeoApiContext geoApiContext = new GeoApiContext.Builder().apiKey(ApiKey.GoogleMapsAPI).build();

        DirectionsResult result =
                null;



        DateTime dt = new DateTime().withHourOfDay(4);
        SharedPreferences pref = getActivity().getSharedPreferences(Prefs.PREF_NAME, MODE_PRIVATE);
        String destination = pref.getString("location","");


        try {
            result = DirectionsApi.newRequest(geoApiContext)
                    .mode(TravelMode.TRANSIT)
                    .transitMode(TransitMode.BUS, TransitMode.SUBWAY)
                    .arrivalTime(dt)
                    .origin(origin)
                    .destination(destination)
                    .transitRoutingPreference(TransitRoutingPreference.LESS_WALKING)
                    .language("KO")
                    .await();

            directionsRouteList = new ArrayList<>(Arrays.asList(result.routes));
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public boolean checkHomeLocationRegistered()
    {
        SharedPreferences pref = getContext().getSharedPreferences(Prefs.PREF_NAME, MODE_PRIVATE);
        String location = pref.getString("location",null);

        return location != null;

    }

    public boolean showLocationAlert(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());

        // 제목셋팅
        alertDialogBuilder.setTitle("위치 정보 등록");

        // AlertDialog 셋팅
        alertDialogBuilder
                .setMessage("위치 정보가 등록되어 있지 않습니다.\n앱을 이용하기 위해서 먼저 위치를 등록해주세요")
                .setCancelable(false)
                .setPositiveButton("등록",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id) {
                                Intent intent = new Intent(getActivity(), LocationRegisterActivity.class);
                                startActivity(intent);
                            }
                        })
                .setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id) {
                                // 다이얼로그를 취소한다
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();

        return true;
    }
}
