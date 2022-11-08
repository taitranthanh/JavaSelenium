Step to start test cases with TestNG

#1. Configure Build Path -> Add TestNG library into Build Path. If not we will get error
Exception in thread "main" java.lang.UnsupportedClassVersionError: org/testng/TestNGException has been compiled by a more recent version of the Java Runtime (class file version 55.0), this version of the Java Runtime only recognizes class file versions up to 52.0

    
#2. mvn install (to install all dependencies)
#3. Executing test cases
    mvn test

Note:
- HTML report file at reports/seleniumExtentReports.html
- Screenshots at screenshots/ folder
- We can change testNG xml file at pom.xml as below
    			<configuration>
					<suiteXmlFiles>
						<suiteXmlFile>testng.xml</suiteXmlFile>
					</suiteXmlFiles>
				</configuration>
