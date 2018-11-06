import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProjectMemberReport {
    private List<List<Project>> bunchOfProjects;
    private List<Project> projects;
    private Map<String, String> alreadySignedUpStudents;
    private Project project;

    ProjectMemberReport() {
        bunchOfProjects = new ArrayList<>();
        projects = new ArrayList<>();
        alreadySignedUpStudents = new HashMap<>();
        project = new Project();
    }

    void setTemplateUp(String line) {
        if (isStudentNickname(line))
            dealWithStudentNickName(line);
        else
            initializeProject(line);
    }

    void addProjectAndInitialize() {
        bunchOfProjects.add(projects);
        initializeValuesForNewTemplate();
    }

    String getAllProjectsNameAndCount() {
        StringBuilder allProjectNameAndCount = new StringBuilder();
        bunchOfProjects.forEach(e -> allProjectNameAndCount.append(getProjectCountString(e)));
        return allProjectNameAndCount.toString();
    }

    private boolean isStudentNickname(String line) {
        return line.equals(line.toLowerCase());
    }

    private void dealWithStudentNickName(String studentNickName) {
        if (doesStudentAlreadySignedUp(studentNickName))
            removeStudent(project, studentNickName);
        else if (project.canStudentSignUpThenDo(studentNickName))
            alreadySignedUpStudents.put(studentNickName, project.getProjectName());
    }

    private void initializeProject(String projectName) {
        project = new Project();
        project.setProjectName(projectName);
        projects.add(project);
    }

    private void initializeValuesForNewTemplate() {
        projects = new ArrayList<>();
        alreadySignedUpStudents = new HashMap<>();
    }

    private boolean doesStudentAlreadySignedUp(String studentName) {
        return alreadySignedUpStudents.containsKey(studentName);
    }

    private void removeStudent(Project currentProject, String studentNickname) {
        List<Project> projects = this.projects.stream()
                .filter(e -> {
                    String duplicateProjectName = alreadySignedUpStudents.get(studentNickname);
                    return e.getProjectName().equals(duplicateProjectName)
                            && !currentProject.getProjectName().equals(duplicateProjectName);
                })
                .collect(Collectors.toList());
        if (projects.size() > 0) {
            projects.get(0).removeStudent(studentNickname);
        }
    }

    private String getProjectCountString(List<Project> projects) {
        StringBuilder returnValue = new StringBuilder();
        projects.stream()
                .sorted(Project::compareTo)
                .forEach(e ->
                        returnValue.append(e.getProjectName())
                                .append(" ")
                                .append(e.getCountOfSignedUpStudents())
                                .append("\r\n"));

        return returnValue.toString();
    }
}