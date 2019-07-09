package com.example.janche.common.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Component
@ConfigurationProperties(prefix = "janche")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationConfig implements IApplicationConfig, Serializable {
    private Map<Integer,String> userlevel;
    private Map<Integer, String> errorcodes;
    private Integer pagesize;
    private String sortcolumn;
    private sortType sorttype;
    private String origins;
    private Map<Integer,String> enableStatus;
    private Map<Integer,String> caseStatus;
    private Map<Integer,String> devStatus;
    private Map<Integer,String> devFactory;
    private Map<Integer,String> devType;
    private Map<Integer,String> devResolution;
    private Map<Integer,String> devCode;
    private Map<Integer,String> devFrameRate;
    private Map<Integer,String> devVideoStream;
    private Map<Integer,String> devVideoBitrate;
    private Map<Integer,String> alarmType;
    private String alarmInfo;
    private Map<Integer,String> logOperation;
    private Map<Integer,String> logType;
    private Long timeInterval;
    private Map<Integer,String> videoQuality;
    private Map<Integer,String> sexConfig;
    private Map<Integer,String> userState;
    private Map<Integer,String> videoClarity;
    private String videoAnalysisURL;


    public enum  sortType {
        ASC(Sort.Direction.ASC),
        DESC(Sort.Direction.DESC);
        @Getter
        private Sort.Direction sortType;

        sortType(Sort.Direction sortType) {
            this.sortType = sortType;
        }
    }

    public Integer getKeyByValue(Map<Integer, String> map, String value){
        Set<Map.Entry<Integer, String>> entrySet = map.entrySet();
        Iterator<Map.Entry<Integer, String>> it = entrySet.iterator();
        while (it.hasNext()){
            Map.Entry<Integer, String> entry = it.next();
            if (entry.getValue().equals(value)){
                return entry.getKey();
            }
        }
        return null;
    }

}
