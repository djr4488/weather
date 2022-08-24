package org.djr4488.quarkus.model.adapters;

import javax.json.bind.adapter.JsonbAdapter;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class MinuteAdapter implements JsonbAdapter<LocalDateTime, Long> {
    @Override
    public Long adaptToJson(LocalDateTime localDateTime) throws Exception {
        return localDateTime.toEpochSecond(ZoneOffset.UTC);
    }

    @Override
    public LocalDateTime adaptFromJson(Long epochMinutes) throws Exception {
        return LocalDateTime.ofEpochSecond(epochMinutes * 100 , 0, ZoneOffset.UTC);
    }
}
