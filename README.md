# hibernate-examples

I prefer H2 to Derby because the Derby devs refused to add 'DROP TABLE ... IF EXISTS' semantics despite many user complaints simply because it is not part of some SQL standard.That, however, clutters tests with useless SQLExceptions when instantiating tables.

In addition to that, H2 also does not create log files (derby.log) by default in random directories like the current working directory.

## Update

H2 supports the command "SHUTDOWN COMPACT". It revealed an annoying leak that prevents BLOBs from getting freed when their data rows get deleted (v1.4.196). It's possible to work around this by re-creating the database (SCRIPT TO/RUNSCRIPT FROM), but H2 also does not support SERIALIZABLE isolation level, so I decided to switch to HSQL.

HSQL also supports "SHUTDOWN COMPACT". It works nicely with BLOBs, but you need to run the "CHECKPOINT" SQL command if those blobs have not been checkpointed yet (ie. merged into the main database file(s)). HSQL also uses more than one file, so it's a good idea to place the main database file as given on the connection string in its own directory.

H2 and HSQL both have a GUI that can be started simply by running their respective database driver jars.

HSQL connection url to use MVCC/serializable isolation:

    jdbc:hsqldb:file:dbdir/db;hsqldb.tx=mvcc;hsqldb.tx_level=SERIALIZABLE


--
[![Build Status](https://travis-ci.org/jjYBdx4IL/hibernate-examples.png?branch=master)](https://travis-ci.org/jjYBdx4IL/hibernate-examples)
devel/java/github/hibernate-examples@7185
