package lotto.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Lotto {

    private static final int LOTTO_PRICE = 1000;
    private static final int BEGIN_INDEX = 0;
    private static final String SEPARATOR = ", ";
    private static final int INIT_COUNT = 1;

    public static long reward(int matchCount) {
        return RewardType.of(matchCount).reward();
    }

    public static long lottoCount(long price) {
        return price / LOTTO_PRICE;
    }

    public static List<LottoNumbers> generateAllLottoNumbers(long lottoCount) {
        List<LottoNumbers> lottoNumbersList = new ArrayList<>();
        for (int i = BEGIN_INDEX; i < lottoCount; i++) {
            lottoNumbersList.add(new LottoNumbers());
        }
        return lottoNumbersList;
    }

    public static LottoNumbers winningLottoNumbers(String input) {
        String[] split = input.split(SEPARATOR);
        Set<LottoNumber> numbers = new HashSet<>();

        for (int i = BEGIN_INDEX; i < split.length; i++) {
            numbers.add(new LottoNumber(Integer.parseInt(split[i])));
        }
        return new LottoNumbers(numbers);
    }

    public static LottoNumber bonusLottoNumber(int bonusLottoNumber) {
        return new LottoNumber(bonusLottoNumber);
    }

    public static LottoRewards reward(List<LottoNumbers> lottoNumbersList, LottoNumbers winningLottoNumbers, LottoNumber bonusLottoNumber) {
        LottoRewards lottoRewards = new LottoRewards();

        for (LottoNumbers lottoNumbers : lottoNumbersList) {
            increaseLottoRewardCount(lottoRewards, lottoNumbers, winningLottoNumbers, bonusLottoNumber);
        }

        return lottoRewards;
    }

    private static void increaseLottoRewardCount(LottoRewards lottoRewards, LottoNumbers lottoNumbers, LottoNumbers winningLottoNumbers, LottoNumber bonusLottoNumber) {
        if (lottoNumbers.isNotWinningMatchCountWith(winningLottoNumbers)) {
            return;
        }

        int matchCount = winningLottoNumbers.matchCount(lottoNumbers);
        RewardType rewardType = RewardType.of(matchCount);

        if (rewardType == RewardType.FIVE && winningLottoNumbers.isMatchWith(bonusLottoNumber)) {
            rewardType = RewardType.FIVE_AND_BONUS;
        }

        if (lottoRewards.isNotContainRewardType(rewardType)) {
            lottoRewards.add(new LottoReward(rewardType, INIT_COUNT));
            return;
        }
        lottoRewards.increaseCountOf(rewardType);
    }

    public static double totalProfitRate(LottoRewards lottoRewards, long purchasePrice) {
        return (double) lottoRewards.totalProfit() / purchasePrice;
    }
}
