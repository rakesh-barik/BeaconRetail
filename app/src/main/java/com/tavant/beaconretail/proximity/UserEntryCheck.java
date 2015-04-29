package com.tavant.beaconretail.proximity;

/**
 * Created by rakesh.barik on 12-03-2015.
 */
public class UserEntryCheck {
    private static UserEntryCheck ourInstance;
    private boolean isInsidePremise;

    private boolean isAtEntry;

    private boolean isAnyPopUpShowing;

    private boolean entryOfferShown = false;
    private boolean menSectionOfferShown = false;
    private boolean womenSectionOfferShown = false;
    private boolean checkOutPopUpShowing;

    public boolean isInsidePremise() {
        return isInsidePremise;
    }

    public void setInsidePremise(boolean isInsidePremise) {
        this.isInsidePremise = isInsidePremise;
    }

    public boolean isAtEntry() {
        return isAtEntry;
    }

    public void setAtEntry(boolean isAtEntry) {
        this.isAtEntry = isAtEntry;
    }

    public boolean isEntryOfferShown() {
        return entryOfferShown;
    }

    public void setEntryOfferShown(boolean entryOfferShown) {
        this.entryOfferShown = entryOfferShown;
    }

    public boolean isMenSectionOfferShown() {
        return menSectionOfferShown;
    }

    public void setMenSectionOfferShown(boolean menSectionOfferShown) {
        this.menSectionOfferShown = menSectionOfferShown;
    }

    public boolean isWomenSectionOfferShown() {
        return womenSectionOfferShown;
    }

    public void setWomenSectionOfferShown(boolean womenSectionOfferShown) {
        this.womenSectionOfferShown = womenSectionOfferShown;
    }

    public boolean isAnyPopUpShowing() {
        return isAnyPopUpShowing;
    }

    public void setAnyPopUpShowing(boolean isAnyPopUpShowing) {
        this.isAnyPopUpShowing = isAnyPopUpShowing;
    }

    public static UserEntryCheck getInstance() {
        if(ourInstance == null){
            ourInstance =  new UserEntryCheck();
        }
        return ourInstance;
    }

    private UserEntryCheck() {
    }

    public boolean isCheckOutPopUpShowing() {
        return checkOutPopUpShowing;
    }

    public void setCheckOutPopUpShowing(boolean checkOutPopUpShowing) {
        this.checkOutPopUpShowing = checkOutPopUpShowing;
    }
}
