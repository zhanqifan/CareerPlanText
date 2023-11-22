package com.ruoyi.sms.factory;

import cn.hutool.core.lang.WeightRandom;
import cn.hutool.core.util.RandomUtil;
import org.omg.CORBA.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SmsFactory {
    private final Map<String, ISmsTemplate> smsTemplateMap;
    private final WeightRandom<ISmsTemplate> smsTemplateWeightRandom;;

    @Autowired
    SmsFactory(Map<String, ISmsTemplate> smsTemplateMap) {
        this.smsTemplateMap = smsTemplateMap;
        List<WeightRandom.WeightObj<ISmsTemplate>> smsTemplateWeightMap  = new ArrayList<WeightRandom.WeightObj<ISmsTemplate>>();
        for (ISmsTemplate smsTemplate : smsTemplateMap.values()) {
            smsTemplateWeightMap.add(new WeightRandom.WeightObj<>(smsTemplate, smsTemplate.getWeight()));
        }
        this.smsTemplateWeightRandom = RandomUtil.weightRandom(smsTemplateWeightMap);
    }

    public ISmsTemplate getSmsFactory(String provider) {
        ISmsTemplate smsTemplate = smsTemplateMap.get(provider);
        if (smsTemplate == null) {
//            throw new SystemException("暂不支持该类型方法");
        }
        return smsTemplate;
    }

    public ISmsTemplate getRandomSmsFactory() {
        return smsTemplateWeightRandom.next();
    }
}
