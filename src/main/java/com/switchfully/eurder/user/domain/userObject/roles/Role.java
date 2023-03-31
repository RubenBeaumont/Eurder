package com.switchfully.eurder.user.domain.userObject.roles;

import com.switchfully.eurder.user.service.security.Feature;

import java.util.List;

import static com.switchfully.eurder.user.service.security.Feature.*;
import static com.google.common.collect.Lists.newArrayList;

public enum Role {
    ADMIN(newArrayList(
            ADD_ITEM,
            UPDATE_ITEM,
            GET_ALL_CUSTOMERS,
            GET_A_CUSTOMER
    )),
    CUSTOMER(newArrayList(
            ORDER_ITEM,
            GET_ALL_ITEMS,
            GET_ALL_MY_ORDERS
    ));

    private final List<Feature> featureList;

    Role(List<Feature> featureList) {
        this.featureList = featureList;
    }

    public boolean containsFeature(Feature feature){
        return featureList.contains(feature);
    }
}
