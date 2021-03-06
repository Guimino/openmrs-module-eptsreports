package org.openmrs.module.eptsreports.reporting.library.queries;

public interface QualityImprovementQueriesCAT13NuminatorP2 {

  class QUERY {

    public static final String findPatientsWhoHaveCVResultAfter33DaysOfRequestByK =
        " SELECT B3.patient_id FROM "
            + " (SELECT tx_new.patient_id AS patient_id, min(fisrtConsultation.encounter_datetime) AS encounter_datetime, tx_new.art_start_date FROM ( "
            + " SELECT patient_id, MIN(art_start_date) art_start_date FROM ( "
            + " SELECT p.patient_id, MIN(value_datetime) art_start_date FROM patient p "
            + " INNER JOIN encounter e ON p.patient_id = e.patient_id "
            + " INNER JOIN obs o ON e.encounter_id = o.encounter_id "
            + " WHERE p.voided = 0 AND e.voided = 0 AND o.voided = 0 AND e.encounter_type = 53 "
            + " AND o.concept_id = 1190 AND o.value_datetime is NOT NULL AND o.value_datetime <= :endInclusionDate AND e.location_id = :location "
            + " GROUP BY p.patient_id "
            + " ) art_start "
            + " GROUP BY patient_id "
            + " ) tx_new "
            + " INNER JOIN "
            + " ( "
            + " SELECT p.patient_id, cc.encounter_datetime as encounter_datetime "
            + " FROM patient p "
            + " INNER JOIN encounter cc ON p.patient_id = cc.patient_id "
            + " INNER JOIN obs o ON cc.encounter_id = o.encounter_id "
            + " WHERE cc.voided = 0 AND cc.encounter_type = 6  AND cc.location_id = :location "
            + " AND o.concept_id = 23722 AND o.value_coded = 856 AND o.voided = 0 "
            + " ) fisrtConsultation ON fisrtConsultation.patient_id = tx_new.patient_id "
            + " INNER JOIN person pe ON tx_new.patient_id = pe.person_id "
            + " WHERE fisrtConsultation.encounter_datetime BETWEEN date_add(tx_new.art_start_date, INTERVAL 80 DAY) AND date_add(tx_new.art_start_date, INTERVAL 130 DAY) AND pe.voided = 0  "
            + " GROUP BY tx_new.patient_id "
            + " ) AS B3 "
            + " INNER JOIN encounter e ON e.patient_id = B3.patient_id AND e.voided = 0 AND e.encounter_type = 6 AND e.location_id = :location "
            + " INNER JOIN obs o ON o.encounter_id = e.encounter_id AND o.voided = 0 "
            + " WHERE e.encounter_datetime > B3.encounter_datetime AND e.encounter_datetime <= date_add(B3.encounter_datetime, INTERVAL 33 DAY) "
            + " AND ((o.concept_id = 856 AND o.value_numeric IS NOT NULL) OR (o.concept_id = 1305 AND o.value_coded IS NOT NULL)) ";

    public static final String
        findPatientsWhoHaveCVResultAfeter33DaysOfRequestForPregnantWishRequestedCVByL =
            " SELECT B4.patient_id FROM "
                + " (SELECT fisrtConsultation.patient_id, fisrtConsultation.encounter_datetime "
                + " FROM "
                + " ( "
                + " SELECT 	p.patient_id, min(cc.encounter_datetime) as encounter_datetime "
                + " FROM patient p "
                + " INNER JOIN encounter cc ON p.patient_id = cc.patient_id "
                + " INNER JOIN obs o ON cc.encounter_id = o.encounter_id "
                + " WHERE cc.voided = 0 AND cc.encounter_type = 6  AND cc.location_id = :location "
                + " AND cc.encounter_datetime BETWEEN :startInclusionDate AND :endInclusionDate "
                + " AND o.concept_id = 1982 AND o.value_coded = 1065 AND o.voided = 0 "
                + " GROUP BY p.patient_id "
                + " ) fisrtConsultation "
                + " INNER JOIN obs cv ON cv.person_id = fisrtConsultation.patient_id "
                + " INNER JOIN person pe ON fisrtConsultation.patient_id = pe.person_id "
                + " WHERE cv.concept_id = 23722 AND cv.value_coded = 856 AND cv.voided = 0 AND pe.voided = 0 "
                + ") AS B4 "
                + " INNER JOIN encounter e ON e.patient_id = B4.patient_id AND e.voided = 0 AND e.encounter_type = 6 AND e.location_id = :location "
                + " INNER JOIN obs o ON o.encounter_id = e.encounter_id AND o.voided = 0 "
                + " WHERE e.encounter_datetime > B4.encounter_datetime AND e.encounter_datetime <= date_add(B4.encounter_datetime, INTERVAL 33 DAY) "
                + " AND ((o.concept_id = 856 AND o.value_numeric IS NOT NULL) OR (o.concept_id = 1305 AND o.value_coded IS NOT NULL)) ";
  }
}
