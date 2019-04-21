package com.pucminas.crs.login;

import com.google.gson.JsonElement;
import com.pucminas.crs.utils.ApiCallInterface;

import io.reactivex.Observable;


public class Repository {

    private ApiCallInterface apiCallInterface;

    public Repository(ApiCallInterface apiCallInterface) {
        this.apiCallInterface = apiCallInterface;
    }

    /*
     * method to call login api
     * */
    public Observable<JsonElement> executeLogin(String emailAddress, String password) {
        return apiCallInterface.login(emailAddress, password);
    }

}
