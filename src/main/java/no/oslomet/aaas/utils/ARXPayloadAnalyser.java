package no.oslomet.aaas.utils;

import org.deidentifier.arx.ARXPopulationModel;
import org.deidentifier.arx.Data;
import org.deidentifier.arx.risk.RiskModelPopulationUniqueness.PopulationUniquenessModel;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/***
 * Utility class analysing the tabular data set against re-identification risk
 */
@Component
public class ARXPayloadAnalyser {

    private static final int PRECENT_CONVERT = 100;

    /***
     * Returns a double that shows the lowest prosecutor re-identification risk found in the data set, based on
     * the population model that is defined.
     * @param data tabular data set to be analysed against re-identification risk
     * @param pModel population model for our data set
     * @return       lowest risk found in the data set
     */
    public double getPayloadLowestProsecutorRisk(Data data, ARXPopulationModel pModel){
        return data.getHandle()
                .getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getLowestRisk();
    }

    /***
     * Returns a double that shows the amount of records/fields that are affected by a specific amount of risk.
     * @param data tabular data set to be analysed against re-identification risk
     * @param pModel population model for our data set that defines the population size and sampling fraction
     * @param risk specific amount of risk that affects one or more records
     * @return       records affect by a specific amount of risk
     */
    public double getPayloadRecordsAffectByRisk(Data data, ARXPopulationModel pModel, double risk){
        return data.getHandle()
                .getRiskEstimator(pModel)
                .getSampleBasedRiskDistribution()
                .getFractionOfRecordsAtRisk(risk);
    }

    /***
     * Returns a double that shows the average prosecutor re-identification risk found in the data set, based on
     * the population model that is defined.
     * @param data tabular data set to be analysed against re-identification risk
     * @param pModel population model for our data set that defines the population size and sampling fraction
     * @return       average risk found in the data set
     */
    public Double getPayloadAverageProsecutorRisk(Data data, ARXPopulationModel pModel){
        return data.getHandle()
                .getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getAverageRisk();
    }

    /***
     * Returns a double that shows the highest prosecutor re-identification risk found in the data set, based on
     * the population model that is defined.
     * @param data tabular data set to be analysed against re-identification risk
     * @param pModel population model for our data set that defines the population size and sampling fraction
     * @return       highest risk found in the data set
     */
    public double getPayloadHighestProsecutorRisk(Data data, ARXPopulationModel pModel){
        return data.getHandle()
                .getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getHighestRisk();
    }

    /***
     * Returns a double that shows the estimated prosecutor re-identification risk found in the data set, based on
     * the population model that is defined.
     * @param data tabular data set to be analysed against re-identification risk
     * @param pModel population model for our data set that defines the population size and sampling fraction
     * @return       estimated prosecutor risk found in the data set
     */
    public double getPayloadEstimatedProsecutorRisk(Data data, ARXPopulationModel pModel){
        return data.getHandle()
                .getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getEstimatedProsecutorRisk();
    }

    /***
     * Returns a double that shows the estimated journalist re-identification risk found in the data set, based on
     * the population model that is defined.
     * @param data tabular data set to be analysed against re-identification risk
     * @param pModel population model for our data set that defines the population size and sampling fraction
     * @return       estimated journalist risk found in the data set
     */
    public double getPayloadEstimatedJournalistRisk(Data data, ARXPopulationModel pModel){
        return data.getHandle()
                .getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getEstimatedJournalistRisk();
    }

    /***
     * Returns a double that shows the estimated marketer re-identification risk found in the data set, based on
     * the population model that is defined.
     * @param data tabular data set to be analysed against re-identification risk
     * @param pModel population model for our data set that defines the population size and sampling fraction
     * @return       estimated marketer risk found in the data set
     */
    public double getPayloadEstimatedMarketerRisk(Data data, ARXPopulationModel pModel){
        return data.getHandle()
                .getRiskEstimator(pModel)
                .getSampleBasedReidentificationRisk()
                .getEstimatedMarketerRisk();
    }

    /***
     * Returns a double that shows the amount of unique records/fields in the data set.
     * @param data tabular data set to be analysed against re-identification risk
     * @param pModel population model for our data set that defines the population size and sampling fraction
     * @return      amount of unique records/fields found in the data set
     */
    public double getPayloadSampleUniques(Data data, ARXPopulationModel pModel){
        return data.getHandle()
                .getRiskEstimator(pModel)
                .getSampleBasedUniquenessRisk()
                .getFractionOfUniqueTuples();
    }

    /***
     * Returns a double that shows the amount of unique records/fields in the data set, which are also unique
     * within the underlying population model from which the data is a part of.
     * @param data tabular data set to be analysed against re-identification risk
     * @param pModel population model for our data set that defines the population size and sampling fraction
     * @return      amount of unique records/fields found in the data set which are also unique in the population model
     */
    public double getPayloadPopulationUniques(Data data, ARXPopulationModel pModel){
        return data.getHandle()
                .getRiskEstimator(pModel)
                .getPopulationBasedUniquenessRisk()
                .getFractionOfUniqueTuples(getPayloadPopulationModel(data,pModel));
    }

    /***
     * Returns the method name used to estimating population uniqueness that assumes that the data set is a uniform
     * sample of the population.
     * @param data tabular data set to be analysed against re-identification risk
     * @param pModel population model for our data set that defines the population size and sampling fraction
     * @return       population model name
     */
    public PopulationUniquenessModel getPayloadPopulationModel(Data data, ARXPopulationModel pModel){
        return data.getHandle()
                .getRiskEstimator(pModel)
                .getPopulationBasedUniquenessRisk()
                .getPopulationUniquenessModel();
    }

    /***
     * Returns a set of strings that contains field names from the data set that has an attribute type of
     * quasi-identifying
     * @param data tabular data set to be analysed against re-identification risk
     * @return      set of strings containing quasi-identifying fields
     */
    public Set<String> getPayloadQuasiIdentifiers(Data data){
        return data.getDefinition().getQuasiIdentifyingAttributes();
    }

    /***
     * Returns a map containing the different statistics found from the data set.
     * @param data tabular data set to be analysed against re-identification risk
     * @param pModel population model for our data set that defines the population size and sampling fraction
     * @return a hash map containing data set re-identification statistics
     */
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
