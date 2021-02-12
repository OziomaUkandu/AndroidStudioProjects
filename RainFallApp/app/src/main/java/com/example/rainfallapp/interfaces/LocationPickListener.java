package com.example.rainfallapp.interfaces;

/*User click on select location from PreferenceDialog, call goes to main activity and launches google map using startActivityForResult
 *When main onActivityResult is called, from here location model is sent to Preference Dialog*/
public interface LocationPickListener {
    void selectLocation();
}
