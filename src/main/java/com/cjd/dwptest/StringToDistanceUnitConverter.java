package com.cjd.dwptest;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.cjd.dwptest.domain.DistanceUnit;

@Component
public class StringToDistanceUnitConverter implements Converter<String, DistanceUnit> {

	@Override
    public DistanceUnit convert(String source) {
        return DistanceUnit.valueOf(source.toUpperCase());
    }
}

