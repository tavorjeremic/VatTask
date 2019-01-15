
package com.tj.vats.task.model;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.style.ToStringCreator;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "code",
    "country_code",
    "periods"
})
public class Rate {

    @JsonProperty("name")
    private String name;
    @JsonProperty("code")
    private String code;
    @JsonProperty("country_code")
    private String countryCode;
    @JsonProperty("periods")
    private List<Period> periods = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    @JsonProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

    @JsonProperty("country_code")
    public String getCountryCode() {
        return countryCode;
    }

    @JsonProperty("country_code")
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @JsonProperty("periods")
    public List<Period> getPeriods() {
        return periods;
    }

    @JsonProperty("periods")
    public void setPeriods(List<Period> periods) {
        this.periods = periods;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
    
    public Rates getRatesByDate(Date date) {
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    	String dateString = df.format(date);
    	Period p = this.periods.stream()
    	.filter(period -> (period.getEffectiveFrom().compareTo(dateString)<=0))
    	.max(Comparator.comparing(Period::getEffectiveFrom))
    	.orElse(null);
    	if(p != null) {
    		return p.getRates();
    	}else {
    		return null;
    	}
    }

    @Override
    public String toString() {
        return new ToStringCreator(this).append("name", name).append("code", code).append("countryCode", countryCode).append("periods", periods).append("additionalProperties", additionalProperties).toString();
    }

}
