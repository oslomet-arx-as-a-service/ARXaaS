package no.oslomet.aaas.service;


import no.oslomet.aaas.model.AnalysationPayload;
import no.oslomet.aaas.model.AnalysisResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AnalysisationService {

    private final Analyser analyser;

    @Autowired
    public AnalysisationService(Analyser analyser){
       this.analyser = analyser;
    }


    public AnalysisResult analyse(AnalysationPayload payload){
        return analyser.analyse(payload);
    }


}
