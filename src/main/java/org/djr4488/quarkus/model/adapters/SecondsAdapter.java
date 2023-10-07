package org.djr4488.quarkus.model.adapters;

import javax.json.bind.adapter.JsonbAdapter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.time.zone.ZoneOffsetTransition;
import java.util.Locale;

public class SecondsAdapter implements JsonbAdapter<LocalDateTime, Long> {
    @Override
    public Long adaptToJson(LocalDateTime localDateTime) throws Exception {
        return localDateTime.toEpochSecond(ZoneOffset.systemDefault().getRules().getOffset(LocalDateTime.now()));
    }

    @Override
    public LocalDateTime adaptFromJson(Long epochSeconds) throws Exception {
        return LocalDateTime.ofEpochSecond(epochSeconds, 0, ZoneOffset.systemDefault().getRules().getOffset(LocalDateTime.now()));
    }
}
