package gorobchuk.alexandr.filter;

import java.util.Calendar;

public class FilterDOP {
    private final Integer service;
    private final Integer serviceVariations;
    private final Integer questionsType;
    private final Integer questionsCategory;
    private final Integer questionsSubCategory;
    private final Calendar dataStart;
    private final Calendar dataEnd;

    public FilterDOP(Integer service, Integer serviceVariations, Integer questionsType, Integer questionsCategory, Integer questionsSubCategory, Calendar dataStart, Calendar dataEnd) {
        this.service = service;
        this.serviceVariations = serviceVariations;
        this.questionsType = questionsType;
        this.questionsCategory = questionsCategory;
        this.questionsSubCategory = questionsSubCategory;
        this.dataStart = dataStart;
        this.dataEnd = dataEnd;
    }

    public Integer getService() {
        return service;
    }

    public Integer getServiceVariations() {
        return serviceVariations;
    }

    public Integer getQuestionsType() {
        return questionsType;
    }

    public Integer getQuestionsCategory() {
        return questionsCategory;
    }

    public Integer getQuestionsSubCategory() {
        return questionsSubCategory;
    }

    public Calendar getDataStart() {
        return dataStart;
    }

    public Calendar getDataEnd() {
        return dataEnd;
    }

    @Override
    public String toString() {
        String dataEndNull;
        if (dataEnd == null){
            dataEndNull = null;
        } else {
            dataEndNull = dataEnd.get(Calendar.DATE) +"."+ dataEnd.get(Calendar.MONTH) +"."+ dataEnd.get(Calendar.YEAR);
        }

        return "FilterDOP{" +
                "service=" + service +
                ", serviceVariations=" + serviceVariations +
                ", questionsType=" + questionsType +
                ", questionsCategory=" + questionsCategory +
                ", questionsSubCategory=" + questionsSubCategory +
                ", dataStart=" + dataStart.get(Calendar.DATE) +"."+ dataStart.get(Calendar.MONTH) +"."+ dataStart.get(Calendar.YEAR) +
                ", dataEnd=" + dataEndNull +
                '}';
    }
}
