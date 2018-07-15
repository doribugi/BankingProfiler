package project.doribugi.bankingprofiler.profiler.logparser;

import project.doribugi.bankingprofiler.profiler.banking.Transfer;

public class TransferLogParser implements LogParser<Transfer> {

  /**
   * 계좌 이체 금융거래정보 구분을 위한 문자열을 반환.
   * @return 계좌 이체 금융거래정보 타입 구분자 (transfer).
   */
  @Override
  public String getLogType() {
    return "transfer";
  }

  /**
   * 로그 메시지 문자열을 파싱하여 계좌 이체 객체를 반환한다.
   * @param logMessage 계좌 이체 금융거래정보 로그 메시지
   * @return Parsing 된 계좌 이체 객체
   * @throws IllegalLogFormatException 로그 메시지가 정의된 형식에 맞지 않은 경우 예외 발생.
   */
  @Override
  public Transfer parse(String logMessage) throws IllegalLogFormatException {
    try {
      String[] contents = logMessage.split(",");
      long customerNumber = Long.parseLong(contents[1].trim());
      String accountNumber = contents[2].trim();
      String receivingBank = contents[3].trim();
      String receivingAccountNumber = contents[4].trim();
      String receivingAccountOwner = contents[5].trim();
      long transferAmount = Long.parseLong(contents[6].trim());
      String transferDt = contents[7].trim();
      return new Transfer(
          customerNumber,
          accountNumber,
          receivingBank,
          receivingAccountNumber,
          receivingAccountOwner,
          transferAmount,
          transferDt
      );
    } catch (Exception e) {
      String message = String.format("Illegal Format - %s", logMessage);
      throw new IllegalLogFormatException(message, e);
    }
  }
}
