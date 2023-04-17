import java.util.Arrays;
import java.util.Optional;
import java.util.function.BiFunction;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

@FunctionalInterface
interface MyInterface {
    void run();
}

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum TestEnum implements MyInterface{
    TYPE1("1", "Additional1", Integer::sum) {
        @Override
        public void run() {
            System.out.println("Run TYPE1");
        }
        @Override
        public int build(final String input) {
            if (NumberUtils.isDigits(input)) {
                return Integer.parseInt(input) * 3 + 1;
            }
            return -1;
        }
    },
    TYPE2("2", "Additional2", (x, y) -> x + y * 2) {
        @Override
        public void run() {
            System.out.println("Run TYPE2");
        }

        @Override
        public int build(final String input) {
            if (NumberUtils.isDigits(input)) {
                return Integer.parseInt(input) * 2 + 1;
            }
            return -1;
        }
    };
    private final String code;
    private final String description;
    private final BiFunction<Integer, Integer, Integer> operation;
    public int apply(final int x, final int y) {
        return this.operation.apply(x, y);
    }
    public abstract int build(final String source);
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
                            int applyResult = TYPE1.apply(1, 2);
                            TYPE1.run();
                            System.out.println("buildResult1 = " + buildResult);
                            System.out.println("applyResult = " + applyResult);
                            break;
                        case TYPE2:
                            buildResult = TYPE2.build("2");
                            applyResult = TYPE1.apply(1, 2);
                            TYPE2.run();
                            System.out.println("buildResult2 = " + buildResult);
                            System.out.println("applyResult = " + applyResult);
                            break;
                        default:
                            break;
                    }
                });
    }

}