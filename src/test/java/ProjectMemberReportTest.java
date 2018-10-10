import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


/**
 * Created by no.pain.no.code@gmail.com
 * nopainnocode.tistory.com
 */
public class ProjectMemberReportTest {

    private StringBuilder input;
    private StringBuilder expectedOutput;

    @Before
    public void setUp() {
        input = new StringBuilder();
        expectedOutput = new StringBuilder();
    }

    @Test
    public void one_project_one_student() {
        input.append("AAA\r\n")
                .append("good01\r\n")
                .append("1\r\n")
                .append("0\r\n");

        canReadSystemIn(input.toString(), "AAA 1\r\n");
    }

    @Test
    public void one_project_two_students() {
        // given
        input.append("AAA\r\n")
                .append("good01\r\n")
                .append("good02\r\n")
                .append("1\r\n")
                .append("0\r\n");

        // when
        canReadSystemIn(input.toString(), "AAA 2\r\n");
        // then
    }

    @Test
    public void one_project_same_students_signed_up_twice() {
        // given
        input.append("AAA\r\n")
                .append("good01\r\n")
                .append("good01\r\n")
                .append("1\r\n")
                .append("0\r\n");

        // when
        canReadSystemIn(input.toString(), "AAA 1\r\n");
        // then
    }

    @Test
    public void two_projects_one_student() {
        input.append("AAA\r\n")
                .append("BBB\r\n")
                .append("good01\r\n")
                .append("1\r\n")
                .append("0\r\n");

        expectedOutput.append("BBB 1\r\n")
                .append("AAA 0\r\n");

        canReadSystemIn(input.toString(), expectedOutput.toString());
    }

    @Test
    public void two_projects_each_have_one_student() {
        input.append("AAA\r\n")
                .append("good01\r\n")
                .append("BBB\r\n")
                .append("good02\r\n")
                .append("1\r\n")
                .append("0\r\n");

        expectedOutput.append("AAA 1\r\n")
                .append("BBB 1\r\n");

        canReadSystemIn(input.toString(), expectedOutput.toString());
    }

    @Test
    public void two_projects_each_have_same_one_student() {
        input.append("AAA\r\n")
                .append("good01\r\n")
                .append("BBB\r\n")
                .append("good01\r\n")
                .append("1\r\n")
                .append("0\r\n");

        expectedOutput.append("AAA 0\r\n")
                .append("BBB 0\r\n");

        canReadSystemIn(input.toString(), expectedOutput.toString());
    }

    @Test
    public void many_projects_and_students() {
        input.append("UBQTS TXT\r\n")
                .append("tthumb\r\n")
                .append("LIVESPACE BLOGJAM\r\n")
                .append("philton\r\n")
                .append("aeinstein\r\n")
                .append("YOUBOOK\r\n")
                .append("j97lee\r\n")
                .append("sswxyzy\r\n")
                .append("j97lee\r\n")
                .append("aeinstein\r\n")
                .append("SKINUX\r\n")
                .append("1\r\n")
                .append("0\r\n");

        expectedOutput.append("YOUBOOK 2\r\n")
                .append("LIVESPACE BLOGJAM 1\r\n")
                .append("UBQTS TXT 1\r\n")
                .append("SKINUX 0\r\n");

        canReadSystemIn(input.toString(), expectedOutput.toString());
    }

    @Test
    public void two_template_with_many_projects_and_students() {
        input.append("AAA\r\n")
                .append("good01\r\n")
                .append("BBB\r\n")
                .append("good01\r\n")
                .append("1\r\n")
                .append("AAA\r\n")
                .append("good01\r\n")
                .append("CCC\r\n")
                .append("good02\r\n")
                .append("good02\r\n")
                .append("good03\r\n")
                .append("good04\r\n")
                .append("DDD\r\n")
                .append("good05\r\n")
                .append("good06\r\n")
                .append("good03\r\n")
                .append("good04\r\n")
                .append("good05\r\n")
                .append("1\r\n")
                .append("0\r\n");

        expectedOutput.append("AAA 0\r\n")
                .append("BBB 0\r\n")
                .append("DDD 2\r\n")
                .append("AAA 1\r\n")
                .append("CCC 1\r\n");

        canReadSystemIn(input.toString(), expectedOutput.toString());
    }

    private void canReadSystemIn(String inputData, String expected) {

        // given
        System.setIn(new ByteArrayInputStream(inputData.getBytes()));

        // when
        ProjectMemberReport projectMemberReport = new ProjectMemberReport();
        String returnValue = projectMemberReport.getProjectsNameAndCountOfMembers();

        // then
        assertThat(returnValue, is(expected));
    }
}
