package kr.co.vibevillage.experienceAndReviewBoard.domain;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Data
public class Like {
    private Long rId;
    private Long uNo;
    private java.sql.Timestamp lCreatedAt;

}
