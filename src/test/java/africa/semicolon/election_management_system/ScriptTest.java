package africa.semicolon.election_management_system;

import africa.semicolon.election_management_system.data.repositories.VoterRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql(scripts = {"/db/data.sql"})
public class ScriptTest {
    @Autowired
    private VoterRepository voterRepository;

    @Test
    @DisplayName("testing dummy data")
    public void testScript() {
        System.out.println(voterRepository.findAll());
    }

}
