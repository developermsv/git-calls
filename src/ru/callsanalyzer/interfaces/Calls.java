package ru.callsanalyzer.interfaces;

import java.util.Date;
import java.util.List;

/**
 * Created by msv on 11.11.2015.
 */
public interface Calls {
    boolean insert(List<Calls> list);
    boolean deleteByPeriod(Date date1, Date date2);
    List <Calls> selectByPeriod(Date date1, Date date2);

}
