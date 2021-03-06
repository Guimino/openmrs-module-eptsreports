package org.openmrs.module.eptsreports.reporting.calculation.txml;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.calculation.result.SimpleResult;
import org.openmrs.module.eptsreports.reporting.calculation.generic.LastRecepcaoLevantamentoCalculation;
import org.openmrs.module.eptsreports.reporting.calculation.util.processor.CalculationProcessorUtils;
import org.openmrs.module.eptsreports.reporting.utils.EptsDateUtil;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.springframework.stereotype.Component;

@Component
public class TxMLPatientsWhoAreLTFUGreatherThan3MonthsCalculation extends TxMLPatientCalculation {

  private static int GREATHER_THAN_3_MONTHS = 90;
  private static int DAYS_TO_LTFU = 28;

  @Override
  protected CalculationResultMap evaluateUsingCalculationRules(
      Map<String, Object> parameterValues,
      EvaluationContext context,
      Set<Integer> cohort,
      Date startDate,
      Date endDate,
      CalculationResultMap resultMap,
      CalculationResultMap inicioRealResult,
      CalculationResultMap lastFilaCalculationResult,
      CalculationResultMap lastSeguimentoCalculationResult,
      CalculationResultMap nextFilaResult,
      CalculationResultMap nextSeguimentoResult,
      CalculationResultMap lastRecepcaoLevantamentoResult,
      LastRecepcaoLevantamentoCalculation lastRecepcaoLevantamentoCalculation) {

    for (Integer patientId : cohort) {
      Date inicioRealDate = (Date) inicioRealResult.get(patientId).getValue();
      Date maxNextDate =
          CalculationProcessorUtils.getMaxDate(
              patientId,
              nextFilaResult,
              nextSeguimentoResult,
              TxMLPatientsWhoMissedNextApointmentCalculation.getLastRecepcaoLevantamentoPlus30(
                  patientId, lastRecepcaoLevantamentoResult, lastRecepcaoLevantamentoCalculation));
      if (maxNextDate != null
          && EptsDateUtil.getDaysBetween(inicioRealDate, maxNextDate) >= GREATHER_THAN_3_MONTHS) {
        Date nextDatePlus28 = CalculationProcessorUtils.adjustDaysInDate(maxNextDate, DAYS_TO_LTFU);
        if (nextDatePlus28.compareTo(CalculationProcessorUtils.adjustDaysInDate(startDate, -1)) >= 0
            && nextDatePlus28.compareTo(endDate) < 0) {
          resultMap.put(patientId, new SimpleResult(maxNextDate, this));
        }
      } else {
        super.checkConsultationsOrFilaWithoutNextConsultationDate(
            patientId, resultMap, endDate, lastFilaCalculationResult, nextFilaResult);
        super.checkConsultationsOrFilaWithoutNextConsultationDate(
            patientId, resultMap, endDate, lastSeguimentoCalculationResult, nextSeguimentoResult);
      }
    }
    return resultMap;
  }
}
