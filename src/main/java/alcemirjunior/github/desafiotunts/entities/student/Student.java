package alcemirjunior.github.desafiotunts.entities.student;

import lombok.*;

import static alcemirjunior.github.desafiotunts.entities.student.Situation.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Student {

    private Long registration;
    private String name;
    private Integer absencesClass;
    private Integer P1Score;
    private Integer P2Score;
    private Integer P3Score;
    private Situation situation;
    private Integer requiredScore;

    public Student(Long registration, String name, Integer absencesClass, Integer P1Score, Integer P2Score, Integer P3Score) {
        this.registration = registration;
        this.name = name;
        this.absencesClass = absencesClass;
        this.P1Score = P1Score;
        this.P2Score = P2Score;
        this.P3Score = P3Score;
    }

    public void calculateFinalStudentSituation() {
        this.setSituation();
        this.setRequiredScore();
    }

    private void setSituation() {
        if (absencesClass > 15) {
            situation = ABSENCES_FAILED;
        } else {
            double media = (P1Score + P2Score + P3Score) / 3.0;
            if (media >= 70) situation = APPROVED;
            else if (media >= 50) situation = FINAL_EXAM;
            else situation = GRADE_FAILED;
        }
    }

    private void setRequiredScore() {
        if (situation.equals(FINAL_EXAM)) {
            requiredScore = (int) (100.0 - (P1Score + P2Score + P3Score) / 3.0);
        } else {
            requiredScore = 0;
        }
    }
}