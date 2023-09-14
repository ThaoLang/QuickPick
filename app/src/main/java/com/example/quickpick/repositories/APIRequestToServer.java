package com.example.quickpick.repositories;

import static com.example.quickpick.constants.APIUrl.BASE_URL;
import static com.example.quickpick.constants.APIUrl.GET_COST_URL;
import static com.example.quickpick.constants.APIUrl.GET_DIRECTION_DETAIL_URL;
import static com.example.quickpick.constants.APIUrl.POST_BOOKING_INFO_URL;
import static com.example.quickpick.constants.APIUrl.UPDATE_FCM_TOKEN_URL;

import android.net.Uri;
import android.util.Log;

import com.example.quickpick.api_structure.GetCostRequest;
import com.example.quickpick.api_structure.GetDirectionDetailRequest;
import com.example.quickpick.api_structure.PostBookingInfoRequest;
import com.example.quickpick.api_structure.UpdateFCMTokenRequest;
import com.example.quickpick.constants.ServerHttpRequestMethods;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class APIRequestToServer {

    private static String NGROK_IGNORE_WARNING_KEY="ngrok-skip-browser-warning";
    public static String NGROK_IGNORE_WARNING_VALUE="any value";

    public int getCostRequest(GetCostRequest request)
    {
        String get_cost_URL = BASE_URL.concat(GET_COST_URL);
        URL url = null;
        int responseCode = -1;
        try
        {
            url = new URL(get_cost_URL);
        }
        catch (MalformedURLException malformedURLException)
        {
            Log.e("getCostRequest error", malformedURLException.getMessage());
            return responseCode;
        }

        HttpURLConnection urlConnection = null;

        try
        {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(String.valueOf(ServerHttpRequestMethods.POST));
            urlConnection.setDoOutput(true);
            urlConnection.setChunkedStreamingMode(0);
            urlConnection.setRequestProperty(NGROK_IGNORE_WARNING_KEY, NGROK_IGNORE_WARNING_VALUE);

            Uri.Builder uriBuilder = new Uri.Builder();

            uriBuilder.appendQueryParameter(GetCostRequest.ID_TOKEN, request.getIdToken())
                    .appendQueryParameter(GetCostRequest.SYSTEM_KEY, request.getSystemKey())
                    .appendQueryParameter(GetCostRequest.USER_ID, request.getUserId())
                    .appendQueryParameter(GetCostRequest.START_ADDRESS, request.getStartAddress())
                    .appendQueryParameter(GetCostRequest.END_ADDRESS, request.getEndAddress());
            String query = uriBuilder.build().getEncodedQuery();

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));

            writer.write(query);
            writer.flush();
            System.out.println("after send");

            writer.close();
            responseCode = urlConnection.getResponseCode();
            System.out.println("after receive response");
        }
        catch(IOException ioException)
        {
            Log.e("getCostRequest error", ioException.getMessage());
            return -1;
        }
        finally
        {
            if(urlConnection != null)
            {
                urlConnection.disconnect();
            }
            System.out.println("response code: "+ responseCode);
            return responseCode;
        }

    }


    public int sendBookingInfoRequest(PostBookingInfoRequest request)
    {
        String post_booking_info_URL = BASE_URL.concat(POST_BOOKING_INFO_URL);
        URL url = null;
        int responseCode = -1;
        try
        {
            url = new URL(post_booking_info_URL);
        }
        catch (MalformedURLException malformedURLException)
        {
            Log.e("sendBookingInfoRequest error", malformedURLException.getMessage());
            return responseCode;
        }

        HttpURLConnection urlConnection = null;

        try
        {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(String.valueOf(ServerHttpRequestMethods.POST));
            urlConnection.setDoOutput(true);
            urlConnection.setChunkedStreamingMode(0);
            urlConnection.setRequestProperty(NGROK_IGNORE_WARNING_KEY, NGROK_IGNORE_WARNING_VALUE);

            Uri.Builder uriBuilder = new Uri.Builder();

            uriBuilder.appendQueryParameter(PostBookingInfoRequest.ID_TOKEN, request.getIdToken())
                    .appendQueryParameter(PostBookingInfoRequest.SYSTEM_KEY, request.getSystemKey())
                    .appendQueryParameter(PostBookingInfoRequest.USER_ID, request.getUserId())
                    .appendQueryParameter(PostBookingInfoRequest.START_ADDRESS, request.getStartAddress())
                    .appendQueryParameter(PostBookingInfoRequest.END_ADDRESS, request.getEndAddress())
                    .appendQueryParameter(PostBookingInfoRequest.COST, request.getCost())
                    .appendQueryParameter(PostBookingInfoRequest.DISTANCE, request.getDistance())
                    .appendQueryParameter(PostBookingInfoRequest.PHONE, request.getPhone())
                    .appendQueryParameter(PostBookingInfoRequest.NAME, request.getName())
                    .appendQueryParameter(PostBookingInfoRequest.DURATION, request.getDuration())
                    .appendQueryParameter(PostBookingInfoRequest.END_ADDRESS, request.getEndAddress())
                    .appendQueryParameter(PostBookingInfoRequest.VEHICLE, request.getVehicle());
            String query = uriBuilder.build().getEncodedQuery();

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));

            writer.write(query);
            writer.flush();
            System.out.println("after send");

            writer.close();
            responseCode = urlConnection.getResponseCode();
            System.out.println("after receive response");
        }
        catch(IOException ioException)
        {
            Log.e("sendBookingInfoRequest error", ioException.getMessage());
            return -1;
        }
        finally
        {
            if(urlConnection != null)
            {
                urlConnection.disconnect();
            }
            System.out.println("response code: "+ responseCode);
            return responseCode;
        }

    }

    public int getDrirectDetailRequest(GetDirectionDetailRequest request)
    {
        String get_cost_URL = BASE_URL.concat(GET_DIRECTION_DETAIL_URL);
        URL url = null;
        int responseCode = -1;
        try
        {
            url = new URL(get_cost_URL);
        }
        catch (MalformedURLException malformedURLException)
        {
            Log.e("getDrirectDetailRequest error", malformedURLException.getMessage());
            return responseCode;
        }

        HttpURLConnection urlConnection = null;

        try
        {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(String.valueOf(ServerHttpRequestMethods.POST));
            urlConnection.setDoOutput(true);
            urlConnection.setChunkedStreamingMode(0);
            urlConnection.setRequestProperty(NGROK_IGNORE_WARNING_KEY, NGROK_IGNORE_WARNING_VALUE);

            Uri.Builder uriBuilder = new Uri.Builder();

            uriBuilder.appendQueryParameter(GetCostRequest.ID_TOKEN, request.getIdToken())
                    .appendQueryParameter(GetCostRequest.SYSTEM_KEY, request.getSystemKey())
                    .appendQueryParameter(GetCostRequest.USER_ID, request.getUserId())
                    .appendQueryParameter(GetCostRequest.START_ADDRESS, request.getStartAddress())
                    .appendQueryParameter(GetCostRequest.END_ADDRESS, request.getEndAddress());
            String query = uriBuilder.build().getEncodedQuery();

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));

            writer.write(query);
            writer.flush();
            System.out.println("after send");

            writer.close();
            responseCode = urlConnection.getResponseCode();
            System.out.println("after receive response");
        }
        catch(IOException ioException)
        {
            Log.e("getDrirectDetailRequest error", ioException.getMessage());
            return -1;
        }
        finally
        {
            if(urlConnection != null)
            {
                urlConnection.disconnect();
            }
            System.out.println("response code: "+ responseCode);
            return responseCode;
        }

    }

    public int postUpdateFCMToken(UpdateFCMTokenRequest request)
    {
        String driverUpdateFCM_URL = BASE_URL.concat(UPDATE_FCM_TOKEN_URL);
        URL url = null;
        int responseCode = -1;
        try
        {
            url = new URL(driverUpdateFCM_URL);
        }
        catch (MalformedURLException malformedURLException)
        {
            Log.e("PostUpdateFCMToken error", malformedURLException.getMessage());
            return responseCode;
        }

        HttpURLConnection urlConnection = null;

        try
        {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(String.valueOf(ServerHttpRequestMethods.POST));
            urlConnection.setDoOutput(true);
            urlConnection.setChunkedStreamingMode(0);
            urlConnection.setRequestProperty(NGROK_IGNORE_WARNING_KEY, NGROK_IGNORE_WARNING_VALUE);

            Uri.Builder uriBuilder = new Uri.Builder();

            uriBuilder.appendQueryParameter(UpdateFCMTokenRequest.ID_TOKEN, request.getIdToken())
                    .appendQueryParameter(UpdateFCMTokenRequest.SYSTEM_KEY, request.getSystemKey())
                    .appendQueryParameter(UpdateFCMTokenRequest.USER_ID, request.getUserId())
                    .appendQueryParameter(UpdateFCMTokenRequest.ROLE, request.getRole())
                    .appendQueryParameter(UpdateFCMTokenRequest.FCM_TOKEN, request.getFcm_token());
            String query = uriBuilder.build().getEncodedQuery();

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));

            writer.write(query);
            writer.flush();
            System.out.println("after send");

            writer.close();
            responseCode = urlConnection.getResponseCode();
            System.out.println("after receive response");
        }
        catch(IOException ioException)
        {
            Log.e("PostUpdateFCMToken error", ioException.getMessage());
            return -1;
        }
        finally
        {
            if(urlConnection != null)
            {
                urlConnection.disconnect();
            }
            System.out.println("response code: "+ responseCode);
            return responseCode;
        }

    }

}
