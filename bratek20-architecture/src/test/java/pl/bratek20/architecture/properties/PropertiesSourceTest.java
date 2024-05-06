package pl.bratek20.architecture.properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import pl.bratek20.architecture.properties.api.PropertiesSource;
import pl.bratek20.architecture.properties.api.PropertyKey;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class PropertiesSourceTest {

    protected record SomeProperty(String value, String otherValue) { }
    protected record OtherProperty(String value) { }

    protected static PropertyKey SOME_PROPERTY_NAME = new PropertyKey("someProperty");
    protected static SomeProperty EXPECTED_SOME_PROPERTY = new SomeProperty("some value", "other value");

    protected static PropertyKey SOME_PROPERTY_LIST_NAME = new PropertyKey("somePropertyList");
    protected static List<SomeProperty> EXPECTED_SOME_PROPERTY_LIST = List.of(
        new SomeProperty("some value 1", "x"),
        new SomeProperty("some value 2", "x")
    );

    protected abstract PropertiesSource createAndSetupSource();
    protected abstract String expectedName();

    private PropertiesSource source;

    @BeforeEach
    void beforeEach() {
        source = createAndSetupSource();
    }

    @Test
    void shouldReturnExpectedName() {
        assertThat(source.getName().getValue()).isEqualTo(expectedName());
    }

    @Test
    void shouldGetExpectedProperty() {
        assertThat(source.get(SOME_PROPERTY_NAME, SomeProperty.class))
            .isEqualTo(EXPECTED_SOME_PROPERTY);
    }

    @Test
    void shouldGetExpectedPropertyList() {
        assertThat(source.getList(SOME_PROPERTY_LIST_NAME, SomeProperty.class))
            .isEqualTo(EXPECTED_SOME_PROPERTY_LIST);
    }

    @Nested
    class HasOfTypeScope {
        @Test
        void shouldReturnTrueForExistingProperty() {
            assertThat(source.hasOfType(SOME_PROPERTY_NAME, SomeProperty.class))
                .isTrue();
        }

        @Test
        void shouldReturnFalseForNotExistingProperty() {
            assertThat(source.hasOfType(new PropertyKey("notExisting"), SomeProperty.class))
                .isFalse();
        }

        @Test
        void shouldReturnFalseForPropertyOfDifferentType() {
            assertThat(source.hasOfType(SOME_PROPERTY_NAME, OtherProperty.class))
                .isFalse();
        }
    }
}