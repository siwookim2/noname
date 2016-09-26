package com.example.owner.hanieum_project.service;

import com.example.owner.hanieum_project.data.Channel;

/**
 * Created by Owner on 2016-08-08.
 */
public interface WeatherServiceCallback {
    void serviceSuccess(Channel channel);

    void serviceFailure(Exception exception);
}
