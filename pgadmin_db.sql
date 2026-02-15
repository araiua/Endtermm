CREATE TABLE Actors (
    aid SERIAL PRIMARY KEY,
    firstname VARCHAR(50),
    lastname VARCHAR(50)
);

CREATE TABLE Movies (
    mid SERIAL PRIMARY KEY,
    title VARCHAR(100),
    release_year INT
);

CREATE TABLE ActorMovie (
    aid INT REFERENCES Actors(aid) ON DELETE CASCADE,
    mid INT REFERENCES Movies(mid) ON DELETE CASCADE,
    role_name VARCHAR(50),
    PRIMARY KEY (aid, mid)
);
