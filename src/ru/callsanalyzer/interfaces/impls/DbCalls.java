package ru.callsanalyzer.interfaces.impls;

import ru.callsanalyzer.interfaces.Calls;

import java.util.Date;
import java.util.List;

/**
 * Created by msv on 11.11.2015.
 */
public class DbCalls implements Calls {
    @Override
    public boolean insert(List<Calls> list) {
        return false;
    }

    @Override
    public boolean deleteByPeriod(Date date1, Date date2) {
        return false;
    }

    @Override
    public List<Calls> selectByPeriod(Date date1, Date date2) {
        return null;
    }
}
