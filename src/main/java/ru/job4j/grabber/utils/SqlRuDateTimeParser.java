package ru.job4j.grabber.utils;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Map;

public class SqlRuDateTimeParser implements DateTimeParser {

    private static final Map<String, String> MONTHS = Map.ofEntries(
            Map.entry("янв", "JANUARY"),
            Map.entry("фев", "FEBRUARY"),
            Map.entry("мар", "MARCH"),
            Map.entry("апр", "APRIL"),
            Map.entry("май", "MAY"),
            Map.entry("июн", "JUNE"),
            Map.entry("июл", "JULE"),
            Map.entry("авг", "AUGUST"),
            Map.entry("сен", "SEPTEMBER"),
            Map.entry("окт", "OCTOBER"),
            Map.entry("ноя", "NOVEMBER"),
            Map.entry("дек", "DECEMBER")
    );

    @Override
    public LocalDateTime parse(String parse) {
        String[] str = parse.replace(",", "").replace(":", " ").split(" ");
        return LocalDateTime.of(
                Integer.parseInt("20" + str[2]),
                Month.valueOf(MONTHS.get(str[1])),
                Integer.parseInt(str[0]),
                Integer.parseInt(str[3]),
                Integer.parseInt(str[4])
        );
    }
}