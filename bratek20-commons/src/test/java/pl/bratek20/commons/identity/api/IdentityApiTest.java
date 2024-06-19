package pl.bratek20.commons.identity.api;

import org.junit.jupiter.api.Test;
import com.github.bratek20.architecture.tests.ApiTest;

import static org.assertj.core.api.Assertions.assertThat;
public abstract class IdentityApiTest extends ApiTest<IdentityApi> {
    @Test
    void shouldGenerateDifferentIdentityId() {
        IdentityId id1 = api.generate();
        IdentityId id2 = api.generate();
        IdentityId id3 = api.generate();

        assertThat(id1).isNotIn(id2, id3);
        assertThat(id2).isNotEqualTo(id3);
    }
}