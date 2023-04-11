package com.siweb.view.builder;

import com.siweb.view.SelectOption;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.enums.FloatMode;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.KeyEvent;
import javafx.util.converter.LocalDateStringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/***
 * BuilderMFXTextFieldController provides an easy way to create a MFXTextField using the builder design pattern
 */
public class BuilderMFXDatePickerController {

    private final MFXDatePicker mfxDatePicker;

    public static class Builder {
        private final String id;
        private String floatingText = "";
        private FloatMode floatMode = FloatMode.BORDER;
        private Boolean isAnimated = false;
        private Boolean isDisable = false;
        private String defaultText = "";
        private double prefWidth = Double.MAX_VALUE;
        private Insets padding = new Insets(6,6,6,6);

        private ChangeListener<? super String> onChangelistener;

        public Builder(String id, String floatingText) {
            this.id = id;
            this.floatingText = floatingText;
        }

        public Builder setFloatingText(String floatingText) {
            this.floatingText = floatingText;
            return this;
        }
        public Builder setFloatMode(FloatMode floatMode) {
            this.floatMode = floatMode;
            return this;
        }
        public Builder setAnimated(Boolean isAnimated) {
            this.isAnimated = isAnimated;
            return this;
        }

        public Builder setDisable(Boolean isDisable) {
            this.isDisable = isDisable;
            return this;
        }


        public Builder setText(String defaultText) {
            this.defaultText = defaultText;
            return this;
        }

        public Builder setText(LocalDate defaultDate) {
            this.defaultText = defaultDate.toString();
            return this;
        }

        public Builder setPrefWidth(double prefWidth) {
            this.prefWidth = prefWidth;
            return this;
        }

        public Builder setPadding(Insets padding) {
            this.padding = padding;
            return this;
        }

        public Builder addSelectionListener(ChangeListener<? super String> onChangelistener) {
            this.onChangelistener = onChangelistener;
            return this;
        }

        public BuilderMFXDatePickerController build() {
            return new BuilderMFXDatePickerController(this);
        }


    }

    private BuilderMFXDatePickerController(Builder builder) {

        this.mfxDatePicker = new MFXDatePicker();

        if(!builder.defaultText.isEmpty())
        {
            this.mfxDatePicker.setValue(LocalDate.parse(builder.defaultText));
        }

        this.mfxDatePicker.setId(builder.id);
        this.mfxDatePicker.setFloatingText(builder.floatingText);
        this.mfxDatePicker.setFloatMode(builder.floatMode);
        this.mfxDatePicker.setAnimated(builder.isAnimated);
        this.mfxDatePicker.setText(builder.defaultText);
        this.mfxDatePicker.setPrefWidth(builder.prefWidth);
        this.mfxDatePicker.setPadding(builder.padding);
        this.mfxDatePicker.setDisable(builder.isDisable);

        if(builder.onChangelistener != null) {
            this.mfxDatePicker.selectedTextProperty().addListener(builder.onChangelistener);
        }


    }

    public MFXDatePicker get() {
        return this.mfxDatePicker;
    }


}
