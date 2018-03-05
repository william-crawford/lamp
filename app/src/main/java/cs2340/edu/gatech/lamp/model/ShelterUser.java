package cs2340.edu.gatech.lamp.model;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Tucker on 3/4/2018.
 */

public abstract class ShelterUser extends User{

    protected ShelterUser(FirebaseUser user) {
        super(user);
    }

    protected void createShelter() {
        //create method later
    }

    protected void deleteShelter(){
        //create method later
    }

}
