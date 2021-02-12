package com.example.rainfallapp.interfaces;

import com.example.rainfallapp.model.PreferencesModel;

/*User Click on Save button in PreferencesDialog then the model (whether all or any values are changed) is ent to main activity and set alarm etc.*/
public interface SaveClickListener {
    void savePressed(PreferencesModel model);
}
