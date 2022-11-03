package alcemirjunior.github.desafiotunts.integration.googleSheet;

import alcemirjunior.github.desafiotunts.entities.student.Student;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public interface GoogleSheetBoundary {

    List<Student> getStudentFromSheet() throws IOException, GeneralSecurityException;

    void updateStudentOnSheet(List<Student> students) throws IOException, GeneralSecurityException;
}
