package lotto.domain;

import lotto.utils.LottoMachine;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class LottoNumbers {

    private static final int LOTTO_NUMBERS_SIZE = 6;
    private static final int MATCH_COUNT_MIN = 3;
    private static final int MATCH_COUNT_MAX = 6;

    private final Set<LottoNumber> numbers;

    public LottoNumbers() {
        this(generatedLottoNumbers());
    }

    public LottoNumbers(Set<LottoNumber> numbers) {
        if (numbers.size() != LOTTO_NUMBERS_SIZE) {
            throw new RuntimeException("로또 번호의 개수는 6개여야 합니다.");
        }
        this.numbers = numbers;
    }

    private static Set<LottoNumber> generatedLottoNumbers() {
        Set<LottoNumber> lottoNumbers = new HashSet<>();
        List<Integer> drawnLottoNumbers = new LottoMachine().drawnLottoNumbers();

        for (int drawnLottoNumber : drawnLottoNumbers) {
            lottoNumbers.add(new LottoNumber(drawnLottoNumber));
        }
        return lottoNumbers;
    }

    boolean isNotWinningMatchCountWith(LottoNumbers numbers) {
        return matchCount(numbers) < MATCH_COUNT_MIN || matchCount(numbers) > MATCH_COUNT_MAX;
    }

    public int matchCount(LottoNumbers numbers) {
        List<LottoNumber> matchNumbers = this.numbers.stream()
                .filter(v -> numbers.value().stream()
                        .anyMatch(Predicate.isEqual(v))).collect(Collectors.toList());
        return matchNumbers.size();
    }

    boolean isMatchWith(LottoNumber number) {
        return numbers.stream().anyMatch(v -> v.value() == number.value());
    }

    public Set<LottoNumber> value() {
        return this.numbers;
    }

    @Override
    public String toString() {
        return numbers.toString();
    }
}
