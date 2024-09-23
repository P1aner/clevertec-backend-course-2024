package ru.clevertec.parser.service.chain;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class JsonAndMapContainer {
    private Map<String, Object> map = new HashMap<>();
    private StringBuilder keyString = new StringBuilder();
    private StringBuilder valueString = new StringBuilder();
    private StringBuilder defaultValue;
    private Boolean isKey = true;

    public JsonAndMapContainer(StringBuilder defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean hasNext() {
        return !defaultValue.isEmpty();
    }

    public void revertKey() {
        isKey = !isKey;
    }

    public void createNewRow() {
        map.put(keyString.toString().trim(), valueString.toString().trim());
        resetToNewRow();
    }

    public void createNewRow(Map<String, Object> stringObjectMap) {
        map.put(keyString.toString().trim(), stringObjectMap);
        resetToNewRow();
    }

    public void createNewRow(List<Object> objects) {
        map.put(keyString.toString().trim(), objects);
        resetToNewRow();
    }

    public char excludeChar() {
        char charAt = defaultValue.charAt(0);
        defaultValue.deleteCharAt(0);
        return charAt;
    }

    public char getFirsChar() {
        return defaultValue.charAt(0);
    }

    private void resetToNewRow() {
        keyString = new StringBuilder();
        valueString = new StringBuilder();
        isKey = true;
    }
}
