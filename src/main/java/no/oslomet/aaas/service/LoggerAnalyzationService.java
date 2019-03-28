package no.oslomet.aaas.service;

import no.oslomet.aaas.controller.AnalyzationController;
import no.oslomet.aaas.model.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class LoggerAnalyzationService {

    private Logger analyzationLogger = LoggerFactory.getLogger(AnalyzationController.class);

    public void loggAnalyzationPayload(Request payload) {
        analyzationLogger.info("Request received: " + "Size of data: set " + " Number of rows = " + numRows(payload) + ", Number of columns " + numColumns(payload) + ", Bytesize = " + bytesize(payload));
    }


    private int numColumns(Request payload) {
        if (payload == null || payload.getData() == null) return 0;
        return payload.getData().get(0).length;
    }

    private int numRows(Request payload) {
        if (payload == null || payload.getData() == null) return 0;
        return payload.getData().size();
    }

    private int bytesize(Request payload) {
        if (payload == null || payload.getData() == null) return 0;
        return payload.getData().toString().length();
    }
}
