package com.mamotec.energycontrolbackend.ocpp;

import eu.chargetime.ocpp.model.Request;

public interface OcppRequestListener<T extends Request>{

    void onRequest(T request);
}
