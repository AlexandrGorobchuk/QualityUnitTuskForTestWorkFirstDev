package gorobchuk.alexandr.add.waitingtimeline;

import java.util.Calendar;

import gorobchuk.alexandr.ResponseType;

public class WaitingTimeline {
    private final Integer service;
    private final Integer serviceVariations;
    private final Integer questionsType;
    private final Integer questionsCategory;
    private final Integer questionsSubCategory;
    private final ResponseType responseType;
    private final Calendar data;
    private final Integer time;

    protected WaitingTimeline(Integer service, Integer serviceVariations, Integer questionsType, Integer questionsCategory, Integer questionsSubCategory, ResponseType responseType, Calendar data, Integer time) {
        this.service = service;
        this.serviceVariations = serviceVariations;
        this.questionsType = questionsType;
        this.questionsCategory = questionsCategory;
        this.questionsSubCategory = questionsSubCategory;
        this.responseType = responseType;
        this.data = data;
        this.time = time;
    }


    @Override
    public String toString() {
        return "WaitingTimeline{" +
                "service=" + service +
                ", serviceVariations=" + serviceVariations +
                ", questionsType=" + questionsType +
                ", questionsCategory=" + questionsCategory +
                ", questionsSubCategory=" + questionsSubCategory +
                ", responseType=" + responseType +
                ", data=" + data.get(Calendar.DATE)+"."+(data.get(Calendar.MONTH)+1)+"."+data.get(Calendar.YEAR) +
                ", time=" + time +
                '}';
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
    public ResponseType getResponseType() {
        return responseType;
    }
    public Calendar getData() {
        return data;
    }

    public Integer getTime() {
        return time;
    }
}
