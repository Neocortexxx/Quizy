package de.android.philipp.myapplication;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class InteractWithServer {

    public static String SaveUserDataOnServer(final String regId, final String username, final String password) {

        String result = "";
        Map paramsMap = new HashMap();
        paramsMap.put("regID", regId);
        paramsMap.put("username", username);
        paramsMap.put("passwort", password);
        try {
            URL serverUrl = null;
            try {
                serverUrl = new URL(Config.REGISTER_URL);
            } catch (MalformedURLException e) {
                Log.e("AppUtil", "URL Connection Error: "
                        + Config.REGISTER_URL, e);
                result = "Invalid URL: " + Config.REGISTER_URL;
            }

            StringBuilder postBody = new StringBuilder();
            Iterator iterator = paramsMap.entrySet().iterator();

            while (iterator.hasNext()) {
                Entry param = (Entry) iterator.next();
                postBody.append(param.getKey()).append('=')
                        .append(param.getValue());
                if (iterator.hasNext()) {
                    postBody.append('&');
                }
            }
            String body = postBody.toString();
            byte[] bytes = body.getBytes();
            HttpURLConnection httpCon = null;
            try {
                httpCon = (HttpURLConnection) serverUrl.openConnection();
                httpCon.setDoOutput(true);
                httpCon.setUseCaches(false);
                httpCon.setFixedLengthStreamingMode(bytes.length);
                httpCon.setRequestMethod("POST");
                httpCon.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded;charset=UTF-8");
                OutputStream out = httpCon.getOutputStream();
                out.write(bytes);
                out.close();

                int status = httpCon.getResponseCode();
                if (status == 200) {
                    result = "success";
                } else {
                    result = "Post Failure." + " Status: " + status;
                }
            } finally {
                if (httpCon != null) {
                    httpCon.disconnect();
                }
            }

        } catch (IOException e) {
            result = "Post Failure. Error in sharing with App Server.";
            Log.e("AppUtil", "Error in sharing with App Server: " + e);
        }
        return result;
    }

    public static String GruppeErstellen(final String gruppenName, final String username) {

        String result = "";
        Map paramsMap = new HashMap();
        paramsMap.put("groupname", gruppenName);
        paramsMap.put("username", username);
        try {
            URL serverUrl = null;
            try {
                serverUrl = new URL(Config.GROUP_URL);
            } catch (MalformedURLException e) {
                Log.e("AppUtil", "URL Connection Error: "
                        + Config.GROUP_URL, e);
                result = "Invalid URL: " + Config.GROUP_URL;
            }

            StringBuilder postBody = new StringBuilder();
            Iterator iterator = paramsMap.entrySet().iterator();

            while (iterator.hasNext()) {
                Entry param = (Entry) iterator.next();
                postBody.append(param.getKey()).append('=')
                        .append(param.getValue());
                if (iterator.hasNext()) {
                    postBody.append('&');
                }
            }
            String body = postBody.toString();
            byte[] bytes = body.getBytes();
            HttpURLConnection httpCon = null;
            try {
                httpCon = (HttpURLConnection) serverUrl.openConnection();
                httpCon.setDoOutput(true);
                httpCon.setUseCaches(false);
                httpCon.setFixedLengthStreamingMode(bytes.length);
                httpCon.setRequestMethod("POST");
                httpCon.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded;charset=UTF-8");
                OutputStream out = httpCon.getOutputStream();
                out.write(bytes);
                out.close();

                int status = httpCon.getResponseCode();
                if (status == 200) {
                    result = "success";
                } else {
                    result = "Post Failure." + " Status: " + status;
                }
            } finally {
                if (httpCon != null) {
                    httpCon.disconnect();
                }
            }

        } catch (IOException e) {
            result = "Post Failure. Error in sharing with App Server.";
            Log.e("AppUtil", "Error in sharing with App Server: " + e);
        }
        return result;
    }

    public static String InGruppeEinladen(final String gruppenName, final String username, String inviter) {

        String result = "";
        Map paramsMap = new HashMap();
        paramsMap.put("groupname", gruppenName);
        paramsMap.put("username", username);
        paramsMap.put("inviter", inviter);

        try {
            URL serverUrl = null;
            try {
                serverUrl = new URL(Config.INVITE_URL);
            } catch (MalformedURLException e) {
                Log.e("AppUtil", "URL Connection Error: "
                        + Config.INVITE_URL, e);
                result = "Invalid URL: " + Config.INVITE_URL;
            }

            StringBuilder postBody = new StringBuilder();
            Iterator iterator = paramsMap.entrySet().iterator();

            while (iterator.hasNext()) {
                Entry param = (Entry) iterator.next();
                postBody.append(param.getKey()).append('=')
                        .append(param.getValue());
                if (iterator.hasNext()) {
                    postBody.append('&');
                }
            }
            String body = postBody.toString();
            byte[] bytes = body.getBytes();
            HttpURLConnection httpCon = null;
            try {
                httpCon = (HttpURLConnection) serverUrl.openConnection();
                httpCon.setDoOutput(true);
                httpCon.setUseCaches(false);
                httpCon.setFixedLengthStreamingMode(bytes.length);
                httpCon.setRequestMethod("POST");
                httpCon.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded;charset=UTF-8");
                OutputStream out = httpCon.getOutputStream();
                out.write(bytes);
                out.close();

                int status = httpCon.getResponseCode();
                if (status == 200) {
                    result = "success";
                } else {
                    result = "Post Failure." + " Status: " + status;
                }
            } finally {
                if (httpCon != null) {
                    httpCon.disconnect();
                }
            }

        } catch (IOException e) {
            result = "Post Failure. Error in sharing with App Server.";
            Log.e("AppUtil", "Error in sharing with App Server: " + e);
        }
        return result;
    }

    public static boolean CheckUserName(final String username)throws IOException {

        URL url = null;
        StringBuilder quellcode = new StringBuilder();
        try {
            url = new URL(Config.CHECK_USER_URL + "Username=" + username);

            String readLine = null;
            String sResult = null;

            BufferedReader buffReader = new BufferedReader(new InputStreamReader(url.openStream (),"UTF-8"));
            while ((readLine = buffReader.readLine ()) != null) {
                if (sResult == null) {
                    quellcode.append(readLine);

                }
                else {
                    //sResult = sResult + readLine;
                    quellcode.append(readLine);
                }
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return quellcode.toString().equals("0");
    }

    public static void CheckRegistration(final String user, final String regID)throws IOException {

        URL url = null;
        StringBuilder quellcode = new StringBuilder();
        try {
            url = new URL(Config.CHECK_REGID_URL + "ID=" + regID + "&username=" + user);

            String readLine = null;
            String sResult = null;

            BufferedReader buffReader = new BufferedReader(new InputStreamReader(url.openStream (),"UTF-8"));
            while ((readLine = buffReader.readLine ()) != null) {
                if (sResult == null) {
                    quellcode.append(readLine);

                }
                else {
                    //sResult = sResult + readLine;
                    quellcode.append(readLine);
                }
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static boolean CheckLogin(final String username, final String password)throws IOException {

        URL url = null;
        StringBuilder quellcode = new StringBuilder();
        try {
            url = new URL(Config.CHECK_LOGIN_URL + "username=" + username + "&password=" + password);

            String readLine = null;
            String sResult = null;

            BufferedReader buffReader = new BufferedReader(new InputStreamReader(url.openStream (),"UTF-8"));
            while ((readLine = buffReader.readLine ()) != null) {
                if (sResult == null) {
                    quellcode.append(readLine);

                }
                else {
                    //sResult = sResult + readLine;
                    quellcode.append(readLine);
                }
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return quellcode.toString().equals("1");
    }

    public static String GetAllGroupsFromServer(final String filter)throws IOException {

        URL url = null;
        StringBuilder quellcode = new StringBuilder();
        try {
            url = new URL(Config.GET_ALL_GROUPS_URL + "Filter=" + filter);

            String readLine = null;
            String sResult = null;

            BufferedReader buffReader = new BufferedReader(new InputStreamReader(url.openStream (),"UTF-8"));
            while ((readLine = buffReader.readLine ()) != null) {
                if (sResult == null) {
                    quellcode.append(readLine);

                }
                else {
                    //sResult = sResult + readLine;
                    quellcode.append(readLine);
                }
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return quellcode.toString();
    }

    public static String GetAllUsersFromServer(final String filter)throws IOException {

        URL url = null;
        StringBuilder quellcode = new StringBuilder();
        try {
            url = new URL(Config.GET_ALL_USERS_URL + "Filter=" + filter);

            String readLine = null;
            String sResult = null;

            BufferedReader buffReader = new BufferedReader(new InputStreamReader(url.openStream (),"UTF-8"));
            while ((readLine = buffReader.readLine ()) != null) {
                if (sResult == null) {
                    quellcode.append(readLine);

                }
                else {
                    //sResult = sResult + readLine;
                    quellcode.append(readLine);
                }
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return quellcode.toString();
    }

    public static String GetMyGroupsFromServer(final String regID)throws IOException {

        URL url = null;
        StringBuilder quellcode = new StringBuilder();
        try {

            url = new URL(Config.GET_MY_GROUPS_URL + "regID=" + regID);

            String readLine = null;
            String sResult = null;

            BufferedReader buffReader = new BufferedReader(new InputStreamReader(url.openStream (),"UTF-8"));
            while ((readLine = buffReader.readLine ()) != null) {
                if (sResult == null) {
                    quellcode.append(readLine);

                }
                else {
                    //sResult = sResult + readLine;
                    quellcode.append(readLine);
                }
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return quellcode.toString();
    }

    public static String GetHighscoresFromServer(){

        URL url = null;
        StringBuilder quellcode = new StringBuilder();
        try {
            url = new URL(Config.GET_HIGHSCORES_URL);

            String readLine = null;
            String sResult = null;

            BufferedReader buffReader = new BufferedReader(new InputStreamReader(url.openStream (),"UTF-8"));
            while ((readLine = buffReader.readLine ()) != null) {
                if (sResult == null) {
                    quellcode.append(readLine);
                }
                else {
                    quellcode.append(readLine);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return quellcode.toString();

    }
}