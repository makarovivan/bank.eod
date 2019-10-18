# Bank End-of-Day Algo
## algo unit test and WXS implementation
This project provides synchronization mechanism for Websphere Application Server cluster or group of servers.
1. import ogclient:8.6.1.2+ (example, get original file from WAS/WXS installation):
mvn install:install-file -Dfile=wsogclient.jar -DgroupId=com.ibm.websphere.objectgrid -DartifactId=ogclient -Dversion=8.6.1.2 -Dpackaging=jar
2. mvn clean package
