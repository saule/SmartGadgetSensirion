package com.sensirion.smartgadget.view.history;

import android.support.annotation.NonNull;

import com.sensirion.smartgadget.peripheral.rht_utils.RHTDataPoint;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HistoryResult {

    @NonNull
    private final Map<String, List<RHTDataPoint>> mResultValues =
            Collections.synchronizedMap(
                    new HashMap<String, List<RHTDataPoint>>()
            );

    public HistoryResult(@NonNull final List<String> devices) {
        for (final String device : devices) {
            //System.out.println("deviceXXXuuuu: "+device);
            //printList(LinkedList<RHTDataPoint>()));
            mResultValues.put(device, Collections.synchronizedList(new LinkedList<RHTDataPoint>()));
        }
    }

    private void printList(List<RHTDataPoint> mylist){
        System.out.println("=============================================================================");
        for (RHTDataPoint data:mylist){
            System.out.println("dewPoint Celcius = "+data.getDewPointCelsius());
            System.out.print("temp Celc "+data.getTemperatureCelsius());
            System.out.print("heat index Celc "+data.getHeatIndexCelsius());
            System.out.print("humidity % = " + data.getRelativeHumidity());
        }
        System.out.println("XXX=============================================================================");
    }

    /**
     * Obtains the history results.
     *
     * @return Iterable of {@link java.util.List <@link RHTDataPoint>}>} with the results
     */
    @NonNull
    public Map<String, List<RHTDataPoint>> getResults() {
        //System.out.println("deviceXXXuuuu: "+device);
       // printList(Collections.synchronizedList(new LinkedList<RHTDataPoint>()));

       /* for (String deviceName:mResultValues.keySet()){
            String key = deviceName.toString();
            System.out.println("device FFFFFFFFFF: " + key);
            List<RHTDataPoint> dataPoints = mResultValues.get(deviceName);
            printList(dataPoints);
        }*/
        return mResultValues;
    }

    /**
     * Adds a datapoint to the result list.
     *
     * @param dataPoint that is going to be added.
     */
    public void addResult(@NonNull final String deviceAddress,
                          @NonNull final RHTDataPoint dataPoint) {
        mResultValues.get(deviceAddress).add(dataPoint);
    }

    /**
     * Obtains the number of values retrieved by the database.
     *
     * @return <code>int</code> with the number of values.
     */
    public int size() {
        int size = 0;
        synchronized (mResultValues) {
            for (List<RHTDataPoint> results : mResultValues.values()) {
                size += results.size();
            }
        }
        return size;
    }

    @NonNull
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        synchronized (mResultValues) {
            for (final String deviceAddress : mResultValues.keySet()) {
                for (final RHTDataPoint datapoint : mResultValues.get(deviceAddress)) {
                    sb.append(
                            String.format(
                                    "\nDevice with address: %s - %s",
                                    deviceAddress,
                                    datapoint.toString())
                    );
                }
            }
        }
        return sb.toString();
    }
}