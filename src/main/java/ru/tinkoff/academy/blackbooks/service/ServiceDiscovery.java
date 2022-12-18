package ru.tinkoff.academy.blackbooks.service;

import org.json.JSONString;

public interface ServiceDiscovery {
    JSONString discoverService(String url);
}