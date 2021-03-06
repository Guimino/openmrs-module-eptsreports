package org.openmrs.module.eptsreports.reporting.library.queries;

public interface QualityImprovementQueriesInterface {

  class QUERY {
    public static final String findPatientsWhoAreNewlyEnrolledOnARTRF05 =
        " SELECT patient_id FROM ( "
            + " SELECT patient_id, MIN(art_start_date) art_start_date FROM ( "
            + " SELECT p.patient_id, MIN(value_datetime) art_start_date FROM patient p "
            + " INNER JOIN encounter e ON p.patient_id = e.patient_id "
            + " INNER JOIN obs o ON e.encounter_id = o.encounter_id "
            + " WHERE p.voided = 0 AND e.voided = 0 AND o.voided = 0 AND e.encounter_type = 53 "
            + " AND o.concept_id = 1190 AND o.value_datetime is NOT NULL AND o.value_datetime <= :endInclusionDate AND e.location_id = :location "
            + " GROUP BY p.patient_id "
            + " ) art_start "
            + " GROUP BY patient_id "
            + " ) tx_new WHERE art_start_date BETWEEN :startInclusionDate AND :endInclusionDate ";

    public static final String
        findPatientsWhoWhereMarkedAsTransferedInAndOnARTOnInAPeriodOnMasterCardRF06 =
            "SELECT p.patient_id from patient p "
                + "INNER JOIN encounter e ON p.patient_id=e.patient_id "
                + "INNER JOIN obs obsTrans ON e.encounter_id=obsTrans.encounter_id AND obsTrans.voided=0 AND obsTrans.concept_id=1369 AND obsTrans.value_coded=1065 "
                + "INNER JOIN obs obsTarv ON e.encounter_id=obsTarv.encounter_id AND obsTarv.voided=0 AND obsTarv.concept_id=6300 AND obsTarv.value_coded=6276 "
                + "WHERE p.voided=0 AND e.voided=0 AND e.encounter_type=53 AND  e.location_id=:location ";

    public static final String findPatientsWhoTransferedOutRF07 =
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

    public static final String findPatientsWhoArePregnantInclusionDateRF08 =
        "Select p.patient_id from 	person pe "
            + "inner join patient p on pe.person_id=p.patient_id "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on e.encounter_id=o.encounter_id "
            + "inner join obs obsGravida on e.encounter_id=obsGravida.encounter_id "
            + "where pe.voided=0 and p.voided=0 and e.voided=0 and o.voided=0 and obsGravida.voided=0 and e.encounter_type=53 and e.location_id=:location and "
            + "o.concept_id=1190 and o.value_datetime is not null and o.value_datetime BETWEEN :startInclusionDate AND :endInclusionDate and "
            + "obsGravida.concept_id=1982 and obsGravida.value_coded=1065 and pe.gender='F' ";

    public static final String findPatientsWhoAreBreastfeedingInclusionDateRF09 =
        "Select p.patient_id from person pe "
            + "inner join patient p on pe.person_id=p.patient_id "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on e.encounter_id=o.encounter_id "
            + "inner join obs obsLactante on e.encounter_id=obsLactante.encounter_id "
            + "where 	pe.voided=0 and p.voided=0 and e.voided=0 and o.voided=0 and obsLactante.voided=0 and e.encounter_type=53 and e.location_id=:location and "
            + "o.concept_id=1190 and o.value_datetime is not null and o.value_datetime BETWEEN :startInclusionDate AND :endInclusionDate and "
            + "obsLactante.concept_id=6332 and obsLactante.value_coded=1065 and pe.gender='F' ";

    public static final String
        findPatientsWhoAreNewlyEnrolledOnARTByAgeUsingYearAdultDesagragation =
            "SELECT patient_id FROM ( "
                + "SELECT patient_id, MIN(art_start_date) art_start_date FROM ( "
                + "SELECT p.patient_id, MIN(value_datetime) art_start_date FROM patient p "
                + "INNER JOIN encounter e ON p.patient_id=e.patient_id "
                + "INNER JOIN obs o ON e.encounter_id=o.encounter_id "
                + "WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=53 "
                + "AND o.concept_id=1190 AND o.value_datetime is NOT NULL AND o.value_datetime<=:endInclusionDate AND e.location_id=:location "
                + "GROUP BY p.patient_id "
                + ") art_start GROUP "
                + "BY patient_id "
                + ") tx_new "
                + "INNER JOIN person pe ON tx_new.patient_id=pe.person_id "
                + "WHERE (TIMESTAMPDIFF(year,birthdate,art_start_date))>=15 AND birthdate IS NOT NULL and pe.voided=0 "
                + "AND art_start_date BETWEEN :startInclusionDate AND :endInclusionDate ";

    public static final String
        findPatientsWhoAreNewlyEnrolledOnARTByAgeUsingYearChildrenDesagragation =
            "SELECT patient_id FROM ( "
                + "SELECT patient_id, MIN(art_start_date) art_start_date FROM ( "
                + "SELECT p.patient_id, MIN(value_datetime) art_start_date FROM patient p "
                + "INNER JOIN encounter e ON p.patient_id=e.patient_id "
                + "INNER JOIN obs o ON e.encounter_id=o.encounter_id "
                + "WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=53 "
                + "AND o.concept_id=1190 AND o.value_datetime is NOT NULL AND o.value_datetime<=:endInclusionDate AND e.location_id=:location "
                + "GROUP BY p.patient_id "
                + ") art_start GROUP "
                + "BY patient_id "
                + ") tx_new "
                + "INNER JOIN person pe ON tx_new.patient_id=pe.person_id "
                + "WHERE (TIMESTAMPDIFF(year,birthdate,art_start_date))< 15 AND birthdate IS NOT NULL and pe.voided=0 "
                + "AND art_start_date BETWEEN :startInclusionDate AND :endInclusionDate ";

    public static final String findPatientsWhoAreNewlyEnrolledOnARTByAChildrenWhit9Months =
        "SELECT patient_id FROM ( "
            + "SELECT patient_id, MIN(art_start_date) art_start_date FROM ( "
            + "SELECT p.patient_id, MIN(value_datetime) art_start_date FROM patient p "
            + "INNER JOIN encounter e ON p.patient_id=e.patient_id "
            + "INNER JOIN obs o ON e.encounter_id=o.encounter_id "
            + "WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=53 "
            + "AND o.concept_id=1190 AND o.value_datetime is NOT NULL AND o.value_datetime<=:endInclusionDate AND e.location_id=:location "
            + "GROUP BY p.patient_id "
            + ") art_start GROUP "
            + "BY patient_id "
            + ") tx_new "
            + "INNER JOIN person pe ON tx_new.patient_id=pe.person_id "
            + "WHERE (TIMESTAMPDIFF(month,birthdate,art_start_date))< 9 AND birthdate IS NOT NULL and pe.voided=0 "
            + "AND art_start_date BETWEEN :startInclusionDate AND :endInclusionDate ";

    public static final String
        findPatientsWhoAreNewlyEnrolledOnARTByAgeUsingYearChildrenBiggerThen1neLess14 =
            "SELECT patient_id FROM ( "
                + "SELECT patient_id, MIN(art_start_date) art_start_date FROM ( "
                + "SELECT p.patient_id, MIN(value_datetime) art_start_date FROM patient p "
                + "INNER JOIN encounter e ON p.patient_id=e.patient_id "
                + "INNER JOIN obs o ON e.encounter_id=o.encounter_id "
                + "WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=53 "
                + "AND o.concept_id=1190 AND o.value_datetime is NOT NULL AND o.value_datetime<=:endInclusionDate AND e.location_id=:location "
                + "GROUP BY p.patient_id "
                + ") art_start GROUP "
                + "BY patient_id "
                + ") tx_new "
                + "INNER JOIN person pe ON tx_new.patient_id=pe.person_id "
                + "WHERE (TIMESTAMPDIFF(year,birthdate,art_start_date)) BETWEEN 1 AND 14 AND birthdate IS NOT NULL and pe.voided=0 "
                + "AND art_start_date BETWEEN :startInclusionDate AND :endInclusionDate ";

    public static final String
        findPatientsWhoAreNewlyEnrolledOnARTByAgeUsingYearChildrenBiggerThen2eLess14 =
            "SELECT patient_id FROM ( "
                + "SELECT patient_id, MIN(art_start_date) art_start_date FROM ( "
                + "SELECT p.patient_id, MIN(value_datetime) art_start_date FROM patient p "
                + "INNER JOIN encounter e ON p.patient_id=e.patient_id "
                + "INNER JOIN obs o ON e.encounter_id=o.encounter_id "
                + "WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=53 "
                + "AND o.concept_id=1190 AND o.value_datetime is NOT NULL AND o.value_datetime<=:endInclusionDate AND e.location_id=:location "
                + "GROUP BY p.patient_id "
                + ") art_start GROUP "
                + "BY patient_id "
                + ") tx_new "
                + "INNER JOIN person pe ON tx_new.patient_id=pe.person_id "
                + "WHERE (TIMESTAMPDIFF(year,birthdate,art_start_date)) BETWEEN 2 AND 14 AND birthdate IS NOT NULL and pe.voided=0 "
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
            + ") art_start "
            + "GROUP BY patient_id "
            + ") tx_new "
            + "INNER JOIN person pe ON tx_new.patient_id=pe.person_id "
            + "WHERE (TIMESTAMPDIFF(month,birthdate,art_start_date))<=18 AND birthdate IS NOT NULL and pe.voided=0 "
            + "AND art_start_date BETWEEN :startInclusionDate AND :endInclusionDate ";

    public static final String
        findPatientsWhoAreNewEnrolledOnArtByAgeUsingYearAdulyAndHaveFirstConsultInclusionPeriodCategory3FR12Numerator =
            " SELECT FichaResumo.patient_id "
                + " FROM "
                + " ( "
                + " SELECT patient_id, min(TR_Date) as TR_Date FROM "
                + " (SELECT p.patient_id AS patient_id, o.obs_datetime As TR_Date "
                + " FROM patient p "
                + " INNER JOIN encounter e ON p.patient_id = e.patient_id "
                + " INNER JOIN obs o ON e.encounter_id = o.encounter_id "
                + " WHERE p.voided = 0 AND e.voided = 0 AND o.voided = 0 AND o.concept_id = 22772 "
                + " AND o.value_coded IN (1030,1040) "
                + " AND e.encounter_type = 53 "
                + " AND e.location_id IN (:location) "
                + " AND o.obs_datetime <= :endInclusionDate "
                + " UNION "
                + " SELECT p.patient_id AS patient_id, o.obs_datetime As TR_Date "
                + " FROM patient p "
                + " INNER JOIN encounter e ON p.patient_id = e.patient_id "
                + " INNER JOIN obs o ON e.encounter_id = o.encounter_id "
                + " WHERE p.voided = 0 AND e.voided = 0 AND o.voided = 0 AND o.concept_id = 23807 "
                + " AND o.value_coded = 1065 "
                + " AND e.encounter_type = 53 "
                + " AND e.location_id IN (:location) "
                + " AND o.obs_datetime <= :endInclusionDate) Minconsult "
                + " group by patient_id "
                + " ) FichaResumo "
                + " INNER JOIN "
                + " ( "
                + " SELECT p.patient_id AS patient_id, e.encounter_datetime AS consult_date "
                + " FROM patient p "
                + " INNER JOIN encounter e ON p.patient_id = e.patient_id "
                + " WHERE p.voided = 0 AND e.voided = 0 "
                + " AND e.encounter_type = 6 "
                + " AND e.location_id IN (:location) "
                + " AND e.encounter_datetime <= :endInclusionDate "
                + " group by p.patient_id "
                + " ) FichaClinica on FichaResumo.patient_id = FichaClinica.patient_id "
                + " AND FichaClinica.consult_date between FichaResumo.TR_Date AND date_add(FichaResumo.TR_Date, INTERVAL 7 DAY)";

    public static final String findPatientsWhoHasNutritionalAssessmentInLastConsultation =
        "select  firstClinica.patient_id  from (  "
            + "select  "
            + "p.patient_id,  "
            + "max(e.encounter_datetime) art_start_date  from patient p  "
            + "inner join encounter e on e.patient_id=p.patient_id  "
            + "where p.voided=0  "
            + "and e.voided=0  "
            + "and e.encounter_datetime between :startInclusionDate and :endRevisionDate  "
            + "and  e.location_id=:location  and e.encounter_type=6  "
            + "group by p.patient_id  "
            + ")  "
            + "firstClinica  "
            + "inner join obs obsGrau on obsGrau.person_id=firstClinica.patient_id  "
            + "where firstClinica.art_start_date=obsGrau.obs_datetime  "
            + "and obsGrau.concept_id=6336  "
            + "and obsGrau.value_coded in (6335,1115,1844,68)  and obsGrau.voided=0 ";

    public static final String
        findPatientsWhoAreNewEnrolledOnArtByAgeUsingYearChildAndHaveFirstConsultMarkedDAMDAGInclusionPeriod =
            " select firstClinica.patient_id "
                + " from "
                + " ( "
                + " select p.patient_id, min(e.encounter_datetime) encounter_datetime "
                + " from patient p "
                + " inner join encounter e on e.patient_id = p.patient_id "
                + " where p.voided = 0 and e.voided = 0 and e.encounter_datetime between :startInclusionDate and :endRevisionDate and "
                + " e.location_id = :location and e.encounter_type = 6 "
                + " group by p.patient_id "
                + " ) firstClinica "
                + " inner join obs obsGrau on obsGrau.person_id = firstClinica.patient_id "
                + " where firstClinica.encounter_datetime = obsGrau.obs_datetime and obsGrau.concept_id = 6336 "
                + " and obsGrau.value_coded in (68,1844) and obsGrau.voided = 0 ";

    public static final String
        findPatientsWhoAreNewEnrolledOnArtByAgeUsingYearChildAndHaveFirstConsultMarkedDAMDAGANDATPUSOJAInclusionPeriod =
            " select firstClinica.patient_id "
                + " from "
                + " ( "
                + " select p.patient_id, min(e.encounter_datetime) encounter_datetime "
                + " from patient p "
                + " inner join encounter e on e.patient_id = p.patient_id "
                + " where p.voided = 0 and e.voided = 0 and e.encounter_datetime between :startInclusionDate and :endRevisionDate and "
                + " e.location_id = :location and e.encounter_type = 6 "
                + " group by p.patient_id "
                + " ) firstClinica "
                + " inner join obs obsGrau on obsGrau.person_id = firstClinica.patient_id "
                + " inner join obs obsApoio on obsApoio.person_id = firstClinica.patient_id "
                + " where firstClinica.encounter_datetime = obsGrau.obs_datetime and obsGrau.concept_id = 6336 and obsGrau.value_coded "
                + " in (68,1844) and obsGrau.voided = 0 and "
                + " firstClinica.encounter_datetime = obsApoio.obs_datetime and obsApoio.concept_id = 2152 and obsApoio.value_coded in "
                + " (2151,6143) and obsApoio.voided = 0 ";

    public static final String findPatientsWhoHasNutritionalAssessmentDAMandDAGInLastConsultation =
        "select firstClinica.patient_id from ( "
            + "select p.patient_id, max(e.encounter_datetime) art_start_date from patient p "
            + "inner join encounter e on e.patient_id=p.patient_id "
            + "where p.voided=0 and e.voided=0 and e.encounter_datetime between :startInclusionDate and :endRevisionDate and "
            + "e.location_id=:location and e.encounter_type=6  group by p.patient_id "
            + ") "
            + "firstClinica "
            + "inner join obs obsGrau on obsGrau.person_id=firstClinica.patient_id "
            + "where firstClinica.art_start_date=obsGrau.obs_datetime and obsGrau.concept_id=6336 and obsGrau.value_coded in (1844,68) "
            + "and obsGrau.voided=0 ";

    public static final String
        findPatientsWhoHasNutritionalAssessmentDAMandDAGAndATPUInLastConsultation =
            "select firstClinica.patient_id from  (   "
                + "select p.patient_id,min(e.encounter_datetime) encounter_datetime  from  patient p   "
                + "inner join encounter e on e.patient_id=p.patient_id  "
                + "where p.voided=0 and e.voided=0 and e.encounter_datetime between :startInclusionDate and :endRevisionDate and   "
                + "e.location_id=:location and e.encounter_type=6   "
                + "group by p.patient_id   "
                + ") firstClinica   "
                + "inner join obs obsGrau on obsGrau.person_id=firstClinica.patient_id   "
                + "inner join obs obsApoio on obsApoio.person_id=firstClinica.patient_id  "
                + "where firstClinica.encounter_datetime=obsGrau.obs_datetime and obsGrau.concept_id=6336 and obsGrau.value_coded in (68,1844) and obsGrau.voided=0 and   "
                + "firstClinica.encounter_datetime=obsApoio.obs_datetime and obsApoio.concept_id=2152 and obsApoio.value_coded in (2151,6143) and obsApoio.voided=0  ";

    public static final String
        findPatientsWhoDiagnosedWithTBActiveInTheLastConsultationIThePeriodCatetory6 =
            "select lastClinica.patient_id from ( "
                + "select p.patient_id,max(e.encounter_datetime) encounter_datetime from patient p "
                + "inner join encounter e on e.patient_id=p.patient_id "
                + "where p.voided=0 and e.voided=0 and e.encounter_datetime between :startInclusionDate and :endRevisionDate and "
                + "e.location_id=:location and e.encounter_type=6 "
                + "group by p.patient_id "
                + ") lastClinica "
                + "inner join obs obsTBActiva on obsTBActiva.person_id=lastClinica.patient_id "
                + "where lastClinica.encounter_datetime=obsTBActiva.obs_datetime and obsTBActiva.concept_id=23761 and obsTBActiva.value_coded=1065 and obsTBActiva.voided=0 ";

    public static final String
        findPatientWwithTBScreeningAtTheLastConsultationOfThePeriodCategory6 =
            "select lastClinica.patient_id from ( "
                + "select p.patient_id,max(e.encounter_datetime) encounter_datetime  from patient p "
                + "inner join encounter e on e.patient_id=p.patient_id "
                + "where p.voided=0 and e.voided=0 and e.encounter_datetime between :startInclusionDate and :endRevisionDate and "
                + "e.location_id=:location and e.encounter_type=6 "
                + "group by p.patient_id "
                + ") lastClinica "
                + "inner join obs obsRastreio on obsRastreio.person_id=lastClinica.patient_id "
                + "where lastClinica.encounter_datetime=obsRastreio.obs_datetime and obsRastreio.concept_id=23758 and obsRastreio.value_coded in (1065,1066) and obsRastreio.voided=0 ";

    public static final String findPatientsDiagnosedWithActiveTBDuringDuringPeriodCategory7 =
        "select p.patient_id  from patient p "
            + "inner join encounter e on e.patient_id=p.patient_id "
            + "inner join obs obsTBActiva on obsTBActiva.encounter_id=e.encounter_id "
            + "where p.voided=0 and e.voided=0 and e.encounter_datetime between :startInclusionDate and :endInclusionDate and  "
            + "e.location_id=:location and e.encounter_type=6 and obsTBActiva.concept_id=23761 and obsTBActiva.value_coded=1065 and obsTBActiva.voided=0 "
            + "group by p.patient_id ";

    public static final String findPatientsWithPositiveTBScreeningInDurindPeriodCategory7 =
        "select p.patient_id from patient p "
            + "inner join encounter e on e.patient_id=p.patient_id "
            + "inner join obs obsTBPositivo on obsTBPositivo.encounter_id=e.encounter_id "
            + "where p.voided=0 and e.voided=0 and e.encounter_datetime between :startInclusionDate and :endInclusionDate and "
            + "e.location_id=:location and e.encounter_type=6 and obsTBPositivo.concept_id=23758 and obsTBPositivo.value_coded=1065 and obsTBPositivo.voided=0 "
            + "group by p.patient_id ";

    public static final String finPatientHaveTBTreatmentDuringPeriodCategory7 =
        "select p.patient_id from patient p "
            + "inner join encounter e on e.patient_id=p.patient_id "
            + "inner join obs obsTB on obsTB.encounter_id=e.encounter_id "
            + "where p.voided=0 and e.voided=0 and obsTB.obs_datetime between :startInclusionDate and :endInclusionDate and  "
            + "e.location_id=:location and e.encounter_type=6 and obsTB.concept_id=1268 and obsTB.value_coded in (1256,1257,1267) and obsTB.voided=0 "
            + "group by p.patient_id ";

    public static final String findPatientWhoStartTPIDuringPeriodCategory7 =
        "select p.patient_id  from patient p "
            + "inner join encounter e on e.patient_id=p.patient_id "
            + "inner join obs obsTPI on obsTPI.encounter_id=e.encounter_id "
            + "where p.voided=0 and e.voided=0 and obsTPI.obs_datetime between :startInclusionDate and :endInclusionDate and  "
            + "e.location_id=:location and e.encounter_type=6 and obsTPI.concept_id=6122 and obsTPI.value_coded=1256 and obsTPI.voided=0 "
            + "group by p.patient_id ";

    public static final String findPatientWhoStartTPI4MonthsAfterDateOfInclusionCategory7 =
        "select p.patient_id from patient p "
            + "inner join encounter e on e.patient_id=p.patient_id "
            + "inner join obs obsTPI on obsTPI.encounter_id=e.encounter_id "
            + "where p.voided=0 and e.voided=0 and obsTPI.obs_datetime between (:startInclusionDate + INTERVAL 4 MONTH)  and :endRevisionDate and  "
            + "e.location_id=:location and e.encounter_type=6 and obsTPI.concept_id=6122 and obsTPI.value_coded=1256 and obsTPI.voided=0 "
            + "group by p.patient_id ";

    public static final String findPatientWhoCompleteTPICategory7 =
        "select patient_id from( "
            + "select inicioTPI.patient_id,inicioTPI.dataInicioTPI,obsFimTPI.obs_datetime dataFimTPI, "
            + "obsTBActiva.obs_datetime dataTBActiva,obsRastreio.obs_datetime dataRastreioPositivo, obsTB.obs_datetime dataTB from  ( "
            + "select p.patient_id,min(obsTPI.obs_datetime) dataInicioTPI from  patient p "
            + "inner join encounter e on e.patient_id=p.patient_id "
            + "inner join obs obsTPI on obsTPI.encounter_id=e.encounter_id "
            + "where p.voided=0 and e.voided=0 and obsTPI.obs_datetime between :startInclusionDate and :endInclusionDate and  "
            + "e.location_id=:location and e.encounter_type=6 and obsTPI.concept_id=6122 and obsTPI.value_coded=1256 and obsTPI.voided=0 "
            + "group by p.patient_id "
            + ") inicioTPI "
            + "inner join obs obsFimTPI on obsFimTPI.person_id=inicioTPI.patient_id "
            + "left join obs obsTBActiva on obsTBActiva.person_id=inicioTPI.patient_id and obsTBActiva.voided=0 and  "
            + "obsTBActiva.concept_id=23761 and obsTBActiva.value_coded=1065 and  "
            + "obsTBActiva.obs_datetime between inicioTPI.dataInicioTPI and (inicioTPI.dataInicioTPI + INTERVAL 9 MONTH) and obsTBActiva.location_id=:location "
            + "left join obs obsRastreio on obsRastreio.person_id=inicioTPI.patient_id and obsRastreio.voided=0 and  "
            + "obsRastreio.concept_id=23758 and obsRastreio.value_coded=1065 and  "
            + "obsRastreio.obs_datetime between inicioTPI.dataInicioTPI and (inicioTPI.dataInicioTPI + INTERVAL 9 MONTH) and obsRastreio.location_id=:location "
            + "left join obs obsTB on obsTB.person_id=inicioTPI.patient_id and obsTB.voided=0 and  "
            + "obsTB.concept_id=1268 and obsTB.value_coded in (1256,1257,1267) and  "
            + "obsTB.obs_datetime between inicioTPI.dataInicioTPI and (inicioTPI.dataInicioTPI + INTERVAL 9 MONTH) and obsTB.location_id=:location "
            + "where obsFimTPI.concept_id=6122 and obsFimTPI.value_coded=1267 and  "
            + "obsFimTPI.obs_datetime between (inicioTPI.dataInicioTPI + INTERVAL 6 MONTH) and (inicioTPI.dataInicioTPI + INTERVAL 9 MONTH) and  "
            + "obsFimTPI.voided=0 and obsFimTPI.location_id=:location and "
            + "obsTBActiva.person_id is null and  "
            + "obsRastreio.person_id is null and  "
            + "obsTB.person_id is null "
            + ")finalTPI ";

    public static final String
        findAdultsOnARTWithMinimum3APSSFollowupConsultationsIntheFirst3MonthsAfterStartingARTCategory11NumeratorAdult =
            " SELECT patient_id FROM ( "
                + " select tx_new.patient_id, tx_new.art_start_date, primeira_consulta.encounter_datetime as APSS_data1, "
                + " segunda_consulta.encounter_datetime as APSS_data2, terceira_consulta.encounter_datetime as APSS_data3 from ( "
                + " SELECT patient_id, MIN(art_start_date) art_start_date "
                + " FROM ( "
                + " SELECT p.patient_id, MIN(value_datetime) art_start_date FROM patient p "
                + " INNER JOIN encounter e ON p.patient_id = e.patient_id "
                + " INNER JOIN obs o ON e.encounter_id = o.encounter_id "
                + " WHERE p.voided = 0 AND e.voided = 0 AND o.voided = 0 AND e.encounter_type = 53 "
                + " AND o.concept_id = 1190 AND o.value_datetime is NOT NULL AND o.value_datetime <= :endInclusionDate AND e.location_id = :location "
                + " GROUP BY p.patient_id "
                + " ) art_start "
                + " GROUP BY patient_id  "
                + " ) tx_new "
                + " inner join encounter primeira_consulta on tx_new.patient_id = primeira_consulta.patient_id and primeira_consulta.voided = 0 and primeira_consulta.encounter_type = 35 and "
                + " primeira_consulta.location_id = :location and (TIMESTAMPDIFF(DAY, tx_new.art_start_date, primeira_consulta.encounter_datetime)) between 20 and 33 "
                + " inner join encounter segunda_consulta on tx_new.patient_id = segunda_consulta.patient_id and segunda_consulta.voided = 0 and segunda_consulta.encounter_type = 35 and "
                + " segunda_consulta.location_id = :location and (TIMESTAMPDIFF(DAY, primeira_consulta.encounter_datetime, segunda_consulta.encounter_datetime)) between 20 and 33 "
                + " inner join encounter terceira_consulta on tx_new.patient_id = terceira_consulta.patient_id and terceira_consulta.voided = 0 and terceira_consulta.encounter_type = 35 and "
                + " terceira_consulta.location_id = :location and (TIMESTAMPDIFF(DAY, segunda_consulta.encounter_datetime, terceira_consulta.encounter_datetime)) between 20 and 33 "
                + " WHERE art_start_date BETWEEN :startInclusionDate AND :endInclusionDate "
                + " ) adult ";

    public static final String findAdultWithCVOver1000CopiesCategory11B2 =
        "select carga_viral.patient_id from ( "
            + "Select p.patient_id, max(o.obs_datetime) data_carga from patient p "
            + "inner join encounter e on p.patient_id = e.patient_id "
            + "inner join obs o on e.encounter_id=o.encounter_id "
            + "where p.voided = 0 and e.voided = 0 and o.voided = 0 and e.encounter_type = 6 and  o.concept_id = 856 and "
            + "o.obs_datetime between :startInclusionDate and :endInclusionDate and e.location_id = :location and o.value_numeric > 1000 "
            + "group by p.patient_id "
            + ") carga_viral "
            + "inner join person on person_id = carga_viral.patient_id WHERE (TIMESTAMPDIFF(year, birthdate, carga_viral.data_carga)) >= 15 AND birthdate IS NOT NULL and voided = 0  ";

    public static final String findChildrenWithCVOver1000CopiesCategory11B2 =
        "select carga_viral.patient_id from ( "
            + "Select p.patient_id, max(o.obs_datetime) data_carga from patient p "
            + "inner join encounter e on p.patient_id = e.patient_id "
            + "inner join obs o on e.encounter_id=o.encounter_id "
            + "where p.voided = 0 and e.voided = 0 and o.voided = 0 and e.encounter_type = 6 and  o.concept_id = 856 and "
            + "o.obs_datetime between :startInclusionDate and :endInclusionDate and e.location_id = :location and o.value_numeric > 1000 "
            + "group by p.patient_id "
            + ") carga_viral "
            + "inner join person on person_id = carga_viral.patient_id WHERE (TIMESTAMPDIFF(year, birthdate, carga_viral.data_carga)) < 15 AND birthdate IS NOT NULL and voided = 0  ";

    public static final String findPatientsWhoHaveLastFirstLineTerapeutic =
        "select firstLine.patient_id from ( "
            + "select maxLinha.patient_id, maxLinha.maxDataLinha from ( "
            + "select p.patient_id,max(o.obs_datetime) maxDataLinha from patient p "
            + "join encounter e on p.patient_id=e.patient_id "
            + "join obs o on o.encounter_id=e.encounter_id "
            + "where e.encounter_type=6 and e.voided=0 and o.voided=0 and p.voided=0 "
            + "and o.concept_id=21151 and e.location_id=:location "
            + "and o.obs_datetime between :startInclusionDate and :endInclusionDate "
            + "group by p.patient_id "
            + " ) maxLinha "
            + "inner join obs on obs.person_id=maxLinha.patient_id and maxLinha.maxDataLinha=obs.obs_datetime "
            + "where obs.concept_id=21151 and obs.value_coded=21150 and obs.voided=0 and obs.location_id=:location "
            + ") firstLine ";

    public static final String
        findPatientsOnThe1stLineOfRTWithCVOver1000CopiesWhoHad3ConsecutiveMonthlyAPSSConsultationsCategory11NumeratorAdultH =
            "select carga_viral.patient_id from ( "
                + "Select p.patient_id, max(o.obs_datetime) data_carga from patient p "
                + "inner join encounter e on p.patient_id = e.patient_id "
                + "inner join obs o on e.encounter_id=o.encounter_id "
                + "where p.voided = 0 and e.voided = 0 and o.voided = 0 and e.encounter_type = 6 and  o.concept_id = 856 and "
                + "o.obs_datetime between :startInclusionDate and :endInclusionDate and e.location_id = :location and o.value_numeric > 1000 "
                + "group by p.patient_id "
                + ") carga_viral "
                + "inner join encounter primeira_consulta on carga_viral.patient_id = primeira_consulta.patient_id and primeira_consulta.voided = 0 and primeira_consulta.encounter_type = 35 and "
                + "primeira_consulta.encounter_datetime=carga_viral.data_carga "
                + "inner join encounter segunda_consulta on carga_viral.patient_id = segunda_consulta.patient_id and segunda_consulta.voided = 0 and segunda_consulta.encounter_type = 35 "
                + "and (TIMESTAMPDIFF(DAY, carga_viral.data_carga, segunda_consulta.encounter_datetime)) between 20 and 33 "
                + "inner join encounter terceira_consulta on carga_viral.patient_id = terceira_consulta.patient_id and terceira_consulta.voided = 0 and terceira_consulta.encounter_type = 35 "
                + "and (TIMESTAMPDIFF(DAY, segunda_consulta.encounter_datetime, terceira_consulta.encounter_datetime)) between 20 and 33 "
                + "inner join person pe on pe.person_id = carga_viral.patient_id "
                + "WHERE (TIMESTAMPDIFF(year, birthdate, carga_viral.data_carga)) >= 15 AND birthdate IS NOT NULL ";

    public static final String
        findChildrenOnThe1stLineOfRTWithCVOver1000CopiesWhoHad3ConsecutiveMonthlyAPSSConsultationsCategory11NumeratorChildrenH =
            "select carga_viral.patient_id from ( "
                + "Select p.patient_id, max(o.obs_datetime) data_carga from patient p "
                + "inner join encounter e on p.patient_id = e.patient_id "
                + "inner join obs o on e.encounter_id=o.encounter_id "
                + "where p.voided = 0 and e.voided = 0 and o.voided = 0 and e.encounter_type = 6 and  o.concept_id = 856 and "
                + "o.obs_datetime between :startInclusionDate and :endInclusionDate and e.location_id = :location and o.value_numeric > 1000 "
                + "group by p.patient_id "
                + ") carga_viral "
                + "inner join encounter primeira_consulta on carga_viral.patient_id = primeira_consulta.patient_id and primeira_consulta.voided = 0 and primeira_consulta.encounter_type = 35 and "
                + "primeira_consulta.encounter_datetime=carga_viral.data_carga "
                + "inner join encounter segunda_consulta on carga_viral.patient_id = segunda_consulta.patient_id and segunda_consulta.voided = 0 and segunda_consulta.encounter_type = 35 "
                + "and (TIMESTAMPDIFF(DAY, carga_viral.data_carga, segunda_consulta.encounter_datetime)) between 20 and 33 "
                + "inner join encounter terceira_consulta on carga_viral.patient_id = terceira_consulta.patient_id and terceira_consulta.voided = 0 and terceira_consulta.encounter_type = 35 "
                + "and (TIMESTAMPDIFF(DAY, segunda_consulta.encounter_datetime, terceira_consulta.encounter_datetime)) between 20 and 33 "
                + "inner join person pe on pe.person_id = carga_viral.patient_id "
                + "WHERE (TIMESTAMPDIFF(year, birthdate, carga_viral.data_carga)) < 15 AND birthdate IS NOT NULL ";

    public static final String findAdultWithCVOver1000CopiesCategory11DenominatorAdult =
        "Select carga_viral.patient_id  from ( "
            + "select ultima_carga.patient_id,ultima_carga.data_carga,obs.value_numeric valor_carga from ( "
            + "Select p.patient_id,max(o.obs_datetime) data_carga from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on e.encounter_id=o.encounter_id "
            + "where p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type in (13,6,9,53,51) and  o.concept_id in (856,1305) and "
            + "o.obs_datetime between  :startInclusionDate and :endInclusionDate and e.location_id=:location "
            + "group by p.patient_id "
            + ") ultima_carga "
            + "inner join obs on obs.person_id=ultima_carga.patient_id and obs.obs_datetime=ultima_carga.data_carga "
            + "where obs.voided=0 and obs.concept_id=856 and obs.value_numeric>1000  and obs.location_id=:location "
            + ") carga_viral "
            + "inner join person on person_id = carga_viral.patient_id WHERE (TIMESTAMPDIFF(year,birthdate,carga_viral.data_carga))>=15 AND birthdate IS NOT NULL and voided=0 ";

    public static final String findChildrenWithCVOver1000CopiesCategory11DenominatorChildren =
        "Select carga_viral.patient_id  from ( "
            + "select ultima_carga.patient_id,ultima_carga.data_carga,obs.value_numeric valor_carga from ( "
            + "Select p.patient_id,max(o.obs_datetime) data_carga from patient p "
            + "inner join encounter e on p.patient_id=e.patient_id "
            + "inner join obs o on e.encounter_id=o.encounter_id "
            + "where p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type in (13,6,9,53,51) and  o.concept_id in (856,1305) and "
            + "o.obs_datetime between  :startInclusionDate and :endInclusionDate and e.location_id=:location "
            + "group by p.patient_id "
            + ") ultima_carga "
            + "inner join obs on obs.person_id=ultima_carga.patient_id and obs.obs_datetime=ultima_carga.data_carga "
            + "where obs.voided=0 and obs.concept_id=856 and obs.value_numeric>1000  and obs.location_id=:location "
            + ") carga_viral "
            + "inner join person on person_id = carga_viral.patient_id WHERE (TIMESTAMPDIFF(year,birthdate,carga_viral.data_carga))<15 AND birthdate IS NOT NULL and voided=0 ";

    public static final String
        findPatientsOnThe1stLineOfRTWithCVOver1000CopiesWhoHad3ConsecutiveMonthlyAPSSConsultationsCategory11NumeratorAdult =
            "Select carga_viral.patient_id  from ( "
                + "select ultima_carga.patient_id,ultima_carga.data_carga,obs.value_numeric valor_carga from ( "
                + "Select p.patient_id,max(o.obs_datetime) data_carga from patient p "
                + "inner join encounter e on p.patient_id=e.patient_id "
                + "inner join obs o on e.encounter_id=o.encounter_id "
                + "where p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type in (13,6,9,53,51) and  o.concept_id in (856,1305) and "
                + "o.obs_datetime between  :startInclusionDate and :endInclusionDate and e.location_id=:location "
                + "group by p.patient_id "
                + ") ultima_carga "
                + "inner join obs on obs.person_id=ultima_carga.patient_id and obs.obs_datetime=ultima_carga.data_carga "
                + "where obs.voided=0 and obs.concept_id=856 and obs.value_numeric>1000  and obs.location_id=:location "
                + ") carga_viral "
                + "inner join encounter e1 on carga_viral.patient_id=e1.patient_id and e.voided=0 and e.encounter_type=35 "
                + "inner join encounter e2 on carga_viral.patient_id=e2.patient_id and e2.voided=0 and e2.encounter_type=35 "
                + "and (TIMESTAMPDIFF(DAY, carga_viral.data_carga,e2.encounter_datetime)) between 58 and 6 "
                + "inner join person on person_id = carga_viral.patient_id "
                + "WHERE (TIMESTAMPDIFF(year,birthdate,carga_viral.data_carga))>=15 AND birthdate IS NOT NULL and voided=0 ";

    public static final String
        findChildrenOnThe1stLineOfRTWithCVOver1000CopiesWhoHad3ConsecutiveMonthlyAPSSConsultationsCategory11NumeratorChildren =
            "Select carga_viral.patient_id  from ( "
                + "select ultima_carga.patient_id,ultima_carga.data_carga,obs.value_numeric valor_carga from ( "
                + "Select p.patient_id,max(o.obs_datetime) data_carga from patient p "
                + "inner join encounter e on p.patient_id=e.patient_id "
                + "inner join obs o on e.encounter_id=o.encounter_id "
                + "where p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type in (13,6,9,53,51) and  o.concept_id in (856,1305) and "
                + "o.obs_datetime between  :startInclusionDate and :endInclusionDate and e.location_id=:location "
                + "group by p.patient_id "
                + ") ultima_carga "
                + "inner join obs on obs.person_id=ultima_carga.patient_id and obs.obs_datetime=ultima_carga.data_carga "
                + "where obs.voided=0 and obs.concept_id=856 and obs.value_numeric>1000  and obs.location_id=:location "
                + ") carga_viral "
                + "inner join encounter e1 on carga_viral.patient_id=e1.patient_id and e.voided=0 and e.encounter_type=35 "
                + "inner join encounter e2 on carga_viral.patient_id=e2.patient_id and e2.voided=0 and e2.encounter_type=35 "
                + "and (TIMESTAMPDIFF(DAY, carga_viral.data_carga,e2.encounter_datetime)) between 58 and 6 "
                + "inner join person on person_id = carga_viral.patient_id "
                + "WHERE (TIMESTAMPDIFF(year,birthdate,carga_viral.data_carga))<15 AND birthdate IS NOT NULL and voided=0 ";

    public static final String
        findPatientsWhoAreNewlyEnrolledOnARTByAgeUsingYearChildrenBiggerThen9MontheLess2 =
            "SELECT patient_id FROM ( "
                + "SELECT patient_id, MIN(art_start_date) art_start_date FROM ( "
                + "SELECT p.patient_id, MIN(value_datetime) art_start_date FROM patient p "
                + "INNER JOIN encounter e ON p.patient_id=e.patient_id "
                + "INNER JOIN obs o ON e.encounter_id=o.encounter_id "
                + "WHERE p.voided=0 AND e.voided=0 AND o.voided=0 AND e.encounter_type=53 "
                + "AND o.concept_id=1190 AND o.value_datetime is NOT NULL AND o.value_datetime<=:endInclusionDate AND e.location_id=:location "
                + "GROUP BY p.patient_id "
                + ") art_start GROUP "
                + "BY patient_id "
                + ") tx_new "
                + "INNER JOIN person pe ON tx_new.patient_id=pe.person_id "
                + "WHERE (TIMESTAMPDIFF(month,birthdate,art_start_date)) <= 9 AND birthdate IS NOT NULL and pe.voided = 0 "
                + "AND art_start_date BETWEEN :startInclusionDate AND :endInclusionDate ";

    public static final String
        findFirstPatientChildrenAPSSConsultationWithinInclusionReportingPeriod =
            "select min_consultation.patient_id "
                + "from (                                                                                                 		"
                + "	select patient_id, min(art_start_date) art_start_date                                                 		"
                + "	from (                                                                                                		"
                + "		select p.patient_id, min(value_datetime) art_start_date                                            		"
                + " 		from patient p                                                                                 		"
                + " 			join encounter e on p.patient_id=e.patient_id                                              		"
                + " 			join obs o on e.encounter_id=o.encounter_id                                                		"
                + " 		where p.voided=0 and e.voided=0 and o.voided=0 and e.encounter_type=53                         		"
                + " 			and o.concept_id=1190 and o.value_datetime is not null                                     		"
                + "			and o.value_datetime<=:endInclusionDate and e.location_id=:location                        			"
                + " 		group by p.patient_id                                                                          		"
                + " 	) art_start                                                                                       		"
                + " 	group by patient_id) tx_new                                                                       		"
                + " 	join                                                                                              		"
                + " 	(select p.patient_id, min(e.encounter_datetime) min_consultation_date                                   "
                + " 	from patient p                                                                                   		"
                + "	 	join encounter e on e.patient_id = p.patient_id                             							"
                + "	where p.voided=0 and e.voided=0 and e.encounter_type = 35         								  			"
                + "	 	and e.encounter_datetime between :startInclusionDate and :endInclusionDate and e.location_id=:location 	"
                + "	group by p.patient_id                                                            				  			"
                + "	) min_consultation on min_consultation.patient_id = tx_new.patient_id	                          			"
                + "	join person pe on pe.person_id= tx_new.patient_id                                                 			"
                + "where (TIMESTAMPDIFF(year,birthdate,tx_new.art_start_date))<2 and birthdate is not null and pe.voided=0    			"
                + "  and tx_new.art_start_date between :startInclusionDate and :endInclusionDate and tx_new.art_start_date < min_consultation.min_consultation_date";
  }
}
