package com.siweb.view;


/***
 * SelectOption is an "option" of a select element with a different "display text" from the "value"
 */
public class SelectOption {

    private final String displayText;
    private final String valText;

    public SelectOption(String displayText, String valText) {
        this.displayText = displayText;
        this.valText = valText;
    }

    public SelectOption(String text) {
        this.displayText = text;
        this.valText = text;
    }

    public String getValText() {
        return valText;
    }

    @Override
    public String toString() {
        return displayText;
    }

}
