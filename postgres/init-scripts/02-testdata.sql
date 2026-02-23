-- Generate some example data
INSERT INTO asteroids (name, risk_profile, distance)
VALUES ('Ceres-Alpha', 'SAFE', 2.5);
INSERT INTO asteroid_resource (asteroid_id, resource_type, amount)
VALUES (
           (SELECT id FROM asteroids WHERE name = 'Ceres-Alpha' LIMIT 1),
    'IRON', 500
    );
INSERT INTO asteroid_resource (asteroid_id, resource_type, amount)
VALUES (
           (SELECT id FROM asteroids WHERE name = 'Ceres-Alpha' LIMIT 1),
    'GOLD', 10
    );

INSERT INTO asteroids (name, risk_profile, distance)
VALUES ('X-99-Eris', 'CAUTION', 15.1);
INSERT INTO asteroid_resource (asteroid_id, resource_type, amount)
VALUES (
           (SELECT id FROM asteroids WHERE name = 'X-99-Eris' LIMIT 1),
    'KRYPTONITE', 5
    );
INSERT INTO asteroid_resource (asteroid_id, resource_type, amount)
VALUES (
           (SELECT id FROM asteroids WHERE name = 'X-99-Eris' LIMIT 1),
    'PLATINUM', 5
    );

INSERT INTO asteroids (name, risk_profile, distance)
VALUES ('Alpha-Lumina-V2', 'VOLATILE', 5.7);
INSERT INTO asteroid_resource (asteroid_id, resource_type, amount)
VALUES (
           (SELECT id FROM asteroids WHERE name = 'Alpha-Lumina-V2' LIMIT 1),
    'IRON', 500
    );
INSERT INTO asteroid_resource (asteroid_id, resource_type, amount)
VALUES (
           (SELECT id FROM asteroids WHERE name = 'Alpha-Lumina-V2' LIMIT 1),
    'PLATINUM', 1
    );
INSERT INTO asteroid_resource (asteroid_id, resource_type, amount)
VALUES (
           (SELECT id FROM asteroids WHERE name = 'Alpha-Lumina-V2' LIMIT 1),
    'GOLD', 2
    );

INSERT INTO asteroids (name, risk_profile, distance)
VALUES ('Aris-Centurion', 'CAUTION',  25.3);
INSERT INTO asteroid_resource (asteroid_id, resource_type, amount)
VALUES (
           (SELECT id FROM asteroids WHERE name = 'Aris-Centurion' LIMIT 1),
    'KRYPTONITE', 1500
    );