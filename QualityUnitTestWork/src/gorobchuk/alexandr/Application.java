package gorobchuk.alexandr;

import gorobchuk.alexandr.add.waitingtimeline.WaitingTimelineList;
import gorobchuk.alexandr.filter.ResultFilter;

public class Application {

    public static void main(String[] args) {
        WaitingTimelineList.addList("C 1.1 8.15.1 N 15.10.2012 83");
        WaitingTimelineList.addList("C 1 10.1 N 01.12.2012 65");
        WaitingTimelineList.addList("C 1.1 5.5.1 P 01.11.2012 117");
        WaitingTimelineList.addList("C 3 10.2 N 02.01.2012 100");
        
//        WaitingTimelineList.getList().stream().forEach(x->System.out.println(x));
        
        ResultFilter filter = new ResultFilter("D 1 * P 29.02.2012-01.12.2012");
        System.out.println(filter.getResult());
    }

}
