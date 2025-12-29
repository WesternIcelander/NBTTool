package io.siggi.nbt.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UserProfile {
    private UUID id;
    private String name;
    private final Map<String,List<AuthLibProperty>> properties;

    public UserProfile(UUID id, String name) {
        this.id = id;
        this.name = name;
        this.properties = new LinkedHashMap<>();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, List<AuthLibProperty>> getProperties() {
        return properties;
    }

    public AuthLibProperty getProperty(String key) {
        List<AuthLibProperty> propertyList = properties.get(key);
        if (propertyList == null || propertyList.isEmpty()) return null;
        return propertyList.get(0);
    }

    public void addProperty(AuthLibProperty property) {
        getProperties().computeIfAbsent(property.name(), k -> new ArrayList<>()).add(property);
    }

    public void setProperty(AuthLibProperty property) {
        List<AuthLibProperty> list = new ArrayList<>();
        list.add(property);
        getProperties().put(property.name(), list);
    }
}
