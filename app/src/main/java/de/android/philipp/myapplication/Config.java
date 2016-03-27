package de.android.philipp.myapplication;

public interface Config {

    // used to share GCM regId with application server - using php app server
    static final String REGISTER_URL = "http://lamp.wlan.hwr-berlin.de/museum/gcm/register.php?";
    static final String GROUP_URL = "http://lamp.wlan.hwr-berlin.de/museum/gcm/erstellen.php?";
    static final String INVITE_URL = "http://lamp.wlan.hwr-berlin.de/museum/gcm/einladen.php?";
    static final String CHECK_USER_URL = "http://lamp.wlan.hwr-berlin.de/museum/gcm/checkUsername.php?";
    static final String CHECK_LOGIN_URL = "http://lamp.wlan.hwr-berlin.de/museum/gcm/checkLogin.php?";
    static final String CHECK_REGID_URL = "http://lamp.wlan.hwr-berlin.de/museum/gcm/checkRegistration.php?";
    static final String GET_ALL_GROUPS_URL = "http://lamp.wlan.hwr-berlin.de/museum/gcm/allGroups.php?";
    static final String GET_ALL_USERS_URL = "http://lamp.wlan.hwr-berlin.de/museum/gcm/allUsers.php?";
    static final String GET_MY_GROUPS_URL = "http://lamp.wlan.hwr-berlin.de/museum/gcm/myGroups.php?";

    static final String GET_HIGHSCORES_URL = "http://lamp.wlan.hwr-berlin.de/museum/gcm/highscores.php";
    static final String GET_QUESTION_URL = "http://lamp.wlan.hwr-berlin.de/museum/gcm/getFrageIDs.php?";

     // Google Project Number
    static final String GPID = "438051207740";

}