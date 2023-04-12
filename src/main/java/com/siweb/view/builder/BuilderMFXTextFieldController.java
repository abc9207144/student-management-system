package com.siweb.view.builder;

import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.enums.FloatMode;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.KeyEvent;

/***
 * BuilderMFXTextFieldController provides an easy way to create a MFXTextField using the builder design pattern
 */
public class BuilderMFXTextFieldController {

    private final MFXTextField mfxTextField;

    public static class Builder {
        private final String id;
        private String floatingText = "";
        private FloatMode floatMode = FloatMode.BORDER;
        private Boolean isAnimated = false;
        private Boolean isDisable = false;
        private String defaultText = "";
        private double prefWidth = Double.MAX_VALUE;
        private Insets padding = new Insets(6,6,6,6);

        private EventHandler<? super KeyEvent> onKeyPressed;

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

        public Builder setPrefWidth(double prefWidth) {
            this.prefWidth = prefWidth;
            return this;
        }

        public Builder setPadding(Insets padding) {
            this.padding = padding;
            return this;
        }

        public Builder setOnKeyPressed(EventHandler<? super KeyEvent> onKeyPressed) {
            this.onKeyPressed = onKeyPressed;
            return this;
        }

        public BuilderMFXTextFieldController build() {
            return new BuilderMFXTextFieldController(this);
        }


    }

    private BuilderMFXTextFieldController(Builder builder) {

        this.mfxTextField = new MFXTextField();

        this.mfxTextField.setId(builder.id);
        this.mfxTextField.setFloatingText(builder.floatingText);
        this.mfxTextField.setFloatMode(builder.floatMode);
        this.mfxTextField.setAnimated(builder.isAnimated);
        this.mfxTextField.setText(builder.defaultText);
        this.mfxTextField.setPrefWidth(builder.prefWidth);
        this.mfxTextField.setPadding(builder.padding);
        this.mfxTextField.setDisable(builder.isDisable);

        if(builder.onKeyPressed != null) {
            this.mfxTextField.setOnKeyPressed(builder.onKeyPressed);
        }


    }

    public MFXTextField get() {
        return this.mfxTextField;
    }


}
