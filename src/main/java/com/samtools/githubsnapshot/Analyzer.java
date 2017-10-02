package com.samtools.githubsnapshot;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
class Analyzer {

    /**
     * Gistogram time set by hours
     *
     * @param list times
     * @return array with frequence times by 0...23 hours
     */
    List<Integer> timeDistribution(List<LocalDateTime> list) {

        if (list == null || list.size() == 0)
            return null;

        Integer hourDist[] = new Integer[24];

        for (int i = 0; i < hourDist.length; i++) {
            hourDist[i] = 0;
        }

        for (LocalDateTime t : list) {
            int hour = t.getHour();
            hourDist[hour]++;
        }
        //list.forEach(timeEvent->hourDist[timeEvent.getHour()]+=1);

        return Arrays.asList(hourDist);
    }

    /**
     * Return median of integer set.
     *
     * @param stats set of integer (disrtibution)
     * @return median
     */
    Integer median(List<Integer> stats) {

        if (stats == null || stats.size() == 0)
            return null;

        long total = stats.stream().mapToInt(i -> i).sum();

        Integer tmp_sum = 0;
        int median;
        for (median = 0; median < stats.size(); median++) {
            tmp_sum += stats.get(median);
            if (tmp_sum > total * 1.0 / 2)
                break;
        }

        return median;
    }
}

