package gorobchuk.alexandr.add.waitingtimeline;

import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import gorobchuk.alexandr.InitialConstant;
import gorobchuk.alexandr.ResponseType;

public class WaitingTimelineList {
    private static List<WaitingTimeline> list = new ArrayList<>();

    public static List<WaitingTimeline> getList() {
        return list;
    }

    public static void addList(String input){
        String[] params = input.split(" ");
        validateAddedObject(params);

        String[] temp = params[1].split("\\.");
        Integer service = setValue(temp, 0, "Service", InitialConstant.MAXSERVICE);
        Integer serviceVariations = setValue(temp, 1, "Service Variations", InitialConstant.MAXVARIATION);

        temp = params[2].split("\\.");
        Integer questionType = setValue(temp, 0, "Question Type", InitialConstant.MAXQUESTIONS);
        Integer questionCategory = setValue(temp, 1, "Question Category", InitialConstant.MAXQUESTIONSCATEGORY);
        Integer questionSubCategory = setValue(temp, 2, "Question SubCategory", InitialConstant.MAXQUESTIONSSUBCATEGORY);

        ResponseType responseType = setResponseType(params[3]);
        Calendar date = setCalendar(params[4]);
        Integer time = setTime(params[5]);

        list.add(new WaitingTimeline(service, serviceVariations, questionType, questionCategory, questionSubCategory, responseType, date, time));
    }

    private static Integer setValue(String[] input, int position, String nameVariable, InitialConstant cons){
        String service;
        try {
            service = input[position];
        } catch (Exception e){
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
    private static Integer setTime (String input){
        Integer result;
        try {
            result = Integer.parseInt(input);
        } catch (Exception e){
            throw new IllegalArgumentException(e+"\n"+"Incorrect time value. Insert correct time: 84, 3. value: '" + input + "'");
        }
        if (result<1){
            throw new IllegalArgumentException("Incorrect time value. Min correct time = 1. value: '" + input + "'");
        }
        return result;
    }
    private static Calendar setCalendar(String value){
        String[] strings = value.split("\\.");
        if (strings.length != 3){
            throw new IllegalArgumentException("Incorrect Date. Insert correct in format dd.mm.yyyy | Incorrect value: '" + value + "'");
        }
        Integer[] numbers = new Integer[strings.length];
        Calendar calendar = new GregorianCalendar();

        try{
            for (int i = 0; i < strings.length; i++) {
                numbers[i] = Integer.parseInt(strings[i]);
            }
        } catch (Exception e){
            throw new IllegalArgumentException(e+"\n"+"Incorrect Date. Insert correct in format dd.mm.yyyy | Incorrect value: '" + value + "'");
        }

        if (String.valueOf(numbers[2]).toCharArray().length != 4 || numbers[2] > Year.now().getValue()){
            throw new IllegalArgumentException("Incorrect years in Date. Insert correct date in format dd.mm.yyyy | Incorrect value: '" + value + "'");
        }
        if (numbers[1]>12 || numbers[1]<1){
            throw new IllegalArgumentException("Incorrect month in Date. Insert correct in format dd.mm.yyyy | Incorrect value: '" + value + "'");
        }
        YearMonth yearsMonth = YearMonth.of(numbers[2], numbers[1]);
        if (yearsMonth.isAfter(YearMonth.now())){
            throw new IllegalArgumentException("Incorrect month in Date. Insert correct in format dd.mm.yyyy | Incorrect value: '" + value + "'");
        }
        int daysOfMonth = yearsMonth.lengthOfMonth();

        if (numbers[0] > daysOfMonth ||numbers[0] < 0){
            throw new IllegalArgumentException("Incorrect days in Date. Max Days "+daysOfMonth+" in "+yearsMonth+ ".Insert correct in format dd.mm.yyyy | Incorrect value: '" + value + "'");
        }

        calendar.set(numbers[2], numbers[1]-1, numbers[0]);
        if (calendar.after(GregorianCalendar.getInstance())){
            throw new IllegalArgumentException("Incorrect Date. Date object > date now. Insert correct in format dd.mm.yyyy | Incorrect value: '" + value+ "'");
        }
        return calendar;
    }
    private static ResponseType setResponseType(String input){
            if (input.equals("P")){
                return ResponseType.P;
            } else if (input.equals("N")){
                return ResponseType.N;
            } else{
                throw new IllegalArgumentException("Incorrect ResponseType. Insert correct P or N | Incorrect value: '" + input + "'");
            }
    }
    private static void validateAddedObject(String[] params){

        if (!params[0].equals("C")){
            throw new IllegalArgumentException("Added Object to list WaitingTimelineList Must Be C (" + Arrays.toString(params) + ")" );
        }

        ResponseType responseType;
        if (!params[3].equals("P") && !params[3].equals("N")) {
            throw new IllegalArgumentException("Response Type Must Be N/P (" + Arrays.toString(params) + ")");
        }
    }
}
