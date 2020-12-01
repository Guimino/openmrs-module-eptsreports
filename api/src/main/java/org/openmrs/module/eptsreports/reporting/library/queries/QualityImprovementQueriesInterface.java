package org.openmrs.module.eptsreports.reporting.library.queries;

public interface QualityImprovementQueriesInterface {

  class QUERY {
    public static final String findPatientsWhoAreNewlyEnrolledOnART =
        "SELECT patient_id FROM ( "
            + "SELECT patient_id, MIN(art_start_date) art_start_date FROM ( "
            + "SELECT p.patient_id, MIN(value_datetime) art_start_date FROM patient p "
            + "INNER JOIN encounter e ON p.patient_id=e.patient_id "
            + "INNER JOIN obs o ON e.encounter_id=o.encounter_id "
            + "WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=53 "
            + "AND o.concept_id=1190 AND o.value_datetime is NOT NULL AND o.value_datetime<=:endInclusionDate AND e.location_id=:location "
            + "GROUP BY p.patient_id "
            + "UNION "
            + "Select p.patient_id,min(o.value_datetime) art_start_date from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on e.encounter_id=o.encounter_id "
            + "inner join obs oLevantou on e.encounter_id=oLevantou.encounter_id "
            + "where  p.voided=0 and e.voided=0 and o.voided=0 and oLevantou.voided=0 and e.encounter_type=52 and o.concept_id=23866 and "
            + "o.value_datetime is not null and o.value_datetime<=:endInclusionDate and e.location_id=:location and "
            + "oLevantou.concept_id=23865 and oLevantou.value_coded=1065 "
            + "group by p.patient_id "
            + ") art_start "
            + "GROUP BY patient_id "
            + ") tx_new WHERE art_start_date BETWEEN :startInclusionDate AND :endInclusionDate ";

    public static final String
        findPatientsWhoWhereMarkedAsTransferedInAndOnARTOnInAPeriodOnMasterCard =
            "SELECT p.patient_id from patient p "
                + "INNER JOIN encounter e ON p.patient_id=e.patient_id "
                + "INNER JOIN obs obsTrans ON e.encounter_id=obsTrans.encounter_id AND obsTrans.voided=0 AND obsTrans.concept_id=1369 AND obsTrans.value_coded=1065 "
                + "INNER JOIN obs obsTarv ON e.encounter_id=obsTarv.encounter_id AND obsTarv.voided=0 AND obsTarv.concept_id=6300 AND obsTarv.value_coded=6276 "
                + "WHERE p.voided=0 AND e.voided=0 AND e.encounter_type=53 AND  e.location_id=:location ";

    public static final String findPatientsWhoTransferedOut =
        "select saida.patient_id from ( "
            + "select p.patient_id, max(o.obs_datetime) data_estado from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs  o on e.encounter_id=o.encounter_id "
            + "where e.voided=0 and o.voided=0 and p.voided=0 and e.encounter_type in (53,6) and "
            + "o.concept_id in(6272,6273) and o.value_coded=1706 and o.obs_datetime<=:endRevisionDate and e.location_id=:location "
            + "group by p.patient_id "
            + ") saida "
            + "inner join ( "
            + "select patient_id,max(encounter_datetime) encounter_datetime from ( "
            + "select p.patient_id,max(e.encounter_datetime) encounter_datetime from patient p "
            + "inner join encounter e on e.patient_id=p.patient_id "
            + "where p.voided=0 and e.voided=0 and e.encounter_datetime<=:endRevisionDate and "
            + "e.location_id=:location and e.encounter_type=6 "
            + "group by p.patient_id "
            + "union "
            + "Select p.patient_id,max(o.value_datetime) encounter_datetime from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on e.encounter_id=o.encounter_id "
            + "inner join obs oLevantou on e.encounter_id=oLevantou.encounter_id "
            + "where  p.voided=0 and e.voided=0 and o.voided=0 and oLevantou.voided=0 and e.encounter_type=52 and o.concept_id=23866 and "
            + "o.value_datetime is not null and o.value_datetime<=:endRevisionDate and e.location_id=:location and "
            + "oLevantou.concept_id=23865 and oLevantou.value_coded=1065 "
            + "group by p.patient_id "
            + ") consultaLev "
            + "group by patient_id "
            + ") consultaOuARV on saida.patient_id=consultaOuARV.patient_id "
            + "where consultaOuARV.encounter_datetime<=saida.data_estado and saida.data_estado<=:endRevisionDate ";

    public static final String findPatientsWhoArePregnantInclusionDate =
        "Select p.patient_id from 	person pe "
            + "inner join patient p on pe.person_id=p.patient_id "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on e.encounter_id=o.encounter_id "
            + "inner join obs obsGravida on e.encounter_id=obsGravida.encounter_id "
            + "where pe.voided=0 and p.voided=0 and e.voided=0 and o.voided=0 and obsGravida.voided=0 and e.encounter_type=53 and e.location_id=:location and "
            + "o.concept_id=1190 and o.value_datetime is not null and o.value_datetime BETWEEN :startInclusionDate AND :endInclusionDate and "
            + "obsGravida.concept_id=1982 and obsGravida.value_coded=1065 and pe.gender='F' ";

    public static final String findPatientsWhoAreBreastfeedingInclusionDate =
        "Select p.patient_id from person pe "
            + "inner join patient p on pe.person_id=p.patient_id "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on e.encounter_id=o.encounter_id "
            + "inner join obs obsLactante on e.encounter_id=obsLactante.encounter_id "
            + "where 	pe.voided=0 and p.voided=0 and e.voided=0 and o.voided=0 and obsLactante.voided=0 and e.encounter_type=53 and e.location_id=:location and "
            + "o.concept_id=1190 and o.value_datetime is not null and o.value_datetime BETWEEN :startInclusionDate AND :endInclusionDate and "
            + "obsLactante.concept_id=6332 and obsLactante.value_coded=1065 and pe.gender='F' ";

    public static final String findPatientsWhoAreNewlyEnrolledOnARTByAgeUsingYearAdult =
        "SELECT patient_id FROM ( "
            + "SELECT patient_id, MIN(art_start_date) art_start_date FROM ( "
            + "SELECT p.patient_id, MIN(value_datetime) art_start_date FROM patient p "
            + "INNER JOIN encounter e ON p.patient_id=e.patient_id "
            + "INNER JOIN obs o ON e.encounter_id=o.encounter_id "
            + "WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=53 "
            + "AND o.concept_id=1190 AND o.value_datetime is NOT NULL AND o.value_datetime<=:endInclusionDate AND e.location_id=:location "
            + "GROUP BY p.patient_id "
            + "UNION "
            + "Select 	p.patient_id,min(o.value_datetime) art_start_date from  patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on e.encounter_id=o.encounter_id "
            + "inner join obs oLevantou on e.encounter_id=oLevantou.encounter_id "
            + "where  p.voided=0 and e.voided=0 and o.voided=0 and oLevantou.voided=0 and e.encounter_type=52 and o.concept_id=23866 and "
            + "o.value_datetime is not null and o.value_datetime<=:endInclusionDate and e.location_id=:location and "
            + "oLevantou.concept_id=23865 and oLevantou.value_coded=1065 "
            + "group by p.patient_id "
            + ") art_start GROUP "
            + "BY patient_id "
            + ") tx_new "
            + "INNER JOIN person pe ON tx_new.patient_id=pe.person_id "
            + "WHERE (TIMESTAMPDIFF(year,birthdate,art_start_date)/365)>=15 AND birthdate IS NOT NULL and pe.voided=0 "
            + "AND art_start_date BETWEEN :startInclusionDate AND :endInclusionDate ";
    
    public static final String findPatientsWhoAreNewlyEnrolledOnARTByAgeUsingYearChildren =
            "SELECT patient_id FROM ( "
                + "SELECT patient_id, MIN(art_start_date) art_start_date FROM ( "
                + "SELECT p.patient_id, MIN(value_datetime) art_start_date FROM patient p "
                + "INNER JOIN encounter e ON p.patient_id=e.patient_id "
                + "INNER JOIN obs o ON e.encounter_id=o.encounter_id "
                + "WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=53 "
                + "AND o.concept_id=1190 AND o.value_datetime is NOT NULL AND o.value_datetime<=:endInclusionDate AND e.location_id=:location "
                + "GROUP BY p.patient_id "
                + "UNION "
                + "Select 	p.patient_id,min(o.value_datetime) art_start_date from  patient p "
                + "inner join encounter e on p.patient_id=e.patient_id "
                + "inner join obs o on e.encounter_id=o.encounter_id "
                + "inner join obs oLevantou on e.encounter_id=oLevantou.encounter_id "
                + "where  p.voided=0 and e.voided=0 and o.voided=0 and oLevantou.voided=0 and e.encounter_type=52 and o.concept_id=23866 and "
                + "o.value_datetime is not null and o.value_datetime<=:endInclusionDate and e.location_id=:location and "
                + "oLevantou.concept_id=23865 and oLevantou.value_coded=1065 "
                + "group by p.patient_id "
                + ") art_start GROUP "
                + "BY patient_id "
                + ") tx_new "
                + "INNER JOIN person pe ON tx_new.patient_id=pe.person_id "
                + "WHERE (TIMESTAMPDIFF(year,birthdate,art_start_date)/365)< 15 AND birthdate IS NOT NULL and pe.voided=0 "
                + "AND art_start_date BETWEEN :startInclusionDate AND :endInclusionDate ";


    public static final String findPatientsWhoAreNewlyEnrolledOnARTByAgeUsingMonth =
        "SELECT patient_id FROM ( "
            + "SELECT patient_id, MIN(art_start_date) art_start_date FROM ( "
            + "SELECT p.patient_id, MIN(value_datetime) art_start_date FROM patient p "
            + "INNER JOIN encounter e ON p.patient_id=e.patient_id "
            + "INNER JOIN obs o ON e.encounter_id=o.encounter_id "
            + "WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=53 "
            + "AND o.concept_id=1190 AND o.value_datetime is NOT NULL AND o.value_datetime<=:endInclusionDate AND e.location_id=:location "
            + "GROUP BY p.patient_id "
            + "UNION "
            + "Select p.patient_id,min(o.value_datetime) art_start_date from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on e.encounter_id=o.encounter_id "
            + "inner join obs oLevantou on e.encounter_id=oLevantou.encounter_id "
            + "where p.voided=0 and e.voided=0 and o.voided=0 and oLevantou.voided=0 and e.encounter_type=52 and o.concept_id=23866 and "
            + "o.value_datetime is not null and o.value_datetime<=:endInclusionDate and e.location_id=:location and "
            + "oLevantou.concept_id=23865 and oLevantou.value_coded=1065 "
            + "group by p.patient_id "
            + ") art_start "
            + "GROUP BY patient_id "
            + ") tx_new "
            + "INNER JOIN person pe ON tx_new.patient_id=pe.person_id "
            + "WHERE (TIMESTAMPDIFF(month,birthdate,art_start_date)/365)<=18 AND birthdate IS NOT NULL and pe.voided=0 "
            + "AND art_start_date BETWEEN :startInclusionDate AND :endInclusionDate ";
  }
}
