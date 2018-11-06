import java.util.Scanner;

/**
 * Created by no.pain.no.code@gmail.com
 * nopainnocode.tistory.com
 */
public class ProjectMemberReporter {

    private final ProjectMemberReport projectMemberReport = new ProjectMemberReport();
    private String line;

    ProjectMemberReporter() {
    }

    public String getProjectsNameAndCountOfMembers() {
        Scanner scanner = new Scanner(System.in);

        while (isNextLineExist(scanner))
            if (isEndOfOneTemplate())
                projectMemberReport.addProjectAndInitialize();
            else
                projectMemberReport.setTemplateUp(line);

        return projectMemberReport.getAllProjectsNameAndCount();
    }

    private boolean isNextLineExist(Scanner scanner) {
        return scanner.hasNext()
                && !(line = scanner.nextLine()).equals("0");
    }

    private boolean isEndOfOneTemplate() {
        return "1".equals(line);
    }
}
