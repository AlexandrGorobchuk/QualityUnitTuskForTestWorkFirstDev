package gorobchuk.alexandr.filter;

import java.time.Year;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import gorobchuk.alexandr.InitialConstant;
import gorobchuk.alexandr.ResponseType;
import gorobchuk.alexandr.add.waitingtimeline.WaitingTimeline;
import gorobchuk.alexandr.add.waitingtimeline.WaitingTimelineList;

public class ResultFilter {
    FilterDOP filterDop;
    List<WaitingTimeline> listTimeline;
    private String result;

    public ResultFilter(String filter) {
        String[] params = filter.split(" ");
        validateAddedObject(params);
        String[] temp = params[1].split("\\.");
        Integer service = setValue(temp, 0, "Service", InitialConstant.MAXSERVICE);
        Integer serviceVariations = setValue(temp, 1, "Service Variations", InitialConstant.MAXVARIATION);

        temp = params[2].split("\\.");
        Integer questionType = setValue(temp, 0, "Question Type", InitialConstant.MAXQUESTIONS);
        Integer questionCategory = setValue(temp, 1, "Question Category", InitialConstant.MAXQUESTIONSCATEGORY);
        Integer questionSubCategory = setValue(temp, 2, "Question SubCategory", InitialConstant.MAXQUESTIONSSUBCATEGORY);

        temp = params[4].split("-");
        Calendar dateStart = setCalendar(temp, 0);
        Calendar dateEnd = setCalendar(temp, 1);
        try {
            this.filterDop = new FilterDOP(service, serviceVariations,questionType,questionCategory, questionSubCategory,dateStart, dateEnd);
        } catch (Exception e){
            throw new IllegalArgumentException(e);
        }
        this.result = get(WaitingTimelineList.getList());

    }

    private String get (List<WaitingTimeline> list) {
        this.listTimeline = list;
        listTimeline = startFilter(this.listTimeline);
//        listTimeline.stream().forEach(x-> System.out.println(x));
        return setResult();

    }
    private String setResult(){
        if (listTimeline.size()<=0){
            return "-";
        }
        double summ = listTimeline.stream().mapToInt((x)->Integer.parseInt(x.getTime().toString())).sum() / (double) listTimeline.size();
        return String.valueOf(summ);
    }
    private List<WaitingTimeline> startFilter(List<WaitingTimeline> list){

        if (filterDop.getDataEnd() != null){
            list = list.stream()
                    .filter(x->x.getData().getTime().after(filterDop.getDataStart().getTime()))
                    .filter(x->x.getData().getTime().before(filterDop.getDataEnd().getTime()))
                    .collect(Collectors.toList());
        } else {
            list = list.stream()
                    .filter(x->x.getData().get(Calendar.YEAR) == filterDop.getDataStart().get(Calendar.YEAR))
                    .filter(x->x.getData().get(Calendar.MONTH) == filterDop.getDataStart().get(Calendar.MONTH))
                    .filter(x->x.getData().get(Calendar.DATE) == filterDop.getDataStart().get(Calendar.DATE))
                    .collect(Collectors.toList());
        }
        if (filterDop.getService()!=null) {
            list = list.stream()
                    .filter(x -> x.getService().equals(filterDop.getService()))
                    .collect(Collectors.toList());
        }
        if (filterDop.getServiceVariations() != null){
            list = list.stream()
                    .filter(x-> x.getServiceVariations().equals(filterDop.getServiceVariations()))
                    .collect(Collectors.toList());
        }
        if (filterDop.getQuestionsType() != null){
            list = list.stream()
                    .filter(x-> x.getQuestionsType().equals(filterDop.getQuestionsType()))
                    .collect(Collectors.toList());
        }
        if (filterDop.getQuestionsCategory()!= null){
            list = list.stream()
                    .filter(x-> x.getQuestionsCategory().equals(filterDop.getQuestionsCategory()))
                    .collect(Collectors.toList());
        }
        if (filterDop.getQuestionsSubCategory()!= null){
            list = list.stream()
                    .filter(x-> x.getQuestionsSubCategory().equals(filterDop.getQuestionsSubCategory()))
                    .collect(Collectors.toList());
        }

        return list;
    }
    private Integer setValue(String[] input, int position, String nameVariable, InitialConstant cons){

        String service;
        try {
            service = input[position];
        } catch (Exception e){
            return null;
        }
        if (input[position].equals("*")){
            return null;
        }
        Integer result;
        try {
            result = Integer.parseInt(service);
        } catch (Exception e){
            throw new IllegalArgumentException(e+"\n"+"Incorrect "+nameVariable+" value. Insert correct value variation: 3.1 or 5 | Incorrect "+nameVariable+": '" + Arrays.toString(input) + "'");
        }
        if (result > cons.getValue()){
            throw new IllegalArgumentException("Incorrect "+nameVariable+" value. Max value: " + cons.getValue() + ". Insert correct value "+nameVariable+". | Incorrect value: '" + Arrays.toString(input) + "'");
        }
        if (result < 1){
            throw new IllegalArgumentException("Incorrect "+nameVariable+" value. Min value: 1. Insert correct value "+nameVariable+". | Incorrect value: '" + Arrays.toString(input) + "'");
        }
        return result;
    }
    private Calendar setCalendar(String[] input, int position){
        String value;
        try {
            value = input[position];
        } catch (Exception e){
            return null;
        }
        String[] strings = value.split("\\.");
        if (strings.length != 3){
            throw new IllegalArgumentException("Incorrect Date. Insert correct in format dd.mm.yyyy | Incorrect value: '" + Arrays.toString(input) + "'");
        }
        Calendar calendar = new GregorianCalendar();
        Integer year;
        Integer month;
        Integer day;

        try{
            year = Integer.parseInt(strings[2]);
            month = Integer.parseInt(strings[1]);
            day = Integer.parseInt(strings[0]);
        } catch (Exception e){
            throw new IllegalArgumentException(e+"\n"+"Incorrect Date. Insert correct in format dd.mm.yyyy | Incorrect value: '" + Arrays.toString(input) + "'");
        }

        if (String.valueOf(year).toCharArray().length != 4 || year > Year.now().getValue()){
            throw new IllegalArgumentException("Incorrect years in Date. Insert correct date in format dd.mm.yyyy | Incorrect value: '" + Arrays.toString(input) + "'");
        }
        if (month > 12 || month < 1){
            throw new IllegalArgumentException("Incorrect month in Date. Insert correct in format dd.mm.yyyy | Incorrect value: '" + Arrays.toString(input) + "'");
        }
        YearMonth yearsMonth = YearMonth.of(year, month);
        if (yearsMonth.isAfter(YearMonth.now())){
            throw new IllegalArgumentException("Incorrect month in Date. Insert correct in format dd.mm.yyyy | Incorrect value: '" + Arrays.toString(input) + "'");
        }
        int daysOfMonth = yearsMonth.lengthOfMonth();

        if (day > daysOfMonth ||day < 0){
            throw new IllegalArgumentException("Incorrect days in Date. Max Days "+daysOfMonth+" in "+yearsMonth+ ".Insert correct in format dd.mm.yyyy | Incorrect value: '" + Arrays.toString(input) + "'");
        }

        calendar.set(year, month-1, day);
        if (calendar.after(GregorianCalendar.getInstance())){
            throw new IllegalArgumentException("Incorrect Date. Date object > date now. Insert correct in format dd.mm.yyyy | Incorrect value: '" + Arrays.toString(input)+ "'");
        }
        return calendar;
    }
//    private ResponseType setResponseType(String input){
//        if (input.equals("P")){
//            return ResponseType.P;
//        } else if (input.equals("N")){
//            return ResponseType.N;
//        } else{
//            throw new IllegalArgumentException("Incorrect ResponseType. Insert correct P or N | Incorrect value: '" + input + "'");
//        }
//    }
    private void validateAddedObject(String[] params){

        if (!params[0].equals("D")){
            throw new IllegalArgumentException("Added Object to list FilterDOP Must Be D (" + Arrays.toString(params) + ")" );
        }
    }

    @Override
    public String toString() {
        return "Filter{" +
                "filterDop=" + filterDop +
                '}';
    }

    public String getResult() {
        return result;
    }
}
