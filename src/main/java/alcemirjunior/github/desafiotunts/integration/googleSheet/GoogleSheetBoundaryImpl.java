package alcemirjunior.github.desafiotunts.integration.googleSheet;

import alcemirjunior.github.desafiotunts.entities.student.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class GoogleSheetBoundaryImpl implements GoogleSheetBoundary {

    @Value("${spreadsheet.id}")
    private String spreadsheetId;
    @Value("${read.range}")
    private String readRange;

    @Autowired
    private GoogleSheetProvider googleSheetProvider;

    @Override
    public List<Student> getStudentFromSheet() throws IOException, GeneralSecurityException {
        ValueRange response = googleSheetProvider.getIntegration()
                .spreadsheets()
                .values()
                .get(spreadsheetId, readRange)
                .execute();
        List<List<Object>> values = response.getValues();
        log.info("Os dados da tabela serao carregados para lista de estudantes em memoria");
        return values
                .stream()
                .map(row -> new Student(
                        Long.parseLong(row.get(0).toString()),
                        row.get(1).toString(),
                        Integer.parseInt(row.get(2).toString()),
                        Integer.parseInt(row.get(3).toString()),
                        Integer.parseInt(row.get(4).toString()),
                        Integer.parseInt(row.get(5).toString())
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void updateStudentOnSheet(List<Student> students) throws IOException, GeneralSecurityException {
        log.info("Situacao e nota para aprovacao final serao setados");
        for (int i = 1; i <= students.size(); i++) {
            String position = "G".concat(Integer.toString(i + 3));
            ValueRange body = new ValueRange().setValues(
                    List.of(
                            Arrays.asList(
                                    students.get(i - 1).getSituation().getDescription(),
                                    students.get(i - 1).getRequiredScore().toString()
                            )
                    )
            );
            googleSheetProvider.getIntegration().spreadsheets().values()
                    .update(spreadsheetId, position, body)
                    .setValueInputOption("RAW")
                    .execute();
            log.info("Estudante: {} - Situacao: {} - Nota para aprovacao final: {}",
                    students.get(i - 1).getName(),
                    students.get(i - 1).getSituation().getDescription(),
                    students.get(i - 1).getRequiredScore().toString()
            );
        }
    }
}
