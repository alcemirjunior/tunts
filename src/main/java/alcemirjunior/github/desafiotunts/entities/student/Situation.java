package alcemirjunior.github.desafiotunts.entities.student;

public enum Situation {
    APPROVED("Aprovado"),
    FINAL_EXAM("Exame Final"),
    GRADE_FAILED("Reprovado por nota"),
    ABSENCES_FAILED("Reprovado por falta");

    private final String description;

    Situation(String description) {
        this.description = description;
    }

    public  String getDescription() {
        return this.description;
    }
}
