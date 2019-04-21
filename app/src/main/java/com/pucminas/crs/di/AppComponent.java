package com.pucminas.crs.di;


import com.pucminas.crs.login.LoginActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class, UtilsModule.class})
@Singleton
public interface AppComponent {

    void doInjection(LoginActivity loginActivity);

}
