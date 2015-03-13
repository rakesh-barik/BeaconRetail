package com.tavant.beaconretail.proximity;

/**
 * Created by rakesh.barik on 12-03-2015.
 */
public class UserEntryCheck {
    private static UserEntryCheck ourInstance;
    private boolean isInsidePremise;
    private boolean isAtEntry;
    private boolean isAtMenSection;
    private boolean isAtWomenSection;

    private boolean showWelcomeMsg = true;
    private boolean showMenSectionMsg = true;
    private boolean showWomenSectionMsg = true;

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

    public boolean isAtMenSection() {
        return isAtMenSection;
    }

    public void setAtMenSection(boolean isAtMenSection) {
        this.isAtMenSection = isAtMenSection;
    }

    public boolean isAtWomenSection() {
        return isAtWomenSection;
    }

    public void setAtWomenSection(boolean isAtWomenSection) {
        this.isAtWomenSection = isAtWomenSection;
    }

    public boolean isShowWelcomeMsg() {
        return showWelcomeMsg;
    }

    public void setShowWelcomeMsg(boolean showWelcomeMsg) {
        this.showWelcomeMsg = showWelcomeMsg;
    }

    public boolean isShowMenSectionMsg() {
        return showMenSectionMsg;
    }

    public void setShowMenSectionMsg(boolean showMenSectionMsg) {
        this.showMenSectionMsg = showMenSectionMsg;
    }

    public boolean isShowWomenSectionMsg() {
        return showWomenSectionMsg;
    }

    public void setShowWomenSectionMsg(boolean showWomenSectionMsg) {
        this.showWomenSectionMsg = showWomenSectionMsg;
    }

    public static UserEntryCheck getInstance() {
        if(ourInstance == null){
            ourInstance =  new UserEntryCheck();
        }
        return ourInstance;
    }

    private UserEntryCheck() {
    }
}
