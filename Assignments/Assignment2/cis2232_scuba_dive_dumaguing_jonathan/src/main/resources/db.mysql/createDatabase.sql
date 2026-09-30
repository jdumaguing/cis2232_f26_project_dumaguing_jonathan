# For hccis.ca version of the database
# DROP DATABASE IF EXISTS jdumaguing_scuba_dive_w26;
# CREATE DATABASE jdumaguing_scuba_dive_w26;
# use jdumaguing_scuba_dive_w26;

#For localhost
DROP DATABASE IF EXISTS cis2232_scuba_dive;
CREATE DATABASE cis2232_scuba_dive;
use cis2232_scuba_dive;

-- ------------------------------------------------------------------------------
-- Note the table below to hold data associated with your project.  Expect one
-- table with 7-9 fields.
-- ------------------------------------------------------------------------------
CREATE TABLE DivePlan
(
    diveId             int(5),
    createdDateTime    varchar(20) NOT NULL COMMENT 'yyyy-MM-dd hh:mm:ss',
    diverName          varchar(50) NOT NULL COMMENT 'Name of the diver',
    heightCm           double COMMENT 'Diver height in centimeters',
    weightKg           double COMMENT 'Diver weight in kilograms',
    gender             varchar(10) NOT NULL COMMENT 'MALE or FEMALE',
    ageGroup           varchar(15) NOT NULL COMMENT 'AGE_10S, AGE_20S, AGE_30S, AGE_40S, AGE_50S_PLUS',
    targetDepthMeters  double COMMENT 'Planned maximum dive depth in meters',
    plannedTimeMinutes double COMMENT 'Planned bottom time in minutes',
    diveScenario       varchar(20) NOT NULL COMMENT 'DRIFT, CAVE_WRECK or COUNTER_CURRENT',
    recommendedTankSizeLiters double COMMENT 'Tank size calculated at submission'
) COMMENT 'This table holds scuba dive plan details';

INSERT INTO DivePlan (diveId, createdDateTime, diverName, heightCm, weightKg, gender, ageGroup,
                      targetDepthMeters, plannedTimeMinutes, diveScenario, recommendedTankSizeLiters)
VALUES (1, '2026-09-27 09:15:22', 'Jonathan Dumaguing', 170.0, 72.0, 'MALE', 'AGE_30S', 18.0, 40.0, 'DRIFT', 6.4),
       (2, '2026-09-27 09:18:41', 'Sean Doyle', 180.0, 85.0, 'MALE', 'AGE_20S', 30.0, 25.0, 'CAVE_WRECK', 7.6),
       (3, '2026-09-27 09:22:05', 'Jake Murphy', 175.0, 78.0, 'MALE', 'AGE_40S', 12.0, 50.0, 'COUNTER_CURRENT', 10.5),
       (4, '2026-09-27 09:25:33', 'Maria Santos', 160.0, 55.0, 'FEMALE', 'AGE_20S', 15.0, 45.0, 'DRIFT', 5.0),
       (5, '2026-09-27 09:29:17', 'Anna Reid', 165.0, 62.0, 'FEMALE', 'AGE_50S_PLUS', 22.0, 30.0, 'CAVE_WRECK', 6.1),
       (6, '2026-09-27 09:33:48', 'Carlos Reyes', 178.0, 90.0, 'MALE', 'AGE_40S', 25.0, 35.0, 'DRIFT', 7.7),
       (7, '2026-09-27 09:37:12', 'Emily Chan', 158.0, 50.0, 'FEMALE', 'AGE_10S', 10.0, 40.0, 'DRIFT', 3.5),
       (8, '2026-09-27 09:41:55', 'Liam Gallant', 183.0, 95.0, 'MALE', 'AGE_30S', 35.0, 20.0, 'COUNTER_CURRENT', 9.9),
       (9, '2026-09-27 09:45:30', 'Sophie Arsenault', 168.0, 64.0, 'FEMALE', 'AGE_30S', 20.0, 45.0, 'CAVE_WRECK', 7.4);
