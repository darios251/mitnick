cd C:\Program Files\PostgreSQL\8.4\bin
pg_dump.exe -i -h localhost -p 5432 -U postgres -F c -b -v -f "C:/project/mitnick/backup.backup" demo
