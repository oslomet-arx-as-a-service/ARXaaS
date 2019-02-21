package no.oslomet.aaas.utils;

import org.deidentifier.arx.ARXPopulationModel;
import org.deidentifier.arx.Data;
import org.deidentifier.arx.risk.RiskModelPopulationUniqueness;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/***
 * Utility class analysing the data against re-identification risk
 */
@Component
public class ARXPayloadAnalyser {

    private static final int PRECENT_CONVERT = 100;

    /***
     * Returns a double that shows the lowest prosecutor re-identification risk found from the data set, based on
     * the population model that is defined.
     * @param data tabular data set to be analysed against re-identification risk
     * @param pModel population model for our data set
     * @return lowest risk calculated from the data set
     */
    public double getPayloadLowestProsecutorRisk(Data data, ARXPopulationModel pModel){
        return data.getHandle()
                .getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getLowestRisk();
    }

    /***
     * Returns a double that shows the amount of records that are affected by a specific amount of risk. This method
     * shows how much of our records has the specific amount of risk.
     * @param data tabular data set to be analysed against re-identification risk
     * @param pModel population model for our data set that defines the population size and sampling fraction
     * @param risk specific amount of risk that affects one or more records
     * @return records affect by a specific amount of risk
     */
    public double getPayloadRecordsAffectByRisk(Data data, ARXPopulationModel pModel, double risk){
        return data.getHandle()
                .getRiskEstimator(pModel)
                .getSampleBasedRiskDistribution()
                .getFractionOfRecordsAtRisk(risk);
    }

    /***
     * Returns a double that shows the average prosecutor re-identification risk found from the data set, based on
     * the population model that is defined.
     * @param data tabular data set to be analysed against re-identification risk
     * @param pModel population model for our data set that defines the population size and sampling fraction
     * @return average risk calculated from the data set
     */
    public Double getPayloadAverageProsecutorRisk(Data data, ARXPopulationModel pModel){
        return data.getHandle()
                .getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getAverageRisk();
    }

    public double getPayloadHighestProsecutorRisk(Data data, ARXPopulationModel pModel){
        return data.getHandle()
                .getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getHighestRisk();
    }

    public double getPayloadEstimatedProsecutorRisk(Data data, ARXPopulationModel pModel){
        return data.getHandle()
                .getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getEstimatedProsecutorRisk();
    }

    public double getPayloadEstimatedJournalistRisk(Data data, ARXPopulationModel pModel){
        return data.getHandle()
                .getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getEstimatedJournalistRisk();
    }

    public double getPayloadEstimatedMarketerRisk(Data data, ARXPopulationModel pModel){
        return data.getHandle()
                .getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getEstimatedMarketerRisk();
    }

    public double getPayloadSampleUniques(Data data, ARXPopulationModel pModel){
        return data.getHandle()
                .getRiskEstimator(pModel)
                .getSampleBasedUniquenessRisk()
                .getFractionOfUniqueTuples();
    }

    public double getPayloadPopulationUniques(Data data, ARXPopulationModel pModel){
        return data.getHandle()
                .getRiskEstimator(pModel)
                .getPopulationBasedUniquenessRisk()
                .getFractionOfUniqueTuples(getPayloadPopulationModel(data,pModel));
    }

    public RiskModelPopulationUniqueness.PopulationUniquenessModel getPayloadPopulationModel(Data data, ARXPopulationModel pModel){
        return data.getHandle()
                .getRiskEstimator(pModel)
                .getPopulationBasedUniquenessRisk()
                .getPopulationUniquenessModel();
    }

    public Set<String> getPayloadQuasiIdentifiers(Data data){
        return data.getDefinition().getQuasiIdentifyingAttributes();
    }

    public Map<String, String> getPayloadAnalysisData(Data data, ARXPopulationModel pModel){
        Map<String, String> metricsMap = new HashMap<>();
        metricsMap.put("measure_value", "[%]");
        metricsMap.put("lowest_risk", String.valueOf(getPayloadLowestProsecutorRisk(data,pModel)* PRECENT_CONVERT));
        metricsMap.put("records_affected_by_lowest_risk", String.valueOf(getPayloadRecordsAffectByRisk(data,pModel, getPayloadLowestProsecutorRisk(data,pModel))* PRECENT_CONVERT));
        metricsMap.put("average_prosecutor_risk", String.valueOf(getPayloadAverageProsecutorRisk(data,pModel)* PRECENT_CONVERT));
        metricsMap.put("highest_prosecutor_risk", String.valueOf(getPayloadHighestProsecutorRisk(data,pModel)* PRECENT_CONVERT));
        metricsMap.put("record_affected_by_highest_risk", String.valueOf(getPayloadRecordsAffectByRisk(data,pModel, getPayloadHighestProsecutorRisk(data,pModel))* PRECENT_CONVERT));
        metricsMap.put("estimated_prosecutor_risk", String.valueOf(getPayloadEstimatedProsecutorRisk(data,pModel)* PRECENT_CONVERT));
        metricsMap.put("estimated_journalist_risk", String.valueOf(getPayloadEstimatedJournalistRisk(data,pModel)* PRECENT_CONVERT));
        metricsMap.put("estimated_marketer_risk", String.valueOf(getPayloadEstimatedMarketerRisk(data,pModel)* PRECENT_CONVERT));
        metricsMap.put( "sample_uniques", String.valueOf(getPayloadSampleUniques(data,pModel)* PRECENT_CONVERT));
        metricsMap.put("population_uniques", String.valueOf(getPayloadPopulationUniques(data,pModel)* PRECENT_CONVERT));
        metricsMap.put("population_model", getPayloadPopulationModel(data,pModel).toString());
        metricsMap.put("quasi_identifiers", getPayloadQuasiIdentifiers(data).toString());
        return metricsMap;
    }
}
