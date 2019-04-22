package no.oslomet.aaas.service;

import no.oslomet.aaas.model.anonymity.AnonymizationResultPayload;
import no.oslomet.aaas.model.anonymity.PrivacyCriterionModel;
import no.oslomet.aaas.model.Request;
import no.oslomet.aaas.model.risk.RiskProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoggerService {

    private String rowNum = "Number of rows = ";
    private String colNum = ", Number of columns ";
    private String byteSize = ", Bytesize = ";
    private String reqIp = ", Request Source IP = ";

    public void loggPayload(Request payload, String ip, Class classToLogg) {
        Logger logger = LoggerFactory.getLogger(classToLogg);

        String log = "Request received, Size of data set: " + rowNum + numRows(payload) +
                colNum + numColumns(payload) +
                byteSize + bytesize(payload) +
                reqIp + ip +
                " Privacy models used = " + logPrivacyModel(payload) +
                " Suppression Limit used = " + logSuppressionLimit(payload);

        logger.info(log);
    }

    public void loggAnalyzationResult(RiskProfile analyzationResult, Request payload, String ip, long requestProcessingTime, Class classToLogg) {
        Logger logger = LoggerFactory.getLogger(classToLogg);

        String log = AnalyzationResponseLog(payload,ip,requestProcessingTime);

        logger.info(log);
    }

    public void loggAnonymizeResult(AnonymizationResultPayload payload, long requestProcessingTime, Class classToLogg, String ip) {
        Logger logger = LoggerFactory.getLogger(classToLogg);

        String log = AnonymizationResponseLog(payload,ip,requestProcessingTime);

        logger.info(log);
    }

    private String AnalyzationResponseLog(Request payload, String ip, long requestProcessingTime){
        return "Request complete, Size of data set: " + rowNum + numRows(payload) +
                colNum + numColumns(payload) +
                byteSize + bytesize(payload) +
                reqIp + ip +
                " Request processing time = " + requestProcessingTime + " milliseconds";
    }

    private String AnonymizationResponseLog(AnonymizationResultPayload payload, String ip, long requestProcessingTime){
        return "Request complete, Size of data set: " + rowNum + numRows(payload) +
                colNum + numColumns(payload) +
                byteSize + bytesize(payload) +
                reqIp + ip +
                " Request processing time = " + requestProcessingTime + " milliseconds";
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


    private String logPrivacyModel(Request payload) {
        if (payload == null || payload.getData() == null || payload.getPrivacyModels() == null) return "";
        String privacyModels = "";
        for (PrivacyCriterionModel privacyModel : payload.getPrivacyModels())
            privacyModels = privacyModels.concat(privacyModel.getPrivacyModel().getName()).concat(", ");
        return privacyModels;
    }

    private Double logSuppressionLimit(Request payload){
        if(payload == null || payload.getSuppressionLimit() == null) return null;
        return payload.getSuppressionLimit();
    }


    private int numColumns(AnonymizationResultPayload payload) {
        if (payload == null || payload.getAnonymizeResult() == null || payload.getAnonymizeResult().getData() == null)
            return 0;
        return payload.getAnonymizeResult().getData().get(0).length;
    }

    private int numRows(AnonymizationResultPayload payload) {
        if (payload == null || payload.getAnonymizeResult() == null || payload.getAnonymizeResult().getData() == null)
            return 0;
        return payload.getAnonymizeResult().getData().size();
    }

    private int bytesize(AnonymizationResultPayload payload) {
        if (payload == null || payload.getAnonymizeResult() == null || payload.getAnonymizeResult().getData() == null)
            return 0;
        return payload.getAnonymizeResult().getData().toString().length();
    }


}
