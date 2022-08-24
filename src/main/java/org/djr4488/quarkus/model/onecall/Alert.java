package org.djr4488.quarkus.model.onecall;

import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.djr4488.quarkus.model.adapters.MinuteAdapter;
import org.djr4488.quarkus.model.adapters.SecondsAdapter;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@Data
public class Alert implements Serializable {
    @JsonbProperty("sender_name")
    private String senderName;
    @JsonbProperty
    private String event;
    @JsonbProperty
    @JsonbTypeAdapter(SecondsAdapter.class)
    private LocalDateTime start;
    @JsonbProperty
    @JsonbTypeAdapter(SecondsAdapter.class)
    private LocalDateTime end;
    @JsonbProperty
    private String description;
    @JsonbProperty
    private List<String> tags;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
