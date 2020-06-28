package com.cjd.dwptest.service;

import org.springframework.stereotype.Component;

import com.cjd.dwptest.domain.Point;
import com.cjd.dwptest.domain.Distance;

@Component
public interface DistanceCalculator {

    public Distance getDistanceBetweenPoints(final Point p1, final Point p2);

}