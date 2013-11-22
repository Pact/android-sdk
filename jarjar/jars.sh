#!/bin/sh
#
# Creates portable httpcore and httpmine JARs using jarjar to rewrite the
# packages to avoid conflicts with the built in Android versions of these
# specific libraries.
#

jar tf httpcore-4.3.jar | \
  grep "\\.class" | \
  python -c "import sys;[sys.stdout.write('.'.join(arg.split('/')[:-1]) +'\n') for arg in sys.stdin]" | \
  sort | uniq | \
  awk '{ print "rule " $1 ".* io.catalyze.jarjar.@0" }' > \
  httpcore.rules               

echo 'zap META*' >> httpcore.rules

java -jar jarjar-1.4.jar process httpcore.rules httpcore-4.3.jar httpcore-jarjar-4.3.jar

jar tf httpmime-4.3.1.jar | \
  grep "\\.class" | \
  python -c "import sys;[sys.stdout.write('.'.join(arg.split('/')[:-1]) +'\n') for arg in sys.stdin]" | \
  sort | uniq | \
  awk '{ print "rule " $1 ".* io.catalyze.jarjar.@0" }' > \
  httpmime.rules

java -jar jarjar-1.4.jar process httpmime.rules httpmime-4.3.1.jar httpmime-jarjar-4.3.1.jar    
