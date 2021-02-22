package org.openmrs.module.eptsreports.reporting.library.queries.mq;

public interface MQCategory12QueriesInterface {

  class QUERY {

    public static final String
        findPatientsWhoAreNewlyEnrolledOnARTByAge14MonthsBeforeRevisionDateAnd11MonthsBeforeRevisionDateCategory12A =
            "SELECT patient_id FROM ( "
                + "SELECT patient_id, MIN(art_start_date) art_start_date FROM ( "
                + "SELECT p.patient_id, MIN(value_datetime) art_start_date FROM patient p "
                + "INNER JOIN encounter e ON p.patient_id=e.patient_id "
                + "INNER JOIN obs o ON e.encounter_id=o.encounter_id "
                + "WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=53 "
                + "AND o.concept_id=1190 AND o.value_datetime is NOT NULL AND o.value_datetime<=:endRevisionDate AND e.location_id=:location "
                + "GROUP BY p.patient_id "
                + ") art_start GROUP "
                + "BY patient_id "
                + ") tx_new "
                + "WHERE art_start_date BETWEEN date_add(:endRevisionDate, interval -14 MONTH) AND  date_add(:endRevisionDate, interval -11 MONTH) ";

    public static final String
        findPatientsWhoAreInTheFirstLine14MonthsBeforeRevisionDateAnd11MonthsBeforeRevisionDateCategory12B1 =
            "select firstLine.patient_id from ( "
                + "select maxLinha.patient_id, maxLinha.maxDataLinha from ( "
                + "select p.patient_id,max(o.obs_datetime) maxDataLinha from patient p "
                + "join encounter e on p.patient_id=e.patient_id "
                + "join obs o on o.encounter_id=e.encounter_id "
                + "where e.encounter_type=6 and e.voided=0 and o.voided=0 and p.voided=0 "
                + "and o.concept_id=21151 and e.location_id=:location "
                + "and o.obs_datetime between date_add(:endRevisionDate, interval -14 MONTH) AND  date_add(:endRevisionDate, interval -11 MONTH) "
                + "group by p.patient_id "
                + ") maxLinha "
                + "inner join obs on obs.person_id=maxLinha.patient_id and maxLinha.maxDataLinha=obs.obs_datetime "
                + "where obs.concept_id=21151 and obs.value_coded=21150 and obs.voided=0 and obs.location_id=:location "
                + ") firstLine ";

    public static final String
        findPatientsWhoAreNotInTheFirstLine14MonthsBeforeRevisionDateAnd11MonthsBeforeRevisionDateCategory12B1E =
            " select Diff_firstLine.patient_id from ( "
                + " SELECT maxLinha.patient_id, maxLinha.maxDataLinha FROM ( "
                + " select p.patient_id, max(o.obs_datetime) maxDataLinha "
                + " FROM patient p "
                + " join encounter e on p.patient_id = e.patient_id "
                + " join obs o on o.encounter_id = e.encounter_id "
                + " where e.encounter_type = 6 and e.voided = 0 and o.voided = 0 and p.voided = 0 "
                + " and o.concept_id = 21151 and e.location_id = :location "
                + " and o.obs_datetime BETWEEN :startInclusionDate AND :endRevisionDate "
                + " group by p.patient_id "
                + " ) maxLinha "
                + " inner join obs on obs.person_id = maxLinha.patient_id and maxLinha.maxDataLinha = obs.obs_datetime "
                + " where obs.concept_id = 21151 and obs.value_coded <> 21150 and obs.voided = 0 and obs.location_id = :location "
                + " ) Diff_firstLine ";

    public static final String
        findPatientsWhoAreInTheSecondLine14MonthsBeforeRevisionDateAnd11MonthsBeforeRevisionDateCategory12B2 =
            "select secondLine.patient_id from ( "
                + "select maxLinha.patient_id, maxLinha.maxDataLinha from ( "
                + "select p.patient_id,max(o.obs_datetime) maxDataLinha from patient p "
                + "join encounter e on p.patient_id=e.patient_id "
                + "join obs o on o.encounter_id=e.encounter_id "
                + "where e.encounter_type=6 and e.voided=0 and o.voided=0 and p.voided=0 "
                + "and o.concept_id=21151 and e.location_id=:location "
                + "and o.obs_datetime between date_add(:endRevisionDate, interval -14 MONTH) AND  date_add(:endRevisionDate, interval -11 MONTH) "
                + "group by p.patient_id "
                + ") maxLinha "
                + "inner join obs on obs.person_id=maxLinha.patient_id and maxLinha.maxDataLinha=obs.obs_datetime "
                + "where obs.concept_id=21151 and obs.value_coded=21148 and obs.voided=0 and obs.location_id=:location "
                + ") secondLine ";

    public static final String
        findPatientsWhoAreNotInTheSecondLine14MonthsBeforeRevisionDateAnd11MonthsBeforeRevisionDateB2E =
            " select secondLine.patient_id from ( "
                + " SELECT maxLinha.patient_id, maxLinha.maxDataLinha FROM ( "
                + " select p.patient_id, max(o.obs_datetime) maxDataLinha "
                + " FROM patient p "
                + " join encounter e on p.patient_id = e.patient_id "
                + " join obs o on o.encounter_id = e.encounter_id "
                + " where e.encounter_type = 6 and e.voided = 0 and o.voided = 0 and p.voided = 0 "
                + " and o.concept_id = 21151 and e.location_id = :location "
                + " and o.obs_datetime BETWEEN :startInclusionDate AND :endRevisionDate "
                + " group by p.patient_id "
                + " ) maxLinha "
                + " inner join obs on obs.person_id = maxLinha.patient_id and maxLinha.maxDataLinha = obs.obs_datetime "
                + " where obs.concept_id = 21151 and obs.value_coded <> 21148 and obs.voided = 0 and obs.location_id = :location "
                + " ) secondLine ";

    public static final String
        findPatientsWhoStartedARTInTheInclusionPeriodAndReturnedForClinicalConsultation33DaysAfterAtartingARTCategory12 =
            "SELECT ret33.patient_id FROM  (  "
                + "select tx_new.patient_id,tx_new.art_start_date as art_start_date  from (  "
                + "SELECT patient_id, MIN(art_start_date) art_start_date  FROM (  "
                + "SELECT p.patient_id, MIN(value_datetime) art_start_date FROM patient p  "
                + "INNER JOIN encounter e ON p.patient_id=e.patient_id  "
                + "INNER JOIN obs o ON e.encounter_id=o.encounter_id   "
                + "WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=53  "
                + "AND o.concept_id=1190 AND o.value_datetime is NOT NULL AND o.value_datetime<=:endInclusionDate  AND e.location_id=:location  "
                + "GROUP BY p.patient_id  "
                + ") art_start  "
                + "GROUP BY patient_id  "
                + ") tx_new    "
                + "inner join "
                + "( select p.patient_id, cc.encounter_datetime as dataret33 from patient p   "
                + "inner join encounter cc on p.patient_id=cc.patient_id   "
                + "WHERE cc.voided=0 and cc.encounter_type=6  and cc.location_id=:location and cc.encounter_datetime BETWEEN :startInclusionDate and :endRevisionDate "
                + "union "
                + "select p.patient_id, o.value_datetime as dataret33  from patient p  "
                + "inner join encounter e on p.patient_id=e.patient_id   "
                + "inner join obs o on o.encounter_id=e.encounter_id  "
                + "inner join obs oLevantou on e.encounter_id=oLevantou.encounter_id   "
                + "where  p.voided=0 and e.voided=0 and o.voided=0 and oLevantou.voided=0 and e.encounter_type=52 and o.concept_id=23866   "
                + "and o.value_datetime is not null and e.location_id=:location and  o.value_datetime BETWEEN :startInclusionDate and :endRevisionDate "
                + "and oLevantou.concept_id=23865 and oLevantou.value_coded=1065  "
                + ") ret on ret.patient_id=tx_new.patient_id "
                + "where tx_new.art_start_date BETWEEN :startInclusionDate AND :endInclusionDate and (TIMESTAMPDIFF(DAY, tx_new.art_start_date,ret.dataret33)) between 20 and 33  "
                + "group by tx_new.patient_id  "
                + ")ret33 ";

    public static final String
        findPatientsWhoStartedARTInTheInclusionPeriodAndReturnedForClinicalConsultation99DaysAfterAtartingARTCategory12 =
            "SELECT patient_id FROM ( "
                + "select tx_new.patient_id,tx_new.art_start_date,primeira_consulta.encounter_datetime as consultaClinica from ( "
                + "SELECT patient_id, MIN(art_start_date) art_start_date "
                + "FROM ( "
                + "SELECT p.patient_id, MIN(value_datetime) art_start_date FROM patient p "
                + "INNER JOIN encounter e ON p.patient_id=e.patient_id "
                + "INNER JOIN obs o ON e.encounter_id=o.encounter_id  "
                + "WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=53 "
                + "AND o.concept_id=1190 AND o.value_datetime is NOT NULL AND o.value_datetime<=:endInclusionDate AND e.location_id=:location "
                + "GROUP BY p.patient_id "
                + ") art_start "
                + "GROUP BY patient_id "
                + ") tx_new "
                + "inner join encounter primeira_consulta on tx_new.patient_id=primeira_consulta.patient_id and primeira_consulta.voided=0 and primeira_consulta.encounter_type in(6)and "
                + "primeira_consulta.location_id=:location and (TIMESTAMPDIFF(DAY, tx_new.art_start_date,primeira_consulta.encounter_datetime)) between 20 and 33 "
                + "inner join encounter segunda_consulta on tx_new.patient_id=segunda_consulta.patient_id and segunda_consulta.voided=0 and segunda_consulta.encounter_type in(6) and "
                + "segunda_consulta.location_id=:location and (TIMESTAMPDIFF(DAY, primeira_consulta.encounter_datetime,segunda_consulta.encounter_datetime)) between 20 and 33 "
                + "inner join encounter terceira_consulta on tx_new.patient_id=terceira_consulta.patient_id and terceira_consulta.voided=0 and terceira_consulta.encounter_type in(6) and "
                + "terceira_consulta.location_id=:location and (TIMESTAMPDIFF(DAY, segunda_consulta.encounter_datetime,terceira_consulta.encounter_datetime)) between 20 and 33 "
                + "WHERE art_start_date BETWEEN :startInclusionDate AND :endInclusionDate"
                + ") ret99 ";

    public static final String findPatientsWhoAreCurrentlyEnrolledOnArtMOHB13 =
        "select patient_id from (select  inicio_fila_seg_prox.*,GREATEST(COALESCE(data_fila,data_seguimento,data_recepcao_levantou),COALESCE(data_seguimento,data_fila,data_recepcao_levantou),COALESCE(data_recepcao_levantou,data_seguimento,data_fila))  data_usar_c, "
            + "GREATEST(COALESCE(data_proximo_lev,data_recepcao_levantou30),COALESCE(data_recepcao_levantou30,data_proximo_lev)) data_usar from (select inicio_fila_seg.*, "
            + "max(obs_fila.value_datetime) data_proximo_lev, "
            + "max(obs_seguimento.value_datetime) data_proximo_seguimento, "
            + "date_add(data_recepcao_levantou, interval 30 day) data_recepcao_levantou30 from (select inicio.*,saida.data_estado,max_fila.data_fila,max_consulta.data_seguimento, max_recepcao.data_recepcao_levantou from ( "
            + "Select patient_id,min(data_inicio) data_inicio from ( "
            + "Select p.patient_id,min(e.encounter_datetime) data_inicio from patient p  "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on o.encounter_id=e.encounter_id "
            + "where e.voided=0 and o.voided=0 and p.voided=0 and  e.encounter_type in (18,6,9) and o.concept_id=1255 and o.value_coded=1256 and  e.encounter_datetime<=:endRevisionDate and e.location_id=:location "
            + "group by p.patient_id "
            + "union "
            + "Select p.patient_id,min(value_datetime) data_inicio from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on e.encounter_id=o.encounter_id "
            + "where p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type in (18,6,9,53) and  o.concept_id=1190 and o.value_datetime is not null and  o.value_datetime<=:endRevisionDate and e.location_id=:location group by p.patient_id "
            + "union "
            + "select pg.patient_id,min(date_enrolled) data_inicio from patient p inner join patient_program pg on p.patient_id=pg.patient_id "
            + "where pg.voided=0 and p.voided=0 and program_id=2 and date_enrolled<=:endRevisionDate and location_id=:location "
            + "group by pg.patient_id "
            + "union "
            + "SELECT e.patient_id, MIN(e.encounter_datetime) AS data_inicio  FROM 	patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "WHERE p.voided=0 and e.encounter_type=18 AND e.voided=0 and e.encounter_datetime<=:endRevisionDate and e.location_id=:location "
            + "GROUP BY p.patient_id "
            + "union "
            + "Select p.patient_id,min(value_datetime) data_inicio from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on e.encounter_id=o.encounter_id "
            + "where p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type=52 and  o.concept_id=23866 and o.value_datetime is not null and  o.value_datetime<=:endRevisionDate and e.location_id=:location "
            + "group by p.patient_id) inicio_real group by patient_id)inicio "
            + "left join ( "
            + "select patient_id,max(data_estado) data_estado from ( "
            + "select maxEstado.patient_id,maxEstado.data_transferidopara data_estado from( "
            + "select pg.patient_id,max(ps.start_date) data_transferidopara from patient p "
            + "inner join patient_program pg on p.patient_id=pg.patient_id "
            + "inner join patient_state ps on pg.patient_program_id=ps.patient_program_id "
            + "where pg.voided=0 and ps.voided=0 and p.voided=0 and pg.program_id=2 and ps.start_date<=:endRevisionDate and pg.location_id=:location "
            + "group by p.patient_id )maxEstado "
            + "inner join patient_program pg2 on pg2.patient_id=maxEstado.patient_id "
            + "inner join patient_state ps2 on pg2.patient_program_id=ps2.patient_program_id "
            + "where pg2.voided=0 and ps2.voided=0 and pg2.program_id=2 and "
            + "ps2.start_date=maxEstado.data_transferidopara and pg2.location_id=:location and ps2.state in (7,8,10) "
            + "union "
            + "select p.patient_id, max(o.obs_datetime) data_estado from patient p  "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs  o on e.encounter_id=o.encounter_id "
            + "where e.voided=0 and o.voided=0 and p.voided=0 and  e.encounter_type in (53,6) and o.concept_id in (6272,6273) and o.value_coded in (1706,1366,1709) and o.obs_datetime<=:endRevisionDate and e.location_id=:location "
            + "group by p.patient_id "
            + "union "
            + "select person_id as patient_id,death_date as data_estado from person  "
            + "where dead=1 and death_date is not null and death_date<=:endRevisionDate) allSaida "
            + "group by patient_id) saida on inicio.patient_id=saida.patient_id "
            + "left join ( "
            + "Select p.patient_id,max(encounter_datetime) data_fila from patient p  "
            + "inner join encounter e on e.patient_id=p.patient_id "
            + "where p.voided=0 and e.voided=0 and e.encounter_type=18 and e.location_id=:location and date(e.encounter_datetime)<=:endRevisionDate "
            + "group by p.patient_id) max_fila on inicio.patient_id=max_fila.patient_id	"
            + "left join (Select p.patient_id,max(encounter_datetime) data_seguimento from patient p "
            + "inner join encounter e on e.patient_id=p.patient_id "
            + "where p.voided=0 and e.voided=0 and e.encounter_type in (6,9) and  e.location_id=:location and e.encounter_datetime<=:endRevisionDate group by p.patient_id) max_consulta on inicio.patient_id=max_consulta.patient_id "
            + "left join ( "
            + "Select p.patient_id,max(value_datetime) data_recepcao_levantou from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on e.encounter_id=o.encounter_id "
            + "where p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type=52 and  o.concept_id=23866 and o.value_datetime is not null and  o.value_datetime<=:endRevisionDate and e.location_id=:location "
            + "group by p.patient_id) max_recepcao on inicio.patient_id=max_recepcao.patient_id "
            + "group by inicio.patient_id) inicio_fila_seg "
            + "left join obs obs_fila on obs_fila.person_id=inicio_fila_seg.patient_id and obs_fila.voided=0 and obs_fila.obs_datetime=inicio_fila_seg.data_fila and obs_fila.concept_id=5096 and obs_fila.location_id=:location "
            + "left join obs obs_seguimento on obs_seguimento.person_id=inicio_fila_seg.patient_id and obs_seguimento.voided=0 and obs_seguimento.obs_datetime=inicio_fila_seg.data_seguimento and obs_seguimento.concept_id=1410 and obs_seguimento.location_id=:location "
            + "group by inicio_fila_seg.patient_id) inicio_fila_seg_prox "
            + "group by patient_id) coorte12meses_final where (data_estado is null or (data_estado is not null and  data_usar_c>data_estado)) and date_add(data_usar, interval 60 day) >=:endRevisionDate ";
  }
}
