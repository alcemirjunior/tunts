package alcemirjunior.github.desafiotunts.integration.googleSheet;

import alcemirjunior.github.desafiotunts.entities.student.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;


@RestController
@RequestMapping("api/v1/tunts-challenge")
public record GoogleSheetController (GoogleSheetBoundary googleSheetBoundary) {

    @GetMapping
    public void executeTuntsChallenge() throws GeneralSecurityException, IOException {
        List<Student> students = googleSheetBoundary.getStudentFromSheet();
        students.forEach(Student::calculateFinalStudentSituation);
        googleSheetBoundary.updateStudentOnSheet(students);
    }

}
