#!/bin/bash
# Execute script as a cronjob every x minutes to update dirty/invalid vote aggregates
# You should create a pgpass file so that login is possible without prompting for a password
USER=wis
HOST=localhost

echo "select * from update_aggregate()" | psql -U $USER -h $HOST -W
