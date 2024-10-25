README



# Debezium

## Configure
https://debezium.io/documentation/reference/stable/connectors/postgresql.html#postgresql-connector-properties


1. run docker compose to start all other containers
`docker compose up`

2. start spring boot app

3. add INSERT FROM test.sql to postgresql db via psql
`psql postgres://postgres@localhost:5432/postgres`
password is password
`INSERT INTO student_info (name, subject_area) VALUES ('Franz Josph Waltermann', 'Informatik');`

4. Open http://localhost:8888/ in your browser to warch Topic: my_dbz_topic.public.student_info
