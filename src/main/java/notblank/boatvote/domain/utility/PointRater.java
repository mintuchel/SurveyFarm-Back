package notblank.boatvote.domain.utility;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class PointRater {
    // 주관식이 많을수록 많은 점수를 줘야함
    // 남은기간이 짧을수록 많은 포인트 줘야함
    // 남은기간이 길면 적은 포인트 줘야함
    // 인원수가 많을수록 적은 포인트
    // 인원수가 적을수록 많은 포인트

    public int setPoint(int maxHeadCnt, int currentHeadCnt, LocalDateTime createdAt, LocalDateTime endAt, int duration) {
        int headCntLeft = maxHeadCnt - currentHeadCnt;
        long daysRemaining = Duration.between(createdAt, endAt).toDays();

        double points = 100*(headCntLeft/maxHeadCnt)*(daysRemaining/duration);
        return (int) points;
    }
}
