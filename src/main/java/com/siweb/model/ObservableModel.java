package com.siweb.model;

import com.siweb.view.SelectOption;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/***
 * ObservableModel makes a model observable, can then be used in a TableView which listens to the changes of the observable list.
 * @param <E> The Object type. E.g. User
 */
public abstract class ObservableModel<E> {
    protected ObservableList<E> obsList = FXCollections.observableArrayList();
    protected TreeMap<Integer, E> modelsMap = new TreeMap<Integer, E>();

    // add the object type from a JSONArray of JSONObjects
    public void add(JSONArray jsonArray, Boolean isAddToObservableList) {
        for(int i = 0; i < jsonArray.length(); i++) {
            add(jsonArray.getJSONObject(i), isAddToObservableList);
        }
    }


    // add the object type from a JSONObject
    public abstract E add(JSONObject jsonObject, Boolean isAddToObservableList);

    // return an unmodifiable observable list
    public ObservableList<E> getObsList() {
        return FXCollections.unmodifiableObservableList(obsList);
    }

    public E get(int i) {
        return modelsMap.get(i);
    }

    // Clear the observable list
    public void clearObsList() {
        obsList.clear();
    }
    public void clearAll() {
        obsList.clear();
        modelsMap.clear();
    }



    public List<SelectOption> getSelectOptionList() {
        ArrayList<SelectOption> res = new ArrayList<>();

        for (int key : modelsMap.keySet()) {
            res.add(new SelectOption(modelsMap.get(key).toString(), modelsMap.get(key)));
        }

        return res;

    }

}
