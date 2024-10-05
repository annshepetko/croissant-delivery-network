set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL

    CREATE DATABASE products;
    CREATE DATABASE delivery;
    CREATE DATABASE orders;
EOSQL