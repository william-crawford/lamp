package cs2340.edu.gatech.lamp.model;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;
import static cs2340.edu.gatech.lamp.utils.ShelterManager.updateShelter;

/**
 * Created by will on 2/17/18.
 */

public class Shelter {

    private String name;
    private Location location;
    private String phoneNumber;
    private String imageURL;

    private String capacity;
    private String notes;
    private String restrictions;

    private int spacesFilled;

    private int numericCapacity;

    public enum GenderPolicy {
        NO_FILTER(""),
        MALE_ONLY("Male Only"),
        FEMALE_ONLY("Female Only"),
        ANYONE("Anyone");

        private String label;

        GenderPolicy(String value){
            this.label = value;
        }

        public String toString(){
            return label;
        }
    }

    public enum AgePolicy {
        NO_FILTER(""),
        FAMILIES_WITH_NEWBORNS("Families with Newborns"),
        CHILDREN("Children"),
        YOUNG_ADULTS("Young Adults"),
        ANYONE("Anyone");

        private String label;

        AgePolicy(String value){
            this.label = value;
        }

        public String toString(){
            return label;
        }
    }

    private GenderPolicy genderPolicy;
    private AgePolicy agePolicy;

    private String key;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
        updateShelter(this);
    }

    public boolean isFull() {

        return numericCapacity - spacesFilled > 0;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
        updateShelter(this);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        updateShelter(this);
    }

    public String getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
        updateShelter(this);
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
        updateShelter(this);
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
        updateShelter(this);
    }


    public int getNumericCapacity() {
        return numericCapacity;
    }

    public int getSpacesFilled() {
        return spacesFilled;
    }

    public void setSpacesFilled(int spacesFilled) {
        this.spacesFilled = spacesFilled;
    }

    public String getKey() {
        return key;
    }

    @Override
    public boolean equals(Object other) {
        return !(other == null || !(other instanceof Shelter)) && this.key.equals(((Shelter) other).key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }

    public Shelter() {
        //This exists
    }

    public Shelter(String[] info) {
        key = info[0];
        name = info[1];
        capacity = info[2];
        restrictions = info[3];
        double longitude = Double.parseDouble(info[4]);
        double latitude = Double.parseDouble(info[5]);
        String address = info[6];
        location = new Location(latitude, longitude, address);
        notes = info[7];
        phoneNumber = info[8];
        imageURL = info[9];
        spacesFilled = Integer.parseInt(info[10]);

        // DON'T READ THIS IF YOU LIKE SLEEPING COMFORTABLY AT NIGHT
        if (restrictions.contains("Women")) {
            genderPolicy = GenderPolicy.FEMALE_ONLY;
        } else if (restrictions.contains("Men")) {
            genderPolicy = GenderPolicy.MALE_ONLY;
        } else {
            genderPolicy = GenderPolicy.ANYONE;
        }

        if (restrictions.contains("Children")) {
            agePolicy = AgePolicy.CHILDREN;
        } else if (restrictions.contains("newborns")) {
            agePolicy = AgePolicy.FAMILIES_WITH_NEWBORNS;
        } else if (restrictions.contains("Young")) {
            agePolicy = AgePolicy.YOUNG_ADULTS;
        } else {
            agePolicy = AgePolicy.ANYONE;
        }

        StringBuilder sb = new StringBuilder();
        for (char c : capacity.toCharArray()) {
            if (c >= 48 && c < 58) {
                sb.append(c);
            }
        }
        try {
            numericCapacity = Integer.parseInt(new String(sb));
        } catch (Exception e) {
            numericCapacity = 0;
        }
    }
    /*
    public String getDetails() {
        String[] detail = getInfo();
        String details = "Unique Key: " + detail[0] + "\nShelter Name: " + detail[1] + "\nCapacity: " + detail[2] + "\nRestrictions: " + detail[3] + "\nLongitude: " + detail[4]
                + "\nLatitude: " + detail[5] + "\nAddress: " + detail[6] + "\nSpecial Notes: " + detail[7] + "\nPhone Number: " + detail[8];
        return details;
    }
    */
    public String[] getInfo() {
        String[] info = new String[10];
        info[0] = key;
        info[1] = name;
        info[2] = capacity;
        info[3] = restrictions;
        info[4] = Double.toString(location.getLongitude());
        info[5] = Double.toString(location.getLatitude());
        info[6] = location.getAddress();
        info[7] = notes;
        info[8] = phoneNumber;
        info[9] = imageURL;
        info[10] = String.valueOf(spacesFilled);

        return info;
    }

    public boolean checkGPFilter(GenderPolicy gp) {
        return gp == null || gp == GenderPolicy.NO_FILTER || gp == genderPolicy;
    }

    public boolean checkAPFilter(AgePolicy ap) {
        return ap == null || ap == AgePolicy.NO_FILTER || ap == agePolicy;
    }

    public boolean checkNameFilter(String filter) {
        return filter.length() == 0 || name.toLowerCase().contains(filter.toLowerCase());
    }
}
