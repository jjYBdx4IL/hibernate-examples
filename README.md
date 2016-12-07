# hibernate-examples

I prefer H2 to Derby because the Derby devs refused to add 'DROP TABLE ... IF EXISTS' semantics despite many user complaints simply because it is not part of some SQL standard.That, however, clutters tests with useless SQLExceptions when instantiating tables.

In addition to that, H2 also does not create log files (derby.log) by default in random directories like the current working directory.


--
[![Build Status](https://travis-ci.org/jjYBdx4IL/hibernate-examples.png?branch=master)](https://travis-ci.org/jjYBdx4IL/hibernate-examples)
devel/java/github/hibernate-examples@7168
