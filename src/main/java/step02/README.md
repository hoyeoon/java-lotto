# 기능 정의

- ## Number.java
  - field
    - private int number
  - constructor
    - Number(int number)
      - range check
  - method
    - private void rangeCheck(int number)
      - 45를 넘어가는 숫자가 없는지
    - public int number();
      - field 값 반환
- ## Lotto.java
  - field
    - List<Integer> lotto;
  - constructor
    - Lotto(List<Integer> numbers);
      - 수동생성
    - Lotto(MakeNumber makeNumber);
      - 자동생성, random 6 자리 생성
    - Lotto makeLottoByString(String str)
      - string 값으로 winning number 생성하는 용도
  - method
    - void isEmpty();
      - null or empty check
    - String check(String str);
      - string 으로 생성시 check 용도
    - void check(List<Integer> lotto);
      - 6자리의 숫자가 맞는지
      - 동일한 숫자가 없는지
      <!-- - List<Integer> lottoNumbers(AutoNumber autoNumber); -->
    - int match(List<Integer> winningNumber);
      - 당첨번호와 맞춰본후 등수를 return;
    - List<Number> lotto()
      - 불변객체로 lotto 반환
- ## Lottos.java
  - field
    - List<Lotto> lottos;
  - constructor
    - Lottos(List<Lotto> lottos);
      - 주 생성자
    - Lottos(int money);
      - 돈을 받고, lotto list 생성
  - method
    - static void check(int money);
    - static List<Lotto> buy(int money);
      - 돈에 따라서 로또 구입
    - List<Lotto> lottos();
      - 불변객체로 lottos 반환
- ## OutCome.java
  - field
  - constructor
    - public OutCome(int money, Lottos lottos, Lotto winningNumber);
  - method
    - List<Integer> match(List<Number winningNumber>);
    - Map<Integer, Integer> statistic();
    - float profit(int income);