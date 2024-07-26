import pymysql

hostname = 'localhost'
user = 'root'
password = 'rishi1234'

db = pymysql.connections.Connection(
    host=hostname,
    user=user,
    password=password
)

cursor = db.cursor()
cursor.execute("CREATE DATABASE IF NOT EXISTS merit_match")
cursor.execute("SHOW DATABASES")

for databases in cursor:
    print(databases)

cursor.close()
db.close()