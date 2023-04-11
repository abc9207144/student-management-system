package com.siweb.view;


import com.siweb.model.TableViewModel;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/***
 * SelectOption is an "option" of a select element with a different "display text" from the "value"
 */
public class SelectOption<T> {

    private final String displayText;
    private final T val;

    public SelectOption(String displayText, T val) {
        this.displayText = displayText;
        this.val = val;
    }

    public SelectOption(String text) {
        this.displayText = text;
        this.val = (T) text;
    }

    public String getValText() {

        if (val instanceof LocalDate) return ((LocalDate) val).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        else if (val instanceof LocalTime) return ((LocalTime) val).format(DateTimeFormatter.ofPattern("hh:mm"));
        else if (val instanceof TableViewModel) return ((TableViewModel) val).getValText();
        else if (val instanceof Integer || val instanceof Double) return val + "";
        else return val.toString();
    }

    @Override
    public String toString() {
        return displayText;
    }

}
