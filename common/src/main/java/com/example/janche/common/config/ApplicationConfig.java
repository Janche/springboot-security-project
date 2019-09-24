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
    private sortType sorttype;
    private String[] origins;
    private Map<Integer,String> logOperation;
    private Map<Integer,String> logType;



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
