import java.util.Arrays;
import java.util.Optional;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum TestEnum {
    TYPE1("1", "Additional1") {
        @Override
        public int build(final String input) {
            if (NumberUtils.isDigits(input)) {
                return Integer.parseInt(input) * 3 + 1;
            }
            return -1;
        }

        @Override
        public String parse(final int input) {
            return input + "YYYYY";
        }
    },
    TYPE2("2", "Additional2") {
        @Override
        public int build(final String input) {
            if (NumberUtils.isDigits(input)) {
                return Integer.parseInt(input) * 2 + 1;
            }
            return -1;
        }

        @Override
        public String parse(final int input) {
            return input + "XXXXX";
        }
    };

    private final String code;

    private final String description;

    public abstract int build(final String source);

    public abstract String parse(final int source);

    public static Optional<TestEnum> findByName(final String input) {
        return Optional.ofNullable(input)
                .map(StringUtils::isNotBlank)
                .flatMap(input1 ->
                        Arrays.stream(values())
                                .filter(item -> item.name().equals(input))
                                .findFirst()
                );
    }

    public static boolean existsByName(final String input) {
        return Optional.ofNullable(input)
                .map(StringUtils::isNotBlank)
                .map(input1 ->
                        Arrays.stream(values())
                                .anyMatch(item -> item.name().equals(input))
                ).orElse(false);
    }

    public static void main(final String[] args) {
        TestEnum.findByName("TYPE1")
                .ifPresent(enumVal -> {
                    switch (enumVal) {
                        case TYPE1:
                            int buildResult = TYPE1.build("2");
                            String parseResult = TYPE1.parse(10);
                            System.out.println("buildResult1 = " + buildResult);
                            System.out.println("parseResult1 = " + parseResult);
                            break;
                        case TYPE2:
                            buildResult = TYPE2.build("3");
                            parseResult = TYPE2.parse(20);
                            System.out.println("buildResult2 = " + buildResult);
                            System.out.println("parseResult2 = " + parseResult);
                            break;
                        default:
                            break;
                    }
                });
    }

}
