package com.siweb.view.builder;

import com.siweb.view.SelectOption;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.enums.FloatMode;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;

import java.util.List;

/***
 * BuilderMFXComboBoxController provides an easy way to create a MFXComboBox using the builder design pattern
 */
public class BuilderMFXComboBoxController {

    private final MFXComboBox<SelectOption> mfxComboBox;

    public static class Builder {

        private final String id;
        private final ObservableList<SelectOption> items = FXCollections.observableArrayList();
        private String valText = "";
        private String floatingText = "";
        private FloatMode floatMode = FloatMode.BORDER;
        private Boolean isAnimated = false;
        private Boolean isDisable = false;
        private Boolean isFiltered = false;
        private double prefWidth = Double.MAX_VALUE;
        private Insets padding = new Insets(6,6,6,6);
        private ChangeListener<? super SelectOption> onChangelistener;

        public Builder(String id, String floatingText, List<SelectOption> selectOptions) {
            this.id = id;
            this.floatingText = floatingText;

            items.addAll(selectOptions);
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

        public Builder setValText(int i) {
            this.valText = i + "";
            return this;
        }
        public Builder setValText(double d) {
            this.valText = d + "";
            return this;
        }
        public Builder setValText(String s) {
            this.valText = s;
            return this;
        }
        public Builder setValText(Object o) {
            this.valText = o.toString();
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

        public Builder setIsFiltered(Boolean isFiltered) {
            this.isFiltered = isFiltered;
            return this;
        }

        public Builder addSelectionListener(ChangeListener<? super SelectOption> onChangelistener) {
            this.onChangelistener = onChangelistener;
            return this;
        }

        public BuilderMFXComboBoxController build() {
            return new BuilderMFXComboBoxController(this);
        }


    }

    private BuilderMFXComboBoxController(Builder builder) {

        if (builder.isFiltered)
        {
            this.mfxComboBox = new MFXFilterComboBox<>(builder.items);
        }
        else
        {
            this.mfxComboBox = new MFXComboBox<>(builder.items);
        }


        if(!builder.valText.isEmpty())
        {
            builder.items.forEach((item) -> {

                // automatically select the option either by display value or by val value
                // this is not ideal for duplicated names in the dropdown
                // TODO: revamp the automatic select option
                if(item.toString().equals(builder.valText) || item.getValText().equals(builder.valText))
                {
                    this.mfxComboBox.selectItem(item);
                }
            });
        }

        this.mfxComboBox.setId(builder.id);
        this.mfxComboBox.setFloatingText(builder.floatingText);
        this.mfxComboBox.setFloatMode(builder.floatMode);
        this.mfxComboBox.setAnimated(builder.isAnimated);
        this.mfxComboBox.setText(builder.valText);
        this.mfxComboBox.setPrefWidth(builder.prefWidth);
        this.mfxComboBox.setPadding(builder.padding);
        this.mfxComboBox.setDisable(builder.isDisable);

        if(builder.onChangelistener != null) {
            this.mfxComboBox.selectedItemProperty().addListener(builder.onChangelistener);
        }


    }

    public MFXComboBox<SelectOption> get() {
        return this.mfxComboBox;
    }


}
