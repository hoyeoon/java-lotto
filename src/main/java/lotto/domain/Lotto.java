package lotto.domain;

import lotto.utils.LottoGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Lotto {

    private static final int LOTTO_PRICE = 1000;
    private static final int BEGIN_INDEX = 0;
    private static final String SEPARATOR = ", ";
    private static final int MATCH_COUNT_MIN = 3;
    private static final int MATCH_COUNT_MAX = 6;
    private static final int BEGIN_MATCH_COUNT = 1;

    public static int matchCount(LottoNumbers myLottoNumbers, LottoNumbers winningLottoNumbers) {
        List<LottoNumber> matchNumbers = myLottoNumbers.value().stream()
                .filter(m -> winningLottoNumbers.value().stream()
                        .anyMatch(Predicate.isEqual(m))).collect(Collectors.toList());
        return matchNumbers.size();
    }

    public static long reward(int matchCount) {
        return MatchType.of(matchCount).reward();
    }

    public static long lottoCount(long price) {
        return price / LOTTO_PRICE;
    }

    public static List<LottoNumbers> generateAllLottoNumbers(long lottoCount) {
        List<LottoNumbers> lottoNumbersList = new ArrayList<>();
        for (int i = BEGIN_INDEX; i < lottoCount; i++) {
            lottoNumbersList.add(LottoGenerator.generateLottoNumbers());
        }
        return lottoNumbersList;
    }

    public static LottoNumbers winningLottoNumbers(String input) {
        String[] split = input.split(SEPARATOR);
        List<LottoNumber> numbers = new ArrayList<>();

        for (int i = BEGIN_INDEX; i < split.length; i++) {
            numbers.add(new LottoNumber(Integer.parseInt(split[i])));
        }
        return new LottoNumbers(numbers);
    }

    public static double totalProfit(List<LottoNumbers> lottoNumbersList, LottoNumbers winningLottoNumbers) {
        long purchasePrice = lottoNumbersList.size() * LOTTO_PRICE;
        long totalReward = 0l;

        for (LottoNumbers lottoNumbers : lottoNumbersList) {
            if (isNotMatchCount(winningLottoNumbers, lottoNumbers)) {
                continue;
            }
            System.out.println("matchCount(lottoNumbers, winningLottoNumbers) = " + matchCount(lottoNumbers, winningLottoNumbers));

            totalReward += MatchType.of(matchCount(lottoNumbers, winningLottoNumbers)).reward();
        }
        return totalReward / purchasePrice;
    }

    private static boolean isNotMatchCount(LottoNumbers winningLottoNumbers, LottoNumbers lottoNumbers) {
        return matchCount(lottoNumbers, winningLottoNumbers) < MATCH_COUNT_MIN || matchCount(lottoNumbers, winningLottoNumbers) > MATCH_COUNT_MAX;
    }

    public static Map<Integer, Integer> matchCounts(List<LottoNumbers> lottoNumbersList, LottoNumbers winningLottoNumbers) {
        Map<Integer, Integer> matchCounts = new HashMap<>();

        for (LottoNumbers lottoNumbers : lottoNumbersList) {
            if (isNotMatchCount(winningLottoNumbers, lottoNumbers)) {
                continue;
            }

            if (!matchCounts.containsKey(matchCount(lottoNumbers, winningLottoNumbers))) {
                matchCounts.put(matchCount(lottoNumbers, winningLottoNumbers), BEGIN_MATCH_COUNT);
                continue;
            }
            int matchCount = matchCounts.get(matchCount(lottoNumbers, winningLottoNumbers));
            matchCounts.put(matchCount(lottoNumbers, winningLottoNumbers), matchCount + 1);
        }

        return matchCounts;
    }
}
